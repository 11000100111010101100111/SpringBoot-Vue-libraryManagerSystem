package com.nhXJH.web.mapper;

import java.util.List;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nhXJH.web.domain.BaseDeptAuthority;
import com.nhXJH.web.domain.BaseFile;
import com.nhXJH.web.domain.vo.FileVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 附件实体对应信息Mapper接口
 * 
 * @author xjh
 * @date 2022-01-25
 */
@Mapper
public interface BaseFileMapper  extends BaseMapper<BaseFile> {
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
     * 新增附件实体对应信息
     * 
     * @param baseFile 附件实体对应信息
     * @return 结果
     */
    public int insertBaseFile(BaseFile baseFile);
    /**
     * 新增附件实体对应信息
     *
     * @param fileList 附件实体对应信息
     * @return 结果
     */
    public int insertBaseFiles(@Param("fileList") List<BaseFile> fileList);

    /**
     * 修改附件实体对应信息
     * 
     * @param baseFile 附件实体对应信息
     * @return 结果
     */
    public int updateBaseFile(BaseFile baseFile);

    /**
     * 删除附件实体对应信息
     * 
     * @param fileId 附件实体对应信息主键
     * @return 结果
     */
    public int deleteBaseFileByFileId(@Param("fileId")Long fileId,@Param("operator") Long operator);

    /**
     * 批量删除附件实体对应信息
     * 
     * @param fileIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBaseFileByFileIds(@Param("array")Long[] fileIds,@Param("operator")Long operator);

    /**
     * 根据实体ID和表代码获取文件完整信息
     *
     * @param baseFile
     * @return 结果
     */
    public List<FileVo> getFile(BaseFile baseFile);
}
