package com.nhXJH.web.service;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;
import com.nhXJH.web.domain.BasePurchaseRequisitionSub;
import com.nhXJH.web.domain.BookStockBrowHistory;
import com.nhXJH.web.domain.vo.BookBrowHistoryVO;
import org.apache.ibatis.annotations.Param;

/**
 * 借书信息Service接口
 * 
 * @author xjh
 * @date 2022-03-14
 */
public interface IBookStockBrowHistoryService extends IService<BookStockBrowHistory>{
    /**
     * 查询借书信息
     * 
     * @param id 借书信息主键
     * @return 借书信息
     */
    public BookStockBrowHistory selectBookStockBrowHistoryById(Long id);
    public Integer getCount(BookStockBrowHistory bookStockBrowHistory);
    /**
     * 查询借书信息列表
     * 
     * @param bookStockBrowHistory 借书信息
     * @return 借书信息集合
     */
    public List<BookStockBrowHistory> selectBookStockBrowHistoryList(BookStockBrowHistory bookStockBrowHistory);

    /**
     * 查询借书信息列表
     *
     * @param bookStockBrowHistory 借书信息
     * @return 借书信息集合
     */
    public List<BookBrowHistoryVO> getMyHistory(BookStockBrowHistory bookStockBrowHistory);   /**
     * 查询借书信息列表
     *
     * @param id 借书信息
     * @return 借书信息集合
     */
    public BookBrowHistoryVO getMyHistoryById(Long id );

    /**
     * 新增借书信息
     * 
     * @param bookStockBrowHistory 借书信息
     * @return 结果
     */
    public int insertBookStockBrowHistory(BookStockBrowHistory bookStockBrowHistory);

    /**
     * 修改借书信息
     * 
     * @param bookStockBrowHistory 借书信息
     * @return 结果
     */
    public int updateBookStockBrowHistory(BookStockBrowHistory bookStockBrowHistory);
    /**
     * 修改借书信息
     *
     * @param bookStockBrowHistory 借书信息
     * @return 结果
     */
    public int back(BookStockBrowHistory bookStockBrowHistory);

    /**
     * 批量删除借书信息
     * 
     * @param ids 需要删除的借书信息主键集合
     * @return 结果
     */
    public int deleteBookStockBrowHistoryByIds(Long[] ids, Long operator);

    /**
     * 删除借书信息信息
     * 
     * @param id 借书信息主键
     * @return 结果
     */
    public int deleteBookStockBrowHistoryById(Long id, Long operator);
}
