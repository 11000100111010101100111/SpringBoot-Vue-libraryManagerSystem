package com.nhXJH.web.service.file;

import com.baomidou.mybatisplus.extension.service.IService;
//import com.nhXJH.system.domain.AttachFile;
import com.nhXJH.enums.FileTypeEnums;
import com.nhXJH.web.domain.AttachFile;
import com.nhXJH.web.domain.param.FileKey;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * (AttachFile)表服务接口
 *
 * @author makejava
 * @since 2022-01-17 14:23:38
 */
public interface AttachFileService extends IService<AttachFile> {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    AttachFile queryById(Long id);

    /**
     * 分页查询
     *
     * @param AttachFile 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    Page<AttachFile> queryByPage(AttachFile AttachFile, PageRequest pageRequest);

    /**
     * 新增数据
     *
     * @param AttachFile 实例对象
     * @return 实例对象
     */
    AttachFile insert(AttachFile AttachFile);

    /**
     * 修改数据
     *
     * @param AttachFile 实例对象
     * @return 实例对象
     */
    AttachFile update(AttachFile AttachFile);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

    /**
     * 多文件上传，保存实体信息
     *
     * @param files 文件实体
     * @return AttachFile实例对象集
     */
    public List<AttachFile> upload(MultipartFile[] files, FileKey fileToken);

    /**
     * 多文件上传，保存实体信息
     *
     * @param files 文件实体
     * @return AttachFile实例对象集
     */
    public List<AttachFile> upload(MultipartFile[] files, FileKey fileToken, FileTypeEnums folder);

    public Boolean ack(List<Long> ids);
}
