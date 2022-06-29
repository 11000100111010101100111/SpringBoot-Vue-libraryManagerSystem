package com.nhXJH.web.service;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;
import com.nhXJH.common.core.domain.AjaxResult;
import com.nhXJH.web.domain.BookStock;
import com.nhXJH.web.domain.BookStockLog;
import com.nhXJH.web.domain.LibraryBook;
import com.nhXJH.web.domain.entity.BrowBookCode;
import com.nhXJH.web.domain.vo.BookStockVO;

/**
 * 图书库存信息Service接口
 * 
 * @author xjh
 * @date 2022-01-25
 */
public interface IBookStockService extends IService<BookStock>{
    /**
     * 查询图书库存信息
     * 
     * @param id 图书库存信息主键
     * @return 图书库存信息
     */
    public BookStock selectBookStockById(Long id);
    public Integer getCount(BookStock bookStock);
    /**
     * 查询图书库存信息列表
     * 
     * @param bookStock 图书库存信息
     * @return 图书库存信息集合
     */
    public List<BookStock> selectBookStockList(BookStock bookStock);
    /**
     * 查询图书库存信息列表
     *
     * @param book 图书库存信息
     * @return 图书库存信息集合
     */
    public BrowBookCode selectBookStockList(LibraryBook book, BrowBookCode browBookCode);
    /**
     * 根据图书借阅码获取图书详情信息
     *
     * **/
    public BookStockVO selectBookStock(String code);
    /**
     * 图书借阅
     * @param code 图书借阅码
     * @param userId 操作人id
     *
     * **/
    public AjaxResult getBookStock(String code, Long userId);
    /**
     * 新增图书库存信息
     * 
     * @param bookStock 图书库存信息
     * @return 结果
     */
    public int insertBookStock(BookStock bookStock);


    /**
     * 修改图书库存信息
     * 
     * @param bookStock 图书库存信息
     * @return 结果
     */
    public int updateBookStock(BookStock bookStock);

    /**
     * 批量删除图书库存信息
     * 
     * @param ids 需要删除的图书库存信息主键集合
     * @return 结果
     */
    public int deleteBookStockByIds(Long[] ids);

    /**
     * 删除图书库存信息信息
     * 
     * @param id 图书库存信息主键
     * @return 结果
     */
    public int deleteBookStockById(Long id);
}
