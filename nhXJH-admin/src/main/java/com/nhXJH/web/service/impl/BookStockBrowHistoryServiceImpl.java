package com.nhXJH.web.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.nhXJH.common.enums.OperatorType;
import com.nhXJH.common.exception.ServiceException;
import com.nhXJH.common.utils.DateUtils;
import com.nhXJH.common.utils.StringUtils;
import com.nhXJH.enums.*;
import com.nhXJH.web.domain.BookStock;
import com.nhXJH.web.domain.BookStockBrowHistory;
import com.nhXJH.web.domain.BookStockLog;
import com.nhXJH.web.domain.LibraryStock;
import com.nhXJH.web.domain.vo.BookBrowHistoryVO;
import com.nhXJH.web.mapper.BookStockBrowHistoryMapper;
import com.nhXJH.web.mapper.BookStockLogMapper;
import com.nhXJH.web.mapper.BookStockMapper;
import com.nhXJH.web.mapper.LibraryStockMapper;
import com.nhXJH.web.service.IBookStockBrowHistoryService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;

/**
 * 借书信息Service业务层处理
 * 
 * @author xjh
 * @date 2022-03-14
 */
@Service
public class BookStockBrowHistoryServiceImpl extends ServiceImpl<BookStockBrowHistoryMapper, BookStockBrowHistory>
        implements IBookStockBrowHistoryService {
    @Autowired
    private BookStockBrowHistoryMapper bookStockBrowHistoryMapper;
    @Autowired
    private BookStockLogMapper logMapper;
    @Autowired
    private BookStockMapper stockMapper;
    @Autowired
    private LibraryStockMapper libraryStockMapper;

    /**
     * 查询借书信息
     * 
     * @param id 借书信息主键
     * @return 借书信息
     */
    @Override
    public BookStockBrowHistory selectBookStockBrowHistoryById(Long id) {
        return bookStockBrowHistoryMapper.selectBookStockBrowHistoryById(id);
    }

    @Override
    public Integer getCount(BookStockBrowHistory bookStockBrowHistory) {
        return bookStockBrowHistoryMapper.getCount(bookStockBrowHistory);
    }

    /**
     * 查询借书信息列表
     * 
     * @param bookStockBrowHistory 借书信息
     * @return 借书信息
     */
    @Override
    public List<BookStockBrowHistory> selectBookStockBrowHistoryList(BookStockBrowHistory bookStockBrowHistory) {
        return bookStockBrowHistoryMapper.selectBookStockBrowHistoryList(bookStockBrowHistory);
    }
    /**
     * 查询借书信息列表
     *
     * @param bookStockBrowHistory 借书信息
     * @return 借书信息
     */
    @Override
    public List<BookBrowHistoryVO> getMyHistory(BookStockBrowHistory bookStockBrowHistory){
        return bookStockBrowHistoryMapper.getMyHistory(bookStockBrowHistory);
    }

    /** 查询借书信息列表
     *
     * @param id 借书信息
     * @return 借书信息集合
     */
    public BookBrowHistoryVO getMyHistoryById(Long id ){
        if (StringUtils.isNull(id)){
            throw new ServiceException("查询异常");
        }
        BookBrowHistoryVO vo = bookStockBrowHistoryMapper.getMyHistoryById(id);
        if (StringUtils.isNotNull(vo.getCreateTime()) && StringUtils.isNotNull(vo.getBackTime())){
             vo.setTotalTime(com.nhXJH.web.util.date.DateUtils.subtractionDate(vo.getCreateTime(),vo.getBackTime()));
        }
        return vo;
    }
    /**
     * 新增借书信息
     * 
     * @param bookStockBrowHistory 借书信息
     * @return 结果
     */
    @Override
    public int insertBookStockBrowHistory(BookStockBrowHistory bookStockBrowHistory) {
        bookStockBrowHistory.setCreateTime(DateUtils.getNowDate());
        return bookStockBrowHistoryMapper.insertBookStockBrowHistory(bookStockBrowHistory);
    }

    /**
     * 修改借书信息
     * 
     * @param bookStockBrowHistory 借书信息
     * @return 结果
     */
    @Override
    public int updateBookStockBrowHistory(BookStockBrowHistory bookStockBrowHistory) {
        bookStockBrowHistory.setUpdateTime(DateUtils.getNowDate());
        return bookStockBrowHistoryMapper.updateBookStockBrowHistory(bookStockBrowHistory);
    }

    @Override
    @Transactional
    public int back(BookStockBrowHistory bookStockBrowHistory) {
        bookStockBrowHistory.setHasBack(LoanStyleEnums.RETURN_BOOK.getDesc());
        bookStockBrowHistoryMapper.updateBookStockBrowHistory(bookStockBrowHistory);

        BookStock bookStock = new BookStock();
        bookStock.setId(bookStockBrowHistory.getStockId());
        bookStock.setUpdateTime(new Date());
        bookStock.setUpdatePersonal(bookStockBrowHistory.getUpdatePersonal());
        bookStock.setHasLoan(HasLoanEnums.NORMAL.getDesc());
        stockMapper.updateBookStock(bookStock);

        bookStock = stockMapper.selectBookStockById(bookStock.getId());
        if (StringUtils.isNull(bookStock)){
            throw new ServiceException("操作异常");
        }

        LibraryStock stock = new LibraryStock();
        stock.setId(bookStock.getStockLocaltion());
        stock.setHasBook(StockHasBookEnums.HAS_BOOK.getDesc());
        libraryStockMapper.updateLibraryStock(stock);

        BookStockLog log = new BookStockLog();
        log.setId(log.getSnowID());
        log.setIsLoan(LoanStyleEnums.RETURN_BOOK.getDesc());
        log.setCreatePersonal(bookStockBrowHistory.getUpdatePersonal());
        log.setIsDel(DelEnums.NOT_DEL.getValue());
        log.setOperateType(BookStockOperatorType.ONLINE.getDesc());
        log.setStockId(bookStockBrowHistory.getStockId());
        log.setCreateTime(new Date());
        logMapper.insertBookStockLog(log);
        return 1;
    }

    /**
     * 批量删除借书信息
     * 
     * @param ids 需要删除的借书信息主键
     * @return 结果
     */
    @Override
    public int deleteBookStockBrowHistoryByIds(Long[] ids,Long operator) {
        return bookStockBrowHistoryMapper.deleteBookStockBrowHistoryByIds(ids,operator);
    }

    /**
     * 删除借书信息信息
     * 
     * @param id 借书信息主键
     * @return 结果
     */
    @Override
    public int deleteBookStockBrowHistoryById(Long id, Long operator) {
        return bookStockBrowHistoryMapper.deleteBookStockBrowHistoryById(id,operator);
    }
}
