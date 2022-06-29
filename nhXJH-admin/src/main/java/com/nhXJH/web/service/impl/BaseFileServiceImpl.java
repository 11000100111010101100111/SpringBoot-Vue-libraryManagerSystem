package com.nhXJH.web.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.nhXJH.common.enums.application.StatusEnums;
import com.nhXJH.web.domain.BaseFile;
import com.nhXJH.common.utils.DateUtils;
import com.nhXJH.web.domain.LibraryAuth;
import com.nhXJH.web.domain.entity.SysFile;
import com.nhXJH.web.domain.param.BaseFileParam;
import com.nhXJH.web.domain.param.GetFileParam;
import com.nhXJH.web.domain.vo.FileVo;
import com.nhXJH.web.domain.vo.GetFileVO;
import com.nhXJH.web.util.file.FileUploadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nhXJH.web.mapper.BaseFileMapper;
//import com.nhXJH.web.domain.BaseFile;
import com.nhXJH.web.service.IBaseFileService;

/**
 * 附件实体对应信息Service业务层处理
 * 
 * @author xjh
 * @date 2022-01-25
 */
@Service
public class BaseFileServiceImpl extends ServiceImpl<BaseFileMapper, BaseFile>
        implements IBaseFileService  {
    @Autowired
    private BaseFileMapper baseFileMapper;

    @Value("${local.file.path}")
    String localpath;
    @Value("${picture.default}")
    String defaultPath;
    /**
     * 查询附件实体对应信息
     * 
     * @param fileId 附件实体对应信息主键
     * @return 附件实体对应信息
     */
    @Override
    public BaseFile selectBaseFileByFileId(Long fileId) {
        return baseFileMapper.selectBaseFileByFileId(fileId);
    }

    @Override
    public Integer getCount(BaseFile baseFile) {
        return baseFileMapper.getCount(baseFile);
    }

    /**
     * 查询附件实体对应信息列表
     * 
     * @param baseFile 附件实体对应信息
     * @return 附件实体对应信息
     */
    @Override
    public List<BaseFile> selectBaseFileList(BaseFile baseFile) {
        return baseFileMapper.selectBaseFileList(baseFile);
    }
    /**
     * 查询附件实体对应信息列表
     *
     * @param baseFile 附件实体对应信息
     * @return 附件实体对应信息
     */
    @Override
    public List<FileVo> getFile(BaseFile baseFile) {
        List<FileVo> files = baseFileMapper.getFile(baseFile);
        if (files!=null&& files.size()>0){
            for (FileVo file : files) {
                file.setUrl(FileUploadUtils.getFilePath(file.getUrl()));
            }
        }else{
            FileVo noVo = new FileVo();
            noVo.setUrl(this.defaultPath);
            files.add(noVo);
        }
        return files;
    }
    /**
     * 新增附件实体对应信息
     *
     * @param files 附件实体对应信息
     * @return 结果
     */
    @Override
    public int insertBaseFile(List<BaseFile> files) {
        for (BaseFile baseFile :files) {
            baseFile.setCreateTime(DateUtils.getNowDate());
            baseFile.setIsDel(StatusEnums.ZERO.getDesc());
        }
        return baseFileMapper.insertBaseFiles(files);
    }

    /**
     * 修改附件实体对应信息
     * 
     * @param baseFile 附件实体对应信息
     * @return 结果
     */
    @Override
    public int updateBaseFile(BaseFile baseFile) {
        baseFile.setUpdateTime(DateUtils.getNowDate());
        return baseFileMapper.updateBaseFile(baseFile);
    }

    /**
     * 批量删除附件实体对应信息
     * 
     * @param array 需要删除的附件实体对应信息主键
     * @return 结果
     */
    @Override
    public int deleteBaseFileByFileIds(Long[] array,Long operator) {
        return baseFileMapper.deleteBaseFileByFileIds(array,operator);
    }

    /**
     * 删除附件实体对应信息信息
     * 
     * @param fileId 附件实体对应信息主键
     * @return 结果
     */
    @Override
    public int deleteBaseFileByFileId(Long fileId,Long operator) {
        return baseFileMapper.deleteBaseFileByFileId(fileId,operator);
    }

    /**
     * 获取作者头像信息
     *
     * @param file 作者信息
     * @return 获取结果
     */
    public List<String> getFileUrlList(BaseFile file){
        List<BaseFile> picList = baseFileMapper.selectBaseFileList(file);
        List<String> fileList = new ArrayList<>();
        for (BaseFile baseFile : picList) {
            fileList.add(baseFile.getUrl());
        }
        return fileList;
    }
    /**
     * 获取附件对应信息
     *
     * @param param 附件查询信息
     * @return 上传结果
     */
    public List<GetFileVO> getFileList(GetFileParam param){
       BaseFile baseFile = new BaseFile();
       baseFile.setModelId(param.getModelId());
       baseFile.setDbId(param.getDbId());
       baseFile.setFileId(param.getFileId());
       baseFile.setStatus(StatusEnums.ONE.getDesc());
       List<FileVo> voList = baseFileMapper.getFile(baseFile);

       List<GetFileVO> getResult = new ArrayList<>();
       voList.forEach(vo->{
           GetFileVO fileVO = new GetFileVO();
           fileVO.setDbId(vo.getDbId());
           fileVO.setFileId(vo.getFileId());
           fileVO.setGroupId(vo.getGroupId());
           fileVO.setMark(vo.getMark());
           fileVO.setName(vo.getFileName());
           fileVO.setNewName(vo.getNewName());
           fileVO.setUrl(FileUploadUtils.getFilePath(vo.getUrl()));
           getResult.add(fileVO);
       });
        return getResult;
    }
}
