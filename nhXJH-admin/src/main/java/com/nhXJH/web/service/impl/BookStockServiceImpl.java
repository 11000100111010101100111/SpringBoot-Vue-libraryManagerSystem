package com.nhXJH.web.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.nhXJH.common.core.domain.AjaxResult;
import com.nhXJH.common.core.domain.entity.SysDept;
import com.nhXJH.common.enums.OperatorType;
import com.nhXJH.common.enums.application.TableTypeEnums;
import com.nhXJH.common.exception.ServiceException;
import com.nhXJH.common.utils.StringUtils;
import com.nhXJH.enums.*;
import com.nhXJH.system.domain.po.BaseBookClassCn;
import com.nhXJH.system.mapper.BaseBookClassCnMapper;
import com.nhXJH.system.mapper.SysDeptMapper;
import com.nhXJH.web.domain.*;
import com.nhXJH.common.utils.DateUtils;
import com.nhXJH.web.domain.entity.BrowBookCode;
import com.nhXJH.web.domain.vo.BaseFileClassify;
import com.nhXJH.web.domain.vo.BookStockVO;
import com.nhXJH.web.mapper.*;
import com.nhXJH.web.util.CodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisServer;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
//import com.nhXJH.web.domain.BookStock;
import com.nhXJH.web.service.IBookStockService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 图书库存信息Service业务层处理
 * 
 * @author xjh
 * @date 2022-01-25
 */
@Service
public class BookStockServiceImpl extends ServiceImpl<BookStockMapper, BookStock>
        implements IBookStockService  {
    @Autowired
    private BookStockMapper bookStockMapper;
    @Autowired
    private SysDeptMapper deptMapper;
    @Autowired
    private LibraryStockMapper stockMapper;
    @Autowired
    private LibraryBookMapper bookMapper;
    @Autowired
    private LibraryAuthMapper authMapper;
    @Autowired
    private LibraryPressMapper pressMapper;
    @Autowired
    private StockStoreyMapper storeyMapper;
    @Autowired
    private StockBookshelfMapper shelfMapper;
    @Autowired
    private BaseBookClassCnMapper clazMapper;
    @Autowired
    private BaseFileMapper fileMapper;
    @Autowired
    private BookStockLogMapper logMapper;
    @Autowired
    private BookStockBrowHistoryMapper browHistoryMapper;

    /**
     * 查询图书库存信息
     * 
     * @param id 图书库存信息主键
     * @return 图书库存信息
     */
    @Override
    public BookStock selectBookStockById(Long id) {
        return bookStockMapper.selectBookStockById(id);
    }

    @Override
    public Integer getCount(BookStock bookStock) {
        return bookStockMapper.getCount(bookStock);
    }

    /**
     * 查询图书库存信息列表
     * 
     * @param bookStock 图书库存信息
     * @return 图书库存信息
     */
    @Override
    public List<BookStock> selectBookStockList(BookStock bookStock) {
        return bookStockMapper.selectBookStockList(bookStock);
    }
    /**
     * 查询图书库存信息列表
     *
     * @param book 图书库存信息
     * @return 图书库存信息集合
     */
    public BrowBookCode selectBookStockList(LibraryBook book, BrowBookCode browBookCode){
        if (null == book.getId()){
            throw new ServiceException("操作失败");
        }
        Long stock = bookStockMapper.getStockBook(book.getId());
        if (null == stock) {
            return null;
        }
        browBookCode.setCode(CodeUtil.toCode(stock) );
        browBookCode.setStockId(stock);
        return browBookCode;
    }

    /**
     * 根据图书借阅码获取图书详情信息
     *
     * **/
    @Override
    @Transactional
    public BookStockVO selectBookStock( String code){
        if (StringUtils.isEmpty(code) || !CodeUtil.isCode(code)){
            throw new ServiceException("图书借阅码有误,获取失败");
        }
        Long id = CodeUtil.getKey(code);
        if (StringUtils.isNull(id)){
            throw new ServiceException("图书借阅码有误,获取失败");
        }
        BookStockVO stockVO = new BookStockVO();
        //@TODO 查询当前库存基本信息
        BookStock stock = bookStockMapper.selectBookStockById(id);
        if (StringUtils.isNull(stock)){
            return null;
        }
        stockVO.setBookStock(stock);



        //@TODO 查询图书基本信息及其附件信息
        Long bookId = stock.getBookId();
        LibraryBook book = bookMapper.selectLibraryBookById(bookId);
        if (null != book) {
            //@TODO 作者/。。。
            Long authId = book.getAuthor();
            if (StringUtils.isNotNull(authId)) {
                LibraryAuth auth = authMapper.selectLibraryAuthById(authId);
                stockVO.setAuth(auth);
            }
            //@TODO 出版社/。。。
            Long pressId = book.getMechanism();
            if (StringUtils.isNotNull(pressId)){
                stockVO.setPress(pressMapper.selectLibraryPressById(pressId));
            }
            Long clazId = book.getClaz();
            if (StringUtils.isNotNull(clazId)){
                stockVO.setClaz(clazMapper.selectBaseBookClassCnById(clazId));
            }

            stockVO.setBook(book);
            BaseFile query = new BaseFile();
            query.setModelId(bookId);
            query.setDbId(TableTypeEnums.BOOK.getCode());
            query.setIsDel(DelEnums.NOT_DEL.getValue());
            query.setStatus(StatusEnums.ONE.getDesc());
            List<BaseFile> fileList = fileMapper.selectBaseFileList(query);
            if (null !=fileList){
                List<String> srcList = new ArrayList<>();
                for (BaseFile baseFile : fileList) {
                    srcList.add(baseFile.getUrl());
                }
                stockVO.setImgSrc(srcList);
            }

            BaseFileClassify fileClassifies = new BaseFileClassify(fileList);
            stockVO.setFile(fileClassifies);
        }else {
            return stockVO;
        }
        //@TODO 部门基本信息及其全体父级部门
        Long deptId = stock.getMechanismId();
        SysDept dept = deptMapper.selectDeptById(deptId);
        if (!StringUtils.isEmpty(code)){
            stockVO.setDept(dept);
            List<SysDept> parentDept = deptMapper.getParent(dept);
            stockVO.setParentDept(parentDept);
        }

        //@TODO 库位信息（书架，书架层，枯萎）
        Long stockId = stock.getStockLocaltion();
        LibraryStock libraryStock = stockMapper.selectLibraryStockById(stockId);
        if (null != libraryStock){
            stockVO.setStock(libraryStock);
            StockStorey storey = storeyMapper.selectStockStoreyById(libraryStock.getStoreId());
            if (null != storey){
                stockVO.setStorey(storey);
                StockBookshelf bookshelf = shelfMapper.selectStockBookshelfById(storey.getBookshelf());
                if (null != bookshelf){
                    stockVO.setShelf(bookshelf);
                }
            }
        }

        return stockVO;
    }

    /**
     * 图书借阅
     * @param code 图书借阅码
     * @param userId 操作人id
     *
     * **/
    @Override
    @Transactional
    public AjaxResult getBookStock(String code,Long userId){
        if (StringUtils.isEmpty(code) || !CodeUtil.isCode(code)){
            return AjaxResult.success("图书已失效，来晚一步了",false);
        }
        Long id = CodeUtil.getKey(code);
        if (StringUtils.isNull(id)){
            return AjaxResult.success("图书已失效，来晚一步了",false);
        }

        BookStock bookStock = bookStockMapper.selectBookStockById(id);
        if (StringUtils.isNull(bookStock)){
            return AjaxResult.success("图书已失效，来晚一步了",false);
        }
        String hasLoan = bookStock.getHasLoan();
        if (
                StringUtils.isEmpty(hasLoan)
                        || !HasLoanEnums.NORMAL.getDesc().equals(hasLoan)){
            return AjaxResult.success("来晚了，此书已被别人抢先借走了...",false);
        }

        BookStockBrowHistory history = browHistoryMapper.selectUserBrowedBook(userId,bookStock.getBookId());
        if (StringUtils.isNotNull(history) && StringUtils.isNotNull(history.getId())){
            return AjaxResult.success("图书借阅失败，你已经借过这本书还没归还呢！不能重复借阅同一本书！",false);
        }
        history = new BookStockBrowHistory();
        history.setId(history.getSnowID());
        history.setUserId(userId);
        history.setBookId(bookStock.getBookId());
        history.setCreatePersonal(userId);
        history.setStockId(bookStock.getId());
        history.setCreateTime(new Date());
        history.setHasBack(HasBrowedEnums.NOMAL.getDesc());
        history.setIsDel(DelEnums.NOT_DEL.getValue());
        history.setStatus(StatusEnums.ONE.getDesc());
        browHistoryMapper.insertBookStockBrowHistory(history);//插入借书记录

        bookStock.setHasLoan(HasLoanEnums.LOAN.getDesc());
        bookStock.setUpdatePersonal(userId);
        bookStock.setUpdateTime(new Date());
        bookStockMapper.loan(bookStock);//更新库存，设置此库位图书被借走，即图书不在库


        LibraryStock stock = new LibraryStock();
        stock.setId(bookStock.getStockLocaltion());
        stock.setHasBook(StockHasBookEnums.NO_BOOK_NOMAL.getDesc());
        stockMapper.updateLibraryStock(stock);

        BookStockLog log = new BookStockLog();
        log.setId(log.getSnowID());
        log.setStockId(bookStock.getId());
        log.setOperateType(BookStockOperatorType.ONLINE.getDesc());
        log.setIsDel(DelEnums.NOT_DEL.getValue());
        log.setIsLoan(LoanStyleEnums.BORROW_BOOK.getDesc());
        log.setCreatePersonal(userId);
        log.setUpdateTime(new Date());
        logMapper.insertBookStockLog(log);//添加借书记录

        return AjaxResult.success("图书借阅成功！",true);
    }

    /**
     * 新增图书库存信息
     * 
     * @param bookStock 图书库存信息
     * @return 结果
     */
    @Override
    public int insertBookStock(BookStock bookStock) {
        bookStock.setCreateTime(DateUtils.getNowDate());
        return bookStockMapper.insertBookStock(bookStock);
    }

    /**
     * 修改图书库存信息
     * 
     * @param bookStock 图书库存信息
     * @return 结果
     */
    @Override
    public int updateBookStock(BookStock bookStock) {
        bookStock.setUpdateTime(DateUtils.getNowDate());
        return bookStockMapper.updateBookStock(bookStock);
    }

    /**
     * 批量删除图书库存信息
     * 
     * @param ids 需要删除的图书库存信息主键
     * @return 结果
     */
    @Override
    public int deleteBookStockByIds(Long[] ids) {
        return bookStockMapper.deleteBookStockByIds(ids);
    }

    /**
     * 删除图书库存信息信息
     * 
     * @param id 图书库存信息主键
     * @return 结果
     */
    @Override
    public int deleteBookStockById(Long id) {
        return bookStockMapper.deleteBookStockById(id);
    }
}
