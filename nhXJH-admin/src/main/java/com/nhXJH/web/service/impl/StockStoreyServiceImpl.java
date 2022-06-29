package com.nhXJH.web.service.impl;

import java.util.*;

import com.nhXJH.common.core.domain.entity.SysUser;
import com.nhXJH.common.exception.ServiceException;
import com.nhXJH.system.domain.po.BaseBookClassCn;
import com.nhXJH.system.mapper.BaseBookClassCnMapper;
import com.nhXJH.system.mapper.SysUserMapper;
import com.nhXJH.web.domain.StockBookshelf;
import com.nhXJH.web.domain.StockStorey;
import com.nhXJH.common.utils.DateUtils;
import com.nhXJH.web.domain.vo.StoreyVO;
import com.nhXJH.web.mapper.StockBookshelfMapper;
import com.nhXJH.web.util.collections.OperatorCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nhXJH.web.mapper.StockStoreyMapper;
import com.nhXJH.web.service.IStockStoreyService;

/**
 * 书架层信息Service业务层处理
 * 
 * @author xjh
 * @date 2022-01-25
 */
@Service
public class StockStoreyServiceImpl extends ServiceImpl<StockStoreyMapper, StockStorey>
        implements IStockStoreyService  {
    @Autowired
    private StockStoreyMapper stockStoreyMapper;
    @Autowired
    private OperatorCollection operatorCollection;
    @Autowired
    private StockBookshelfMapper stockBookshelfMapper;
    @Autowired
    private SysUserMapper userMapper;
    @Autowired
    private BaseBookClassCnMapper bookClassMapper;

    /**
     * 查询书架层信息
     * 
     * @param id 书架层信息主键
     * @return 书架层信息
     */
    @Override
    public StockStorey selectStockStoreyById(Long id) {
        return stockStoreyMapper.selectStockStoreyById(id);
    }

    @Override
    public Integer getCount(StockStorey stockStorey) {
        return stockStoreyMapper.getCount(stockStorey);
    }

    /**
     * 查询书架层信息列表
     * 
     * @param stockStorey 书架层信息
     * @return 书架层信息
     */
    @Override
    public List<StockStorey> selectStockStoreyList(StockStorey stockStorey) {
        return stockStoreyMapper.selectStockStoreyList(stockStorey);
    }
    /**
     * 查询书架层结果集信息列表
     *
     * @param stockStorey 书架层信息
     * @return 书架层信息集合
     */
    public List<StoreyVO> selectStockStoreyVOList(StockStorey stockStorey){
        List<StockStorey> list = stockStoreyMapper.selectStockStoreyList(stockStorey);
        List<StoreyVO> result = new ArrayList<>();
        if (null == list || list.size()<=0){
            return result;
        }

        Set<Long> bookClassIdSet = new HashSet<>();
        Set<Long> userIdSet = new HashSet<>();
        Set<Long> bookShelfIdSet = new HashSet<>();
        list.forEach(storey->{
            bookClassIdSet.add(storey.getBookType());
            userIdSet.add(storey.getUpdatePersonal());
            userIdSet.add(storey.getCreatePersonal());
            bookShelfIdSet.add(storey.getBookshelf());
        });


        /**获取用户基本信息*/
        Map<Long, SysUser> userMap = new HashMap<>();
        Long[] queryIds = operatorCollection.toArray(userIdSet);
        if (null==queryIds){
            queryIds = new Long[]{-1l};
        }
        List<SysUser> userList = userMapper.selectUserByIds(queryIds);
        if (null != userList) {
            userList.forEach(user -> userMap.put(user.getUserId(), user));
        }

        /**获取图书类别基本信息*/
        Map<Long, BaseBookClassCn> bookClassMap = new HashMap<>();
        queryIds = operatorCollection.toArray(bookClassIdSet);
        if (null==queryIds){
            queryIds = new Long[]{-1l};
        }
        List<BaseBookClassCn> bookClassList = bookClassMapper.selectBookClassByIds(queryIds);
        if (null != bookClassList) {
            bookClassList.forEach(bookClass -> bookClassMap.put(bookClass.getId(), bookClass));
        }

        /**获取书架层基本信息*/
        Map<Long, StockBookshelf> bookShelfMap = new HashMap<>();
        queryIds = operatorCollection.toArray(bookShelfIdSet);
        if (null==queryIds){
            queryIds = new Long[]{-1l};
        }

        List<StockBookshelf> bookShelfList = stockBookshelfMapper.selectStockBookshelfByIds(queryIds);
        if (null != bookShelfList) {
            bookShelfList.forEach(shelf -> bookShelfMap.put(shelf.getId(), shelf));
        }

        /**组装返回结果 */
        list.forEach(storey->{
            StockBookshelf bookshelf = bookShelfMap.get(storey.getBookshelf());
            SysUser createUser = userMap.get(storey.getCreatePersonal());
            SysUser updateUser = userMap.get(storey.getUpdatePersonal());
            BaseBookClassCn bookClass = bookClassMap.get(storey.getBookType());
            StoreyVO vo = new StoreyVO(
                    createUser,
                    updateUser,
                    bookClass,
                    bookshelf,
                    storey
            );
            result.add(vo);
        });
        return result;
    }
    /**
     * 新增书架层信息
     * 
     * @param stockStorey 书架层信息
     * @return 结果
     */
    @Override
    public int insertStockStorey(StockStorey stockStorey) {
        stockStorey.setCreateTime(DateUtils.getNowDate());
        return stockStoreyMapper.insertStockStorey(stockStorey);
    }

    /**
     * 修改书架层信息
     * 
     * @param stockStorey 书架层信息
     * @return 结果
     */
    @Override
    public int updateStockStorey(StockStorey stockStorey) {
        stockStorey.setUpdateTime(DateUtils.getNowDate());
        return stockStoreyMapper.updateStockStorey(stockStorey);
    }

    /**
     * 批量删除书架层信息
     * 
     * @param ids 需要删除的书架层信息主键
     * @return 结果
     */
    @Override
    public List<StockStorey> deleteStockStoreyByIds(Long[] ids) {
        List<StockStorey> hasBookStorey = stockStoreyMapper.hasBooks(ids);
        if (null!=hasBookStorey && hasBookStorey.size()>0){
            return hasBookStorey;
        }
        stockStoreyMapper.deleteStockStoreyByIds(ids);
        return null;
    }

    /**
     * 删除书架层信息信息
     * 
     * @param id 书架层信息主键
     * @return 结果
     */
    @Override
    public int deleteStockStoreyById(Long id) {
        if (this.hasBook(id)>0){
            throw new ServiceException("当前书架层已存放相关图书，无法删除");
        }
        return stockStoreyMapper.deleteStockStoreyById(id);
    }

    @Override
    public Integer hasBook(Long id) {
        return stockStoreyMapper.hasBook(id);
    }
}
