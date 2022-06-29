package com.nhXJH.web.service.impl;

import java.util.*;

import com.nhXJH.common.core.domain.entity.SysUser;
import com.nhXJH.common.enums.application.StatusEnums;
import com.nhXJH.common.enums.application.TableTypeEnums;
import com.nhXJH.common.exception.ServiceException;
import com.nhXJH.enums.DelEnums;
import com.nhXJH.enums.StockHasBookEnums;
import com.nhXJH.system.domain.po.BaseBookClassCn;
import com.nhXJH.system.mapper.BaseBookClassCnMapper;
import com.nhXJH.system.mapper.SysUserMapper;
import com.nhXJH.web.domain.*;
import com.nhXJH.common.utils.DateUtils;
import com.nhXJH.web.domain.param.LibraryStockMoreParam;
import com.nhXJH.web.domain.vo.BaseFileClassify;
import com.nhXJH.web.domain.vo.StockVO;
import com.nhXJH.web.mapper.*;
import com.nhXJH.web.util.collections.OperatorCollection;
import com.nhXJH.web.util.file.FileUploadUtils;
import io.swagger.annotations.ApiModelProperty;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
//import com.nhXJH.web.domain.LibraryStock;
import com.nhXJH.web.service.ILibraryStockService;

/**
 * 库位信息Service业务层处理
 * 
 * @author xjh
 * @date 2022-01-25
 */
@Service
public class LibraryStockServiceImpl extends ServiceImpl<LibraryStockMapper, LibraryStock>
        implements ILibraryStockService  {
    @Autowired
    private LibraryStockMapper libraryStockMapper;
    @Autowired
    private StockBookshelfMapper bookshelfMapper;
    @Autowired
    private StockStoreyMapper storeyMapper;
    @Autowired
    private SysUserMapper userMapper;
    @Autowired
    private BookStockMapper bookStockMapper;
    @Autowired
    private LibraryBookMapper libraryBookMapper;
    @Autowired
    private LibraryAuthMapper authMapper;
    @Autowired
    private BaseBookClassCnMapper bookClassCnMapper;
    @Autowired
    private LibraryPressMapper pressMapper;
    @Autowired
    private BaseFileMapper fileMapper;
    @Autowired
    private BookStockLogMapper bookStockLogMapper;
    @Autowired
    private OperatorCollection operatorCollection;

    /**
     * 查询库位信息
     * 
     * @param id 库位信息主键
     * @return 库位信息
     */
    @Override
    public LibraryStock selectLibraryStockById(Long id) {
        return libraryStockMapper.selectLibraryStockById(id);
    }

    @Override
    public Integer getCount(LibraryStock libraryStock) {
        return libraryStockMapper.getCount(libraryStock);
    }

    /**
     * 查询库位相关信息
     *
     * @param id 库位信息主键
     * @return 库位信息
     */
    public StockVO selectLibraryStockVOById(Long id){
        if (null == id){
            throw new ServiceException("库位选择异常");
        }
        StockVO result = new StockVO();

        Set<Long> uIds = new HashSet<>();

        LibraryStock libraryStock = libraryStockMapper.selectLibraryStockById(id);//库位信息
        BookStock bookStock = null;
        if (null != libraryStock) {
            uIds.add(libraryStock.getCreatePersonal());
            uIds.add(libraryStock.getUpdatePersonal());
            bookStock = bookStockMapper.selectBookStockByStockId(libraryStock.getId());//图书库存信息
        }


        LibraryBook book = null;
        BookStockLog log = null;
        if (null != bookStock) {

            //获取最后一条图书出库信息
            log = bookStockLogMapper.selectByStockId(bookStock.getId());
            if (log != null) {
                uIds.add(log.getCreatePersonal());
            }

            uIds.add(bookStock.getCreatePersonal());
            uIds.add(bookStock.getUpdatePersonal());
            book = libraryBookMapper.selectLibraryBookById(bookStock.getBookId());//图书实体信息
        }


        LibraryAuth auth = null;//图书作者信息
        BaseBookClassCn bookType = null;//图书类别信息
        LibraryPress press = null;//图书出版社信息
        List<BaseFile> files = new ArrayList<>();//图书实体相关附件信息
        if (null != book) {
            auth = authMapper.selectLibraryAuthById(book.getAuthor());//图书作者信息
            bookType = bookClassCnMapper.selectBaseBookClassCnById(book.getClaz());//图书类别信息
            press = pressMapper.selectLibraryPressById(book.getMechanism());//图书出版社信息

            BaseFile query = new BaseFile();
            query.setModelId(book.getId());
            query.setDbId(TableTypeEnums.BOOK.getCode());
            files = fileMapper.selectBaseFileList(query);//图书实体相关附件信息
        }

        List<SysUser> userList = userMapper.selectUserByIds(operatorCollection.toArray(uIds));
        Map<Long,SysUser> userMap = new HashMap<>();
        userList.forEach(u-> userMap.put(u.getUserId(),u));
        if (null != libraryStock) {
            SysUser createStockU = userMap.get(libraryStock.getCreatePersonal());
            SysUser updateStockU = userMap.get(libraryStock.getUpdatePersonal());
            result.setCreateStockU(createStockU);
            result.setUpdateStockU(updateStockU);
        }


        if (null != bookStock) {
            SysUser createStockBookU = userMap.get(bookStock.getCreatePersonal());
            SysUser updateStockBookU = userMap.get(bookStock.getUpdatePersonal());
            result.setCreateStockBookU(createStockBookU);
            result.setUpdateStockBookU(updateStockBookU);
        }
        if (null != log) {
            SysUser useBookU = userMap.get(log.getCreatePersonal());
            result.setUseBookU(useBookU);
        }
        BaseFileClassify fileClassify = new BaseFileClassify(files);

//        result.setAuth(null == auth?new LibraryAuth():auth);
//        result.setBook(null == book?new LibraryBook():book);
//        result.setBookStock(null == bookStock?new BookStock():bookStock);
//        result.setBookType(null == bookType?new BaseBookClassCn():bookType);
//        result.setStock(null == libraryStock?new LibraryStock():libraryStock);
//        result.setMechanism(null == mechanism? new LibraryMechanism():mechanism);
        result.setAuth(auth);
        result.setBook(book);
        result.setBookStock(bookStock);
        result.setBookType(bookType);
        result.setStock(libraryStock);
        result.setPress(press);
        result.setFile(null == fileClassify? new BaseFileClassify():fileClassify);
        return result;
    }
    /**
     * 查询库位信息列表
     * 
     * @param libraryStock 库位信息
     * @return 库位信息
     */
    @Override
    public List<LibraryStock> selectLibraryStockList(LibraryStock libraryStock) {
        libraryStock.setIsDel(DelEnums.NOT_DEL.getValue());
        List<LibraryStock> list = libraryStockMapper.selectLibraryStockList(libraryStock);
        list.forEach(stock->stock.setUrl(FileUploadUtils.getFilePath(stock.getUrl())));
        return list;
    }

    /**
     * 查询库位信息列表
     *
     * @return 库位信息集合
     */
    public List<LibraryStock> getStockList(Long bookType,Long mechanism){
        return libraryStockMapper.getStockList(StockHasBookEnums.NO_BOOK_NOMAL.getDesc(),StatusEnums.ONE.getDesc(),DelEnums.NOT_DEL.getValue(),bookType,mechanism);
    }

    /**
     * 新增库位信息
     * 
     * @param libraryStock 库位信息
     * @return 结果
     */
    @Override
    public int insertLibraryStock(LibraryStock libraryStock) {
        libraryStock.setCreateTime(DateUtils.getNowDate());
        Long storeId = libraryStock.getStoreId();
        if (null == storeId || storeId<1){
            throw new ServiceException("填写内容有误(X01101)");
        }
        StockStorey storey = storeyMapper.selectStockStoreyById(storeId);
        if (null == storey){
            throw new ServiceException("操作异常");
        }
        StockBookshelf bookshelf = bookshelfMapper.selectStockBookshelfById(storey.getBookshelf());
        if (null == bookshelf){
            throw new ServiceException("操作异常");
        }
        libraryStock.setMechanism(bookshelf.getDept());
        libraryStock.setIsDel(StatusEnums.ZERO.getDesc());
        libraryStock.setHasBook(StockHasBookEnums.NO_BOOK_NOMAL.getDesc());//默认这个库位未存放书籍
        return libraryStockMapper.insertLibraryStock(libraryStock);
    }

    /**
     * 批量新增库位信息
     *
     * @param libraryStock 库位信息
     * @return 结果
     */
    public int addMore(LibraryStockMoreParam libraryStock){
        List<LibraryStock> list = new ArrayList<>();
        if (null == libraryStock.getNumber()){
            throw new ServiceException("批量新增数量不能为空！");
        }
        Integer number = libraryStock.getNumber();
        if (number<1){
            throw new ServiceException("批量新增数量不能小于0！");
        }

        libraryStock.setCreateTime(DateUtils.getNowDate());
        Long storeId = libraryStock.getStoreId();
        if (null == storeId || storeId<1){
            throw new ServiceException("填写内容有误(X01101)");
        }
        StockStorey storey = storeyMapper.selectStockStoreyById(storeId);
        if (null == storey){
            throw new ServiceException("操作异常");
        }
        StockBookshelf bookshelf = bookshelfMapper.selectStockBookshelfById(storey.getBookshelf());
        if (null == bookshelf){
            throw new ServiceException("操作异常");
        }
        libraryStock.setMechanism(bookshelf.getDept());
        libraryStock.setIsDel(StatusEnums.ZERO.getDesc());
        libraryStock.setHasBook(StockHasBookEnums.NO_BOOK_NOMAL.getDesc());//默认这个库位未存放书籍
        int index = 1;
        while (number>=index){
            LibraryStock stock = new LibraryStock();
            BeanUtils.copyProperties(libraryStock,stock);
            stock.setId(stock.getSnowID());
            int num = Math.round(0)+10;
            stock.setStockCode(stock.getStockCode()+"_"+num+"_#"+index);
            stock.setLocaltion(stock.getLocaltion()+"_"+num+"_#"+index);
            list.add(stock);
            index++;
        }

        return libraryStockMapper.insertMoreLibraryStock(list);
    }

    /**
     * 修改库位信息
     * 
     * @param libraryStock 库位信息
     * @return 结果
     */
    @Override
    public int updateLibraryStock(LibraryStock libraryStock) {
        libraryStock.setUpdateTime(DateUtils.getNowDate());
        return libraryStockMapper.updateLibraryStock(libraryStock);
    }

    /**
     * 批量删除库位信息
     * 
     * @param ids 需要删除的库位信息主键
     * @return 结果
     */
    @Override
    public int deleteLibraryStockByIds(Long[] ids,Long operator) {
        return libraryStockMapper.deleteLibraryStockByIds(ids,operator);
    }

    /**
     * 删除库位信息信息
     * 
     * @param id 库位信息主键
     * @return 结果
     */
    @Override
    public int deleteLibraryStockById(Long id,Long operator) {
        return libraryStockMapper.deleteLibraryStockById(id,operator);
    }
}
