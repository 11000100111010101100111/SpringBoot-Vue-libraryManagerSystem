package com.nhXJH.web.service;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;
import com.nhXJH.web.domain.BookStockBrowHistory;
import com.nhXJH.web.domain.BookStockLog;
import com.nhXJH.web.domain.vo.BookStockLogVO;

/**
 * 图书库存出入信息Service接口
 * 
 * @author xjh
 * @date 2022-01-25
 */
public interface IBookStockLogService extends IService<BookStockLog>{
    /**
     * 查询图书库存出入信息
     * 
     * @param id 图书库存出入信息主键
     * @return 图书库存出入信息
     */
    public BookStockLog selectBookStockLogById(Long id);
    public Integer getCount(BookStockLog bookStockLog);
    /**
     * 查询图书库存出入信息
     *
     * @param stockId 库位ID
     * @return 图书库存出入信息
     */
    public List<BookStockLogVO> selectBookStockLogVOById(Long stockId);
    /**
     * 查询图书库存出入信息列表
     * 
     * @param bookStockLog 图书库存出入信息
     * @return 图书库存出入信息集合
     */
    public List<BookStockLog> selectBookStockLogList(BookStockLog bookStockLog);

    /**
     * 新增图书库存出入信息
     * 
     * @param bookStockLog 图书库存出入信息
     * @return 结果
     */
    public int insertBookStockLog(BookStockLog bookStockLog);

    /**
     * 修改图书库存出入信息
     * 
     * @param bookStockLog 图书库存出入信息
     * @return 结果
     */
    public int updateBookStockLog(BookStockLog bookStockLog);

    /**
     * 批量删除图书库存出入信息
     * 
     * @param ids 需要删除的图书库存出入信息主键集合
     * @return 结果
     */
    public int deleteBookStockLogByIds(Long[] ids);

    /**
     * 删除图书库存出入信息信息
     * 
     * @param id 图书库存出入信息主键
     * @return 结果
     */
    public int deleteBookStockLogById(Long id);
}
