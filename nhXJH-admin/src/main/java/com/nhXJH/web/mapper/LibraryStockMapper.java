package com.nhXJH.web.mapper;

import java.util.List;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nhXJH.web.domain.LibraryPress;
import com.nhXJH.web.domain.LibraryStock;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 库位信息Mapper接口
 * 
 * @author xjh
 * @date 2022-01-25
 */
@Mapper
public interface LibraryStockMapper  extends BaseMapper<LibraryStock> {
    /**
     * 查询库位信息
     * 
     * @param id 库位信息主键
     * @return 库位信息
     */
    public LibraryStock selectLibraryStockById(Long id);
    /**
     * 获取图书库存信息
     *
     * @param array 根据id数组批量获取库位信息
     * @return 结果
     */
    public List<LibraryStock> getStockListByIds(Long[] array);
    /**
     * 查询库位信息列表
     * 
     * @param libraryStock 库位信息
     * @return 库位信息集合
     */
    public List<LibraryStock> selectLibraryStockList(LibraryStock libraryStock);
    public Integer getCount(LibraryStock libraryStock);
    /**
     * 查询库位信息列表
     *
     * @return 库位信息集合
     */
    public List<LibraryStock> getStockList(
            @Param("hasBook")String hasBook,
            @Param("status")String status,
            @Param("isDel")String isDel,
            @Param("bookType")Long bookType,
            @Param("mechanism")Long mechanism
    );
    /**
     * 新增库位信息
     * 
     * @param libraryStock 库位信息
     * @return 结果
     */
    public int insertLibraryStock(LibraryStock libraryStock);

    public int insertMoreLibraryStock(@Param("list")List<LibraryStock> libraryStock);

    /**
     * 修改库位信息
     * 
     * @param libraryStock 库位信息
     * @return 结果
     */
    public int updateLibraryStock(LibraryStock libraryStock);
    /**
     * 修改库位信息
     *
     * @param libraryStock 库位信息
     * @return 结果
     */
    public int inStock(@Param("stock") LibraryStock libraryStock,@Param("array") Long[] array);

    /**
     * 删除库位信息
     * 
     * @param id 库位信息主键
     * @return 结果
     */
    public int deleteLibraryStockById(Long id,Long operator);

    /**
     * 批量删除库位信息
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteLibraryStockByIds(Long[] ids,Long operator);
}
