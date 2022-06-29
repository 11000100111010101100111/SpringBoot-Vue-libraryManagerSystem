package com.nhXJH.web.service;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;
import com.nhXJH.web.domain.LibraryPress;
import com.nhXJH.web.domain.LibraryStock;
import com.nhXJH.web.domain.param.LibraryStockMoreParam;
import com.nhXJH.web.domain.vo.StockVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;

/**
 * 库位信息Service接口
 * 
 * @author xjh
 * @date 2022-01-25
 */
public interface ILibraryStockService extends IService<LibraryStock>{
    /**
     * 查询库位信息
     * 
     * @param id 库位信息主键
     * @return 库位信息
     */
    public LibraryStock selectLibraryStockById(Long id);
    public Integer getCount(LibraryStock libraryStock);
    /**
     * 查询库位相关信息
     *
     * @param id 库位信息主键
     * @return 库位信息
     */
    public StockVO selectLibraryStockVOById(Long id);

    /**
     * 查询库位信息列表
     * 
     * @param libraryStock 库位信息
     * @return 库位信息集合
     */
    public List<LibraryStock> selectLibraryStockList(LibraryStock libraryStock);
    /**
     * 查询库位信息列表
     *
     * @return 库位信息集合
     */
    public List<LibraryStock> getStockList( Long bookType, Long mechanism  );

    /**
     * 新增库位信息
     * 
     * @param libraryStock 库位信息
     * @return 结果
     */
    public int insertLibraryStock(LibraryStock libraryStock);
    /**
     * 批量新增库位信息
     *
     * @param libraryStock 库位信息
     * @return 结果
     */
    public int addMore(LibraryStockMoreParam libraryStock);

    /**
     * 修改库位信息
     * 
     * @param libraryStock 库位信息
     * @return 结果
     */
    public int updateLibraryStock(LibraryStock libraryStock);

    /**
     * 批量删除库位信息
     * 
     * @param ids 需要删除的库位信息主键集合
     * @return 结果
     */
    public int deleteLibraryStockByIds(Long[] ids,Long operator);

    /**
     * 删除库位信息信息
     * 
     * @param id 库位信息主键
     * @return 结果
     */
    public int deleteLibraryStockById(Long id,Long operator);
}
