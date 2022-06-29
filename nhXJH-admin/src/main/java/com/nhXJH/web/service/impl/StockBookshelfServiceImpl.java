package com.nhXJH.web.service.impl;

import java.util.*;

import com.nhXJH.common.core.domain.entity.SysDept;
import com.nhXJH.common.core.domain.entity.SysUser;
import com.nhXJH.common.enums.application.StatusEnums;
import com.nhXJH.enums.DelEnums;
import com.nhXJH.system.domain.po.BaseBookClassCn;
import com.nhXJH.system.mapper.BaseBookClassCnMapper;
import com.nhXJH.system.mapper.SysDeptMapper;
import com.nhXJH.system.mapper.SysUserMapper;
import com.nhXJH.web.domain.LibraryBook;
import com.nhXJH.web.domain.StockBookshelf;
import com.nhXJH.common.utils.DateUtils;
import com.nhXJH.web.domain.vo.BookShelfVO;
import com.nhXJH.web.util.collections.OperatorCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nhXJH.web.mapper.StockBookshelfMapper;
//import com.nhXJH.web.domain.StockBookshelf;
import com.nhXJH.web.service.IStockBookshelfService;

/**
 * 书架信息Service业务层处理
 * 
 * @author xjh
 * @date 2022-01-25
 */
@Service
public class StockBookshelfServiceImpl extends ServiceImpl<StockBookshelfMapper, StockBookshelf>
        implements IStockBookshelfService  {
    @Autowired
    private StockBookshelfMapper stockBookshelfMapper;
    @Autowired
    private SysUserMapper userMapper;
    @Autowired
    private SysDeptMapper deptMapper;
    @Autowired
    private BaseBookClassCnMapper bookClassMapper;
    @Autowired
    private OperatorCollection operatorCollection;

    /**
     * 查询书架信息
     * 
     * @param id 书架信息主键
     * @return 书架信息
     */
    @Override
    public StockBookshelf selectStockBookshelfById(Long id) {
        return stockBookshelfMapper.selectStockBookshelfById(id);
    }

    /**
     * 查询书架信息列表
     * 
     * @param stockBookshelf 书架信息
     * @return 书架信息
     */
    @Override
    public List<StockBookshelf> selectStockBookshelfList(StockBookshelf stockBookshelf) {
        return stockBookshelfMapper.selectStockBookshelfList(stockBookshelf);
    }

    /**
     * 获取书架信息返回结果列表
     *
     * @param stockBookshelf 书架信息
     * @return 书架信息
     */
    @Override
    public List<BookShelfVO> selectStockBookshelfVOList(StockBookshelf stockBookshelf) {
        List<StockBookshelf> shelfList =  stockBookshelfMapper.selectStockBookshelfList(stockBookshelf);

        if(null == shelfList && shelfList.size()<=0){
            return null;
        }

        Set<Long> deptIdSet = new HashSet<>();
        Set<Long> userIdSet = new HashSet<>();
        Set<Long> bookClassIdSet = new HashSet<>();
        shelfList.forEach(shelf->{
            deptIdSet.add(shelf.getDept());
            userIdSet.add(shelf.getUpdatePersonal());
            userIdSet.add(shelf.getCreatePersonal());
            bookClassIdSet.add(shelf.getBookType());
        });

        /**获取部门基本信息*/
        Long[] queryIds = operatorCollection.toArray(deptIdSet);
        if (null==queryIds){
            queryIds = new Long[]{-1l};
        }
        Map<Long,SysDept> deptMap = new HashMap<>();
        List<SysDept> deptList = deptMapper.selectDeptByIds(queryIds);
        if (null != deptList) {
            deptList.forEach(dept -> deptMap.put(dept.getDeptId(), dept));
        }

        /**获取用户基本信息*/
        Map<Long,SysUser> userMap = new HashMap<>();
        queryIds = operatorCollection.toArray(userIdSet);
        if (null==queryIds){
            queryIds = new Long[]{-1l};
        }
        List<SysUser> userList = userMapper.selectUserByIds(queryIds);
        if (null != userList) {
            userList.forEach(user -> userMap.put(user.getUserId(), user));
        }

        /**获取图书类别基本信息*/
        Map<Long,BaseBookClassCn> bookClassMap = new HashMap<>();
        queryIds = operatorCollection.toArray(bookClassIdSet);
        if (null==queryIds){
            queryIds = new Long[]{-1l};
        }
        List<BaseBookClassCn> bookClassList = bookClassMapper.selectBookClassByIds(queryIds);
        if (null != bookClassList) {
            bookClassList.forEach(bookClass -> bookClassMap.put(bookClass.getId(), bookClass));
        }

        /**组装返回结果 */
        List<BookShelfVO> result = new ArrayList<>();
        shelfList.forEach(shelf->{
            SysDept dept = deptMap.get(shelf.getDept());
            SysUser createUser = userMap.get(shelf.getCreatePersonal());
            SysUser updateUser = userMap.get(shelf.getUpdatePersonal());
            BaseBookClassCn bookClass = bookClassMap.get(shelf.getBookType());
            BookShelfVO vo = new BookShelfVO(
                    createUser,
                    updateUser,
                    dept,
                    bookClass,
                    shelf
            );
            result.add(vo);
        });
        return result;
    }
    /**
     * 获取书架信息返回结果
     *
     * @param id 书架信息id
     * @return 书架信息
     */
    @Override
    public BookShelfVO selectStockBookshelfVOById(Long id) {
        StockBookshelf shelf =  stockBookshelfMapper.selectStockBookshelfById(id);

        if(null ==shelf){
            return null;
        }

        /**获取部门基本信息*/
        SysDept dept = deptMapper.selectDeptById(shelf.getDept());

        /**获取用户基本信息*/
        Set<Long> userIdSet = new HashSet<>();
        userIdSet.add(shelf.getCreatePersonal());
        userIdSet.add(shelf.getUpdatePersonal());
        Long[] queryIds = operatorCollection.toArray(userIdSet);
        if (null==queryIds){
            queryIds = new Long[]{-1l};
        }
        Map<Long,SysUser> userMap = new HashMap<>();
        queryIds = operatorCollection.toArray(userIdSet);
        List<SysUser> userList = userMapper.selectUserByIds(queryIds);
        if (null != userList) {
            userList.forEach(user -> userMap.put(user.getUserId(), user));
        }
        /**获取图书类别基本信息*/
        BaseBookClassCn bookClass = bookClassMapper.selectBaseBookClassCnById(shelf.getBookType());

        /**组装返回结果 */
        SysUser createUser = userMap.get(shelf.getCreatePersonal());
        SysUser updateUser = userMap.get(shelf.getUpdatePersonal());
        BookShelfVO result = new BookShelfVO(
                createUser,
                updateUser,
                dept,
                bookClass,
                shelf
        );
        return result;
    }
    /**
     * 查询书架总数
     *
     * @param stockBookshelf 书架信息
     * @return 书架信息总数
     */
    @Override
    public Long getCount(StockBookshelf stockBookshelf){
        return stockBookshelfMapper.selectCount(stockBookshelf);
    }
    /**
     * 新增书架信息
     * 
     * @param stockBookshelf 书架信息
     * @return 结果
     */
    @Override
    public int insertStockBookshelf(StockBookshelf stockBookshelf) {
        stockBookshelf.setCreateTime(DateUtils.getNowDate());
        stockBookshelf.setStatus(StatusEnums.ONE.getDesc());
        stockBookshelf.setIsDel(DelEnums.NOT_DEL.getValue());
        return stockBookshelfMapper.insertStockBookshelf(stockBookshelf);
    }

    /**
     * 修改书架信息
     * 
     * @param stockBookshelf 书架信息
     * @return 结果
     */
    @Override
    public int updateStockBookshelf(StockBookshelf stockBookshelf) {
        stockBookshelf.setUpdateTime(DateUtils.getNowDate());
        return stockBookshelfMapper.updateStockBookshelf(stockBookshelf);
    }

    /**
     * 批量删除书架信息
     * 
     * @param ids 需要删除的书架信息主键
     * @return 结果
     */
    @Override
    public List<StockBookshelf> deleteStockBookshelfByIds(Long[] ids) {
        List<StockBookshelf> hasBooksOnStock = stockBookshelfMapper.hasBookOnStock(ids);
        if (null != hasBooksOnStock || hasBooksOnStock.size()>0){
            return hasBooksOnStock;
        }
        stockBookshelfMapper.deleteStockBookshelfByIds(ids);
        return null;
    }

    /**
     * 删除书架信息信息
     * 
     * @param id 书架信息主键
     * @return 结果
     */
    @Override
    public List<StockBookshelf> deleteStockBookshelfById(Long id) {
        return this.deleteStockBookshelfByIds(new Long[]{id});
    }

    /**
     * 修改书架信息状态
     *
     * @param bookshelf 图书信息
     * @return 结果
     */
    public int updateUserStatus(StockBookshelf bookshelf){
        return stockBookshelfMapper.updateStockBookshelf(bookshelf);
    }
}
