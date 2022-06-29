package com.nhXJH.web.mapper;

import java.util.List;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nhXJH.web.domain.LibraryBook;
import com.nhXJH.web.domain.LibraryMechanism;
import com.nhXJH.web.domain.param.MechanismParam;
import org.apache.ibatis.annotations.Mapper;

/**
 * 图书管理机构信息Mapper接口
 * 
 * @author xjh
 * @date 2022-01-25
 */
@Mapper
public interface LibraryMechanismMapper  extends BaseMapper<LibraryMechanism> {
    /**
     * 查询图书管理机构信息
     * 
     * @param id 图书管理机构信息主键
     * @return 图书管理机构信息
     */
    public LibraryMechanism selectLibraryMechanismById(Long id);
    public Integer getCount(MechanismParam libraryMechanism);
    /**
     * 查询图书管理机构信息列表
     * 
     * @param libraryMechanism 图书管理机构信息
     * @return 图书管理机构信息集合
     */
    public List<LibraryMechanism> selectLibraryMechanismList(MechanismParam libraryMechanism);

    /**
     * 新增图书管理机构信息
     * 
     * @param libraryMechanism 图书管理机构信息
     * @return 结果
     */
    public int insertLibraryMechanism(LibraryMechanism libraryMechanism);

    /**
     * 修改图书管理机构信息
     * 
     * @param libraryMechanism 图书管理机构信息
     * @return 结果
     */
    public int updateLibraryMechanism(LibraryMechanism libraryMechanism);

    /**
     * 删除图书管理机构信息
     * 
     * @param id 图书管理机构信息主键
     * @return 结果
     */
    public int deleteLibraryMechanismById(Long id);

    /**
     * 批量删除图书管理机构信息
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteLibraryMechanismByIds(Long[] ids);
}
