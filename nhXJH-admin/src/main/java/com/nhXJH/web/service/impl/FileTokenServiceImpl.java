package com.nhXJH.web.service.impl;

import java.util.List;

import com.nhXJH.web.domain.FileToken;
import com.nhXJH.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nhXJH.web.mapper.FileTokenMapper;
//import com.nhXJH.web.domain.FileToken;
import com.nhXJH.web.service.IFileTokenService;

/**
 * 附件访问秘钥信息Service业务层处理
 * 
 * @author xjh
 * @date 2022-01-25
 */
@Service
public class FileTokenServiceImpl extends ServiceImpl<FileTokenMapper, FileToken>
        implements IFileTokenService  {
    @Autowired
    private FileTokenMapper fileTokenMapper;

    /**
     * 查询附件访问秘钥信息
     * 
     * @param id 附件访问秘钥信息主键
     * @return 附件访问秘钥信息
     */
    @Override
    public FileToken selectFileTokenById(Long id) {
        return fileTokenMapper.selectFileTokenById(id);
    }

    @Override
    public Integer getCount(FileToken fileToken) {
        return fileTokenMapper.getCount(fileToken);
    }

    /**
     * 查询附件访问秘钥信息列表
     * 
     * @param fileToken 附件访问秘钥信息
     * @return 附件访问秘钥信息
     */
    @Override
    public List<FileToken> selectFileTokenList(FileToken fileToken) {
        return fileTokenMapper.selectFileTokenList(fileToken);
    }

    /**
     * 新增附件访问秘钥信息
     * 
     * @param fileToken 附件访问秘钥信息
     * @return 结果
     */
    @Override
    public int insertFileToken(FileToken fileToken) {
        fileToken.setCreateTime(DateUtils.getNowDate());
        return fileTokenMapper.insertFileToken(fileToken);
    }

    /**
     * 修改附件访问秘钥信息
     * 
     * @param fileToken 附件访问秘钥信息
     * @return 结果
     */
    @Override
    public int updateFileToken(FileToken fileToken) {
        return fileTokenMapper.updateFileToken(fileToken);
    }

    /**
     * 批量删除附件访问秘钥信息
     * 
     * @param ids 需要删除的附件访问秘钥信息主键
     * @return 结果
     */
    @Override
    public int deleteFileTokenByIds(Long[] ids) {
        return fileTokenMapper.deleteFileTokenByIds(ids);
    }

    /**
     * 删除附件访问秘钥信息信息
     * 
     * @param id 附件访问秘钥信息主键
     * @return 结果
     */
    @Override
    public int deleteFileTokenById(Long id) {
        return fileTokenMapper.deleteFileTokenById(id);
    }
}
