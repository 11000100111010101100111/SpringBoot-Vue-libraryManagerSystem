package com.nhXJH.web.mapper;

import java.util.List;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nhXJH.web.domain.BookStock;
import com.nhXJH.web.domain.BookStockLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * 图书库存信息Mapper接口
 * 
 * @author xjh
 * @date 2022-01-25
 */
@Mapper
public interface BookStockMapper  extends BaseMapper<BookStock> {
    /**
     * 查询图书库存信息
     * 
     * @param id 图书库存信息主键
     * @return 图书库存信息
     */
    public BookStock selectBookStockById(Long id);
    /**
     * 查询图书库存信息
     *
     * @param id 图书库存信息库位
     * @return 图书库存信息
     */
    public BookStock selectBookStockByStockId(Long id);
    public Integer getCount(BookStock bookStock);

    /**
     * 查询图书库存信息
     *
     * @param id 图书库存信息库位
     * @return 图书库存信息
     */
    public Long getStockBook(Long id);

    /**
     * 查询图书库存信息列表
     * 
     * @param bookStock 图书库存信息
     * @return 图书库存信息集合
     */
    public List<BookStock> selectBookStockList(BookStock bookStock);

    /**
     * 新增图书库存信息
     * 
     * @param bookStock 图书库存信息
     * @return 结果
     */
    public int insertBookStock(BookStock bookStock);
    /**
     * 新增图书库存信息
     *
     * @param list 图书库存信息
     * @return 结果
     */
    public int bookInStock(List<BookStock> list);
    /**
     * 修改图书库存信息
     * 
     * @param bookStock 图书库存信息
     * @return 结果
     */
    public int updateBookStock(BookStock bookStock);
    /**
     * 借书
     *
     * @param bookStock 图书库存信息
     * @return 结果
     */
    public int loan(BookStock bookStock);

    /**
     * 删除图书库存信息
     * 
     * @param id 图书库存信息主键
     * @return 结果
     */
    public int deleteBookStockById(Long id);

    /**
     * 批量删除图书库存信息
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBookStockByIds(Long[] ids);
}
