package com.nhXJH.web.service;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;
import com.nhXJH.web.domain.BaseDeptAuthority;
import com.nhXJH.web.domain.BaseFile;
import com.nhXJH.web.domain.LibraryAuth;
import com.nhXJH.web.domain.param.BaseFileParam;
import com.nhXJH.web.domain.param.GetFileParam;
import com.nhXJH.web.domain.vo.FileVo;
import com.nhXJH.web.domain.vo.GetFileVO;

/**
 * 附件实体对应信息Service接口
 * 
 * @author xjh
 * @date 2022-01-25
 */
public interface IBaseFileService extends IService<BaseFile>{
    /**
     * 查询附件实体对应信息
     * 
     * @param fileId 附件实体对应信息主键
     * @return 附件实体对应信息
     */
    public BaseFile selectBaseFileByFileId(Long fileId);
    public Integer getCount(BaseFile baseFile);
    /**
     * 查询附件实体对应信息列表
     * 
     * @param baseFile 附件实体对应信息
     * @return 附件实体对应信息集合
     */
    public List<BaseFile> selectBaseFileList(BaseFile baseFile);
    /**
     * 查询附件实体对应信息列表
     *
     * @param baseFile 附件实体对应信息
     * @return 附件实体对应信息集合
     */
    public List<FileVo> getFile(BaseFile baseFile);
    /**
     * 新增附件实体对应信息
     * 
     * @param baseFiles 附件实体对应信息
     * @return 结果
     */
    public int insertBaseFile(List<BaseFile> baseFiles);

    /**
     * 修改附件实体对应信息
     * 
     * @param baseFile 附件实体对应信息
     * @return 结果
     */
    public int updateBaseFile(BaseFile baseFile);

    /**
     * 批量删除附件实体对应信息
     * 
     * @param fileIds 需要删除的附件实体对应信息主键集合
     * @return 结果
     */
    public int deleteBaseFileByFileIds(Long[] fileIds,Long operator);

    /**
     * 删除附件实体对应信息信息
     * 
     * @param fileId 附件实体对应信息主键
     * @return 结果
     */
    public int deleteBaseFileByFileId(Long fileId,Long operator);

    /**
     * 获取作者头像信息
     *
     * @param file 作者信息
     * @return 获取结果
     */
    public List<String> getFileUrlList(BaseFile file);
    /**
     * 上传作者头像信息
     *
     * @param auth 作者信息
     * @return 上传结果
     */
//    public int setPicList(LibraryAuth auth);
    public List<GetFileVO> getFileList(GetFileParam param);
}
