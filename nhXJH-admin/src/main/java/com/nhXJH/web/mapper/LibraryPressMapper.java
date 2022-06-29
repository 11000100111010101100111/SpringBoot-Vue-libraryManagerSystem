package com.nhXJH.web.mapper;

import java.util.List;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nhXJH.web.domain.LibraryPress;
import com.nhXJH.web.domain.param.MechanismParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 出版社信息Mapper接口
 * 
 * @author xjh
 * @date 2022-01-25
 */
@Mapper
public interface LibraryPressMapper  extends BaseMapper<LibraryPress> {
    /**
     * 查询出版社信息
     * 
     * @param id 出版社信息主键
     * @return 出版社信息
     */
    public LibraryPress selectLibraryPressById(Long id);
    public Integer getCount(LibraryPress libraryPress);
    /**
     * 查询出版社信息列表
     * 
     * @param libraryPress 出版社信息
     * @return 出版社信息集合
     */
    public List<LibraryPress> selectLibraryPressList(LibraryPress libraryPress);

    /**
     * 新增出版社信息
     * 
     * @param libraryPress 出版社信息
     * @return 结果
     */
    public int insertLibraryPress(LibraryPress libraryPress);

    /**
     * 修改出版社信息
     * 
     * @param libraryPress 出版社信息
     * @return 结果
     */
    public int updateLibraryPress(LibraryPress libraryPress);

    /**
     * 删除出版社信息
     * 
     * @param id 出版社信息主键
     * @return 结果
     */
    public int deleteLibraryPressById(Long id,Long operator);

    /**
     * 批量删除出版社信息
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteLibraryPressByIds(@Param("array") Long[] ids, @Param("operator") Long operator);

}
