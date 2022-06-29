package com.nhXJH.web.mapper;

import java.util.List;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nhXJH.web.domain.BasePurchaseRequisition;
import com.nhXJH.web.domain.BasePurchaseRequisitionSub;
import com.nhXJH.web.domain.BookStockBrowHistory;
import com.nhXJH.web.domain.vo.BookBrowHistoryVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;

/**
 * 借书信息Mapper接口
 * 
 * @author xjh
 * @date 2022-03-14
 */
public interface BookStockBrowHistoryMapper  extends BaseMapper<BookStockBrowHistory> {
    /**
     * 查询借书信息
     * 
     * @param id 借书信息主键
     * @return 借书信息
     */
    public BookStockBrowHistory selectBookStockBrowHistoryById(Long id);

    /**
     * 查询借书信息
     *
     * @param userId 借书人id
     * @param bookId 图书id
     * @return 借书信息
     */
    public BookStockBrowHistory selectUserBrowedBook(@Param("userId") Long userId,@Param("bookId") Long bookId);

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
    public List<BookBrowHistoryVO> getMyHistory(BookStockBrowHistory bookStockBrowHistory);

    public BookBrowHistoryVO getMyHistoryById(Long id );

    /**
     * 新增借书信息
     * 
     * @param bookStockBrowHistory 借书信息
     * @return 结果
     */
    public int insertBookStockBrowHistory(BookStockBrowHistory bookStockBrowHistory);
    public Integer getCount(BookStockBrowHistory bookStockBrowHistory);
    /**
     * 修改借书信息
     * 
     * @param bookStockBrowHistory 借书信息
     * @return 结果
     */
    public int updateBookStockBrowHistory(BookStockBrowHistory bookStockBrowHistory);
    /**
     * 删除借书信息
     * 
     * @param id 借书信息主键
     * @return 结果
     */
    public int deleteBookStockBrowHistoryById(@Param("id") Long id, @Param("operator") Long operator);

    /**
     * 批量删除借书信息
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBookStockBrowHistoryByIds(@Param("array") Long[] ids, @Param("operator") Long operator);
}
