package com.nhXJH.web.mapper;

import java.util.List;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nhXJH.web.domain.DbMap;
import com.nhXJH.web.domain.FileToken;
import org.apache.ibatis.annotations.Mapper;

/**
 * 附件访问秘钥信息Mapper接口
 * 
 * @author xjh
 * @date 2022-01-25
 */
@Mapper
public interface FileTokenMapper  extends BaseMapper<FileToken> {
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
     * 删除附件访问秘钥信息
     * 
     * @param id 附件访问秘钥信息主键
     * @return 结果
     */
    public int deleteFileTokenById(Long id);

    /**
     * 批量删除附件访问秘钥信息
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFileTokenByIds(Long[] ids);
}
