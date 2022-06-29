package com.nhXJH.web.service;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;
import com.nhXJH.web.domain.LibraryBook;
import com.nhXJH.web.domain.PurchaseTemplate;
import com.nhXJH.web.domain.StockBookshelf;
import com.nhXJH.web.domain.vo.BookShelfVO;

/**
 * 书架信息Service接口
 * 
 * @author xjh
 * @date 2022-01-25
 */
public interface IStockBookshelfService extends IService<StockBookshelf>{
    /**
     * 查询书架信息
     * 
     * @param id 书架信息主键
     * @return 书架信息
     */
    public StockBookshelf selectStockBookshelfById(Long id);
    /**
     * 查询书架信息列表
     * 
     * @param stockBookshelf 书架信息
     * @return 书架信息集合
     */
    public List<StockBookshelf> selectStockBookshelfList(StockBookshelf stockBookshelf);
    /**
     * 获取书架信息返回结果列表
     *
     * @param stockBookshelf 书架信息
     * @return 书架信息
     */
    public List<BookShelfVO> selectStockBookshelfVOList(StockBookshelf stockBookshelf);
    /**
     * 获取书架信息返回结果
     *
     * @param id 书架信息id
     * @return 书架信息
     */
    public BookShelfVO selectStockBookshelfVOById(Long id);
    /**
     * 查询书架总数
     *
     * @param stockBookshelf 书架信息
     * @return 书架信息总数
     */
    public Long getCount(StockBookshelf stockBookshelf);
    /**
     * 新增书架信息
     * 
     * @param stockBookshelf 书架信息
     * @return 结果
     */
    public int insertStockBookshelf(StockBookshelf stockBookshelf);

    /**
     * 修改书架信息
     * 
     * @param stockBookshelf 书架信息
     * @return 结果
     */
    public int updateStockBookshelf(StockBookshelf stockBookshelf);

    /**
     * 批量删除书架信息
     * 
     * @param ids 需要删除的书架信息主键集合
     * @return 结果
     */
    public List<StockBookshelf> deleteStockBookshelfByIds(Long[] ids);

    /**
     * 删除书架信息信息
     * 
     * @param id 书架信息主键
     * @return 结果
     */
    public List<StockBookshelf> deleteStockBookshelfById(Long id);

    /**
     * 修改书架信息状态
     *
     * @param bookshelf 图书信息
     * @return 结果
     */
    public int updateUserStatus(StockBookshelf bookshelf);
}
