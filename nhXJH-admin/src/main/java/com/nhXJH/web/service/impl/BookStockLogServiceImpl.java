package com.nhXJH.web.service.impl;

import java.util.*;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nhXJH.common.core.domain.entity.SysUser;
import com.nhXJH.common.enums.application.StatusEnums;
import com.nhXJH.common.exception.ServiceException;
import com.nhXJH.common.utils.StringUtils;
import com.nhXJH.enums.DelEnums;
import com.nhXJH.system.mapper.SysUserMapper;
import com.nhXJH.web.domain.BookStock;
import com.nhXJH.web.domain.BookStockLog;
import com.nhXJH.common.utils.DateUtils;
import com.nhXJH.web.domain.vo.BookStockLogVO;
import com.nhXJH.web.mapper.BookStockMapper;
import com.nhXJH.web.util.collections.OperatorCollection;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nhXJH.web.mapper.BookStockLogMapper;
//import com.nhXJH.web.domain.BookStockLog;
import com.nhXJH.web.service.IBookStockLogService;

/**
 * 图书库存出入信息Service业务层处理
 * 
 * @author xjh
 * @date 2022-01-25
 */
@Service
public class BookStockLogServiceImpl extends ServiceImpl<BookStockLogMapper, BookStockLog>
        implements IBookStockLogService  {
    @Autowired
    private BookStockLogMapper bookStockLogMapper;
    @Autowired
    private BookStockMapper bookStockMapper;
    @Autowired
    private SysUserMapper userMapper;
    @Autowired
    private OperatorCollection operatorCollection;

    /**
     * 查询图书库存出入信息
     * 
     * @param id 图书库存出入信息主键
     * @return 图书库存出入信息
     */
    @Override
    public BookStockLog selectBookStockLogById(Long id) {
        return bookStockLogMapper.selectBookStockLogById(id);
    }

    @Override
    public Integer getCount(BookStockLog bookStockLog) {
        return bookStockLogMapper.getCount(bookStockLog);
    }

    /**
     * 查询图书库存出入信息
     *
     * @param stockId 库位ID
     * @return 图书库存出入信息
     */
    @Override
    public List<BookStockLogVO> selectBookStockLogVOById(Long stockId) {
        if (null == stockId){
            throw new ServiceException("访问异常");
        }
        List<BookStockLogVO> result = new ArrayList<>();
//        QueryWrapper<BookStockLog> query = new QueryWrapper<>();
//        query.eq("stock_id",stockId).eq("is_del", DelEnums.NOT_DEL.getValue()).orderByDesc("create_time");
        BookStock bookStock = new BookStock();
        bookStock.setStatus(StatusEnums.ONE.getDesc());
        bookStock.setIsDel(DelEnums.NOT_DEL.getValue());
        bookStock.setStockLocaltion(stockId);
        List<BookStock> stocks = bookStockMapper.selectBookStockList(bookStock);
        if (null == stocks ){
            return result;
        }
        if (stocks.size()>1){
            throw new ServiceException("访问异常");//查询结果过多，存在脏数据
        }
        if (stocks.size()<=0){
            return result;
        }
        bookStock = stocks.get(0);

        List<BookStockLog> list = bookStockLogMapper.selectLogsByStockId(bookStock.getId());

        if (null == list){
            return result;
        }

        Set<Long> uids = new HashSet<>();
        for (BookStockLog bookStockLog : list) {
            uids.add(bookStockLog.getCreatePersonal());
            uids.add(bookStockLog.getOtherPersonal());
            uids.add(bookStockLog.getUpdatePersonal());
        }
        Long[] ids = operatorCollection.toArray(uids);
        if (StringUtils.isNull(ids)){
            return result;
        }
        List<SysUser> users = userMapper.selectUserByIds(ids);
        Map<Long,SysUser> userMap = new HashMap<>();
        for (SysUser user : users) {
            userMap.put(user.getUserId(),user);
        }

        for (BookStockLog log : list) {
            result.add(new BookStockLogVO(
                    userMap.get(log.getCreatePersonal()),
                    userMap.get(log.getUpdatePersonal()),
                    userMap.get(log.getOtherPersonal()),
                    log
            ));
        }

        return result;
    }
    /**
     * 查询图书库存出入信息列表
     * 
     * @param bookStockLog 图书库存出入信息
     * @return 图书库存出入信息
     */
    @Override
    public List<BookStockLog> selectBookStockLogList(BookStockLog bookStockLog) {
        return bookStockLogMapper.selectBookStockLogList(bookStockLog);
    }

    /**
     * 新增图书库存出入信息
     * 
     * @param bookStockLog 图书库存出入信息
     * @return 结果
     */
    @Override
    public int insertBookStockLog(BookStockLog bookStockLog) {
        bookStockLog.setCreateTime(DateUtils.getNowDate());
        return bookStockLogMapper.insertBookStockLog(bookStockLog);
    }

    /**
     * 修改图书库存出入信息
     * 
     * @param bookStockLog 图书库存出入信息
     * @return 结果
     */
    @Override
    public int updateBookStockLog(BookStockLog bookStockLog) {
        bookStockLog.setUpdateTime(DateUtils.getNowDate());
        return bookStockLogMapper.updateBookStockLog(bookStockLog);
    }

    /**
     * 批量删除图书库存出入信息
     * 
     * @param ids 需要删除的图书库存出入信息主键
     * @return 结果
     */
    @Override
    public int deleteBookStockLogByIds(Long[] ids) {
        return bookStockLogMapper.deleteBookStockLogByIds(ids);
    }

    /**
     * 删除图书库存出入信息信息
     * 
     * @param id 图书库存出入信息主键
     * @return 结果
     */
    @Override
    public int deleteBookStockLogById(Long id) {
        return bookStockLogMapper.deleteBookStockLogById(id);
    }
}
