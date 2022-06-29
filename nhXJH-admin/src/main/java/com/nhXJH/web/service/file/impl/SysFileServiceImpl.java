package com.nhXJH.web.service.file.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nhXJH.common.core.domain.model.LoginUser;
import com.nhXJH.common.enums.application.StatusEnums;
import com.nhXJH.common.exception.ServiceException;
import com.nhXJH.common.utils.SecurityUtils;
import com.nhXJH.common.utils.StringUtils;
import com.nhXJH.enums.FileTypeEnums;
import com.nhXJH.web.domain.AttachFile;
import com.nhXJH.web.domain.param.FileKey;
import com.nhXJH.web.mapper.SysFileMapper;
import com.nhXJH.web.service.file.AttachFileService;
import com.nhXJH.web.service.file.FileHandel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * (AttachFile)表服务实现类
 *
 * @author xjh
 * @since 2022-01-17 14:23:39
 */
@Service("sysFileService")
public class SysFileServiceImpl extends ServiceImpl<SysFileMapper, AttachFile> implements AttachFileService {
    @Resource
    private SysFileMapper sysFileDao;

    @Resource(name = "localFileServiceImpl")
    private FileHandel fileHandel;

    @Autowired
    private AttachFileService attachFileService;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public AttachFile queryById(Long id) {
        return this.sysFileDao.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param sysFile 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @Override
    public Page<AttachFile> queryByPage(AttachFile sysFile, PageRequest pageRequest) {
        long total = this.sysFileDao.count(sysFile);
        return new PageImpl<>(this.sysFileDao.queryAllByLimit(sysFile, pageRequest), pageRequest, total);
    }

    /**
     * 新增数据
     *
     * @param sysFile 实例对象
     * @return 实例对象
     */
    @Override
    public AttachFile insert(AttachFile sysFile) {
        this.sysFileDao.insert(sysFile);
        return sysFile;
    }

    /**
     * 修改数据
     *
     * @param sysFile 实例对象
     * @return 实例对象
     */
    @Override
    public AttachFile update(AttachFile sysFile) {
        this.sysFileDao.update(sysFile);
        return this.queryById(sysFile.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.sysFileDao.deleteById(id) > 0;
    }


    /**
     * 多文件上传，保存实体信息
     *
     * @param files 文件实体
     * @return SysFile实例对象集
     */
    @Transactional
    public List<AttachFile> upload(MultipartFile[] files, FileKey fileToken) {
        return attachFileService.upload(files,fileToken,null);
    }
    /**
     * 多文件上传，保存实体信息
     *
     * @param files 文件实体
     * @return SysFile实例对象集
     */
    @Transactional
    public List<AttachFile> upload(MultipartFile[] files, FileKey fileToken, FileTypeEnums folder) {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        List<AttachFile> fileList = new ArrayList<>();
        try {
            fileList = fileHandel.upload(files,fileToken,folder);
        } catch (Exception e) {
            throw new ServiceException("上传失败");
        }
        for (AttachFile sysFile : fileList) {
            sysFile.setId(sysFile.getSnowID());
            sysFile.setCreatePerson(loginUser.getUserId());
            sysFile.setCreateTime(new Date());
            sysFile.setMark(StringUtils.EMPTY);
            sysFile.setIsDel(StatusEnums.ZERO.getDesc());
            sysFile.setStatus(StatusEnums.ZERO.getCode());
        }

        sysFileDao.insertBatch(fileList);

        return fileList;
    }

    public Boolean ack(List<Long> ids){
        QueryWrapper<AttachFile> wrapper = new QueryWrapper<>();
        wrapper.eq("`status`",StatusEnums.ONE.getCode())
                .eq("update_persoanl",SecurityUtils.getLoginUser().getUserId())
                .eq("update_time",new Date());
        wrapper.and(w->{
            for (Long id : ids) {
                w.or().eq("id",id);
            }
        });
        return super.update(wrapper);
    }
}
