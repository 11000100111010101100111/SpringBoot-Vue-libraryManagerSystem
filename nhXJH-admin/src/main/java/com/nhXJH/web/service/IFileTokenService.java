package com.nhXJH.web.service;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;
import com.nhXJH.web.domain.DbMap;
import com.nhXJH.web.domain.FileToken;

/**
 * 附件访问秘钥信息Service接口
 * 
 * @author xjh
 * @date 2022-01-25
 */
public interface IFileTokenService extends IService<FileToken>{
    /**
     * 查询附件访问秘钥信息
     * 
     * @param id 附件访问秘钥信息主键
     * @return 附件访问秘钥信息
     */
    public FileToken selectFileTokenById(Long id);
    public Integer getCount(FileToken fileToken);
    /**
     * 查询附件访问秘钥信息列表
     * 
     * @param fileToken 附件访问秘钥信息
     * @return 附件访问秘钥信息集合
     */
    public List<FileToken> selectFileTokenList(FileToken fileToken);

    /**
     * 新增附件访问秘钥信息
     * 
     * @param fileToken 附件访问秘钥信息
     * @return 结果
     */
    public int insertFileToken(FileToken fileToken);

    /**
     * 修改附件访问秘钥信息
     * 
     * @param fileToken 附件访问秘钥信息
     * @return 结果
     */
    public int updateFileToken(FileToken fileToken);

    /**
     * 批量删除附件访问秘钥信息
     * 
     * @param ids 需要删除的附件访问秘钥信息主键集合
     * @return 结果
     */
    public int deleteFileTokenByIds(Long[] ids);

    /**
     * 删除附件访问秘钥信息信息
     * 
     * @param id 附件访问秘钥信息主键
     * @return 结果
     */
    public int deleteFileTokenById(Long id);
}
