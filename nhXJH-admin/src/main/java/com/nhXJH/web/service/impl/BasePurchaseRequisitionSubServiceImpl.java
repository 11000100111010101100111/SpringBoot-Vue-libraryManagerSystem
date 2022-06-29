package com.nhXJH.web.service.impl;

import java.util.*;

import com.nhXJH.common.core.domain.entity.SysDept;
import com.nhXJH.common.enums.application.TableTypeEnums;
import com.nhXJH.common.exception.ServiceException;
import com.nhXJH.common.utils.DateUtils;
import com.nhXJH.enums.*;
import com.nhXJH.system.domain.po.BaseBookClassCn;
import com.nhXJH.system.mapper.BaseBookClassCnMapper;
import com.nhXJH.system.mapper.SysDeptMapper;
import com.nhXJH.web.domain.*;
import com.nhXJH.web.domain.vo.InStockDetailVO;
import com.nhXJH.web.domain.vo.InStockVO;
import com.nhXJH.web.mapper.*;
import com.nhXJH.web.service.IBasePurchaseRequisitionSubService;
import com.nhXJH.web.util.collections.OperatorCollection;
import com.nhXJH.web.util.file.FileUploadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;

/**
 * 采购申请子Service业务层处理
 * 
 * @author xjh
 * @date 2022-02-28
 */
@Service
public class BasePurchaseRequisitionSubServiceImpl extends ServiceImpl<BasePurchaseRequisitionSubMapper, BasePurchaseRequisitionSub>
        implements IBasePurchaseRequisitionSubService {
    @Autowired
    private BasePurchaseRequisitionSubMapper basePurchaseRequisitionSubMapper;
    @Autowired
    private BaseFileMapper fileMapper;
    @Autowired
    private SysDeptMapper deptMapper;
    @Autowired
    private LibraryBookMapper bookMapper;
    @Autowired
    private BasePurchaseRequisitionMapper requisitionMapper;
    @Autowired
    private LibraryStockMapper stockMapper;
    @Autowired
    private BookStockMapper bookStockMapper;
    @Autowired
    private BookStockLogMapper bookStockLogMapper;
    @Autowired
    private StockStoreyMapper storeyMapper;
    @Autowired
    private StockBookshelfMapper shelfMapper;
    @Autowired
    private BaseBookClassCnMapper clazMapper;


    @Autowired
    private OperatorCollection operatorCollection;
    /**
     *
     * 查询采购申请子
     * 
     * @param id 采购申请子主键
     * @return 采购申请子
     */
    @Override
    public BasePurchaseRequisitionSub selectBasePurchaseRequisitionSubById(Long id) {
        return basePurchaseRequisitionSubMapper.selectBasePurchaseRequisitionSubById(id);
    }

    @Override
    public Integer getCount(BasePurchaseRequisitionSub basePurchaseRequisitionSub) {
        return basePurchaseRequisitionSubMapper.getCount(basePurchaseRequisitionSub);
    }

    /**
     * 查询采购申请子列表
     * 
     * @param basePurchaseRequisitionSub 采购申请子
     * @return 采购申请子
     */
    @Override
    public List<BasePurchaseRequisitionSub> selectBasePurchaseRequisitionSubList(BasePurchaseRequisitionSub basePurchaseRequisitionSub) {
        return basePurchaseRequisitionSubMapper.selectBasePurchaseRequisitionSubList(basePurchaseRequisitionSub);
    }

    /**
     * 查询采购申请子列表
     *
     * @param basePurchaseRequisitionSub 采购申请子
     * @return 采购申请子集合
     */
    public List<BasePurchaseRequisitionSub> selectSubList(BasePurchaseRequisitionSub basePurchaseRequisitionSub){
        return basePurchaseRequisitionSubMapper.selectSubList(basePurchaseRequisitionSub);
    }

    /**
     * 图书自动入库
     *
     * @param subPurchase 采购申请子表信息
     * @return 采购申请子集合
     */
    @Override
    @Transactional
    public InStockVO inStock(BasePurchaseRequisitionSub subPurchase) {
        InStockVO result = new InStockVO();
        if (null == subPurchase){
            throw new ServiceException("信息提交有误！");
        }
        Long operator = subPurchase.getUpdatePersonal();//我的id

        Long bookId = subPurchase.getEntityId();
        if (null == bookId){
            throw new ServiceException("信息提交有误！");
        }
        //获取待入库图书
        LibraryBook book = bookMapper.selectLibraryBookById(bookId);

        if (null== book){
            throw new ServiceException("信息提交有误:找不到图书信息！");
        }
        result.setBook(book);
        result.setClaz(clazMapper.selectBaseBookClassCnById(book.getClaz()));
        BaseFile queryFile = new BaseFile();
        queryFile.setType(FileTypeEnums.PICTURE.getValue());
        queryFile.setModelId(bookId);
        queryFile.setDbId(TableTypeEnums.BOOK.getCode());
        queryFile.setStatus(StatusEnums.ONE.getDesc());
        List<BaseFile> fileList = fileMapper.selectBaseFileList(queryFile);
        String stockPageImg = FileUploadUtils.DEFAULT_IMG_PATH;
        if (null!=fileList&& fileList.size()>0){
            result.setBookFile(fileList);
            stockPageImg = fileList.get(0).getUrl();
        }

        //获取当前入库图书的申请部门
        Long purchaseId = subPurchase.getGroupId();
        if (null == purchaseId){
            throw new ServiceException("信息提交有误:找不到采购申请信息！");
        }
        BasePurchaseRequisition requisition = requisitionMapper.selectBasePurchaseRequisitionById(purchaseId);
        //找到当前申请部门对应的符合当前图书类别的库位信息列表
        if (null == requisition ){
            throw new ServiceException("信息提交有误:找不到采购申请信息！");
        }

        if (null == requisition.getApproveDept()){
            throw new ServiceException("信息提交有误:采购申请缺少部门信息，失效的采购申请！");
        }

        Long approveDeptId =requisition.getApproveDept();
        SysDept approveDept = deptMapper.selectDeptById(approveDeptId);
        result.setApproveDept(approveDept);
        List<LibraryStock> stockList = stockMapper.getStockList(
                StockHasBookEnums.NO_BOOK_NOMAL.getDesc(),
                StatusEnums.ONE.getDesc(),
                DelEnums.NOT_DEL.getValue(),
                book.getClaz(),//申请入库图书类别
                approveDeptId//申请图书部门ID
        );
        if (null == stockList || stockList.size()<1){
            result.setMsg("入库失败：找不到合适的库位或库位信息不足！");
            result.setStatus(InStockStatus.ERROR.getStatus());
            return result;
        }

        //计算库位数目与待入库图书的数目
        int inStockNum = stockList.size();//可入库数量

        int needNum = (int)(subPurchase.getPurchaseNumber() - subPurchase.getPurchaseOverNumber());
        int inNum = Math.min(needNum, inStockNum);//本次需要入库数
        //修改库位状态
        Long [] stockIds = new Long[inNum];
        for (int i = 0;i<inNum;i++){
            stockIds[i] = stockList.get(i).getId();
        }
        LibraryStock query = new LibraryStock();
        query.setUpdatePersonal(operator);
        query.setHasBook(StockHasBookEnums.HAS_BOOK.getDesc());
        query.setUrl(stockPageImg);//设置当前库位封面为图书第一张图片
        int inStockNumUpdate = stockMapper.inStock(query,stockIds);
        if (inNum != inStockNumUpdate){
            throw new ServiceException("入库失败：数据操作异常！");
        }

        //依次将图书填入库位(新增图书库位实体)
        List<BookStock> inStockBookList = new ArrayList<>();
        for (int index=0;index<stockIds.length;index++) {
            BookStock bookStock = new BookStock();
            bookStock.setId(bookStock.getSnowID());
            bookStock.setCreateTime(new Date());
            bookStock.setIsDel(DelEnums.NOT_DEL.getValue());
            bookStock.setBookId(bookId);
            bookStock.setEntityCode(book.getCode()+"_"+bookStock.getSnowID()+"_"+(index+1));
            bookStock.setStockLocaltion(stockIds[index]);
            bookStock.setHasLoan(LoanStyleEnums.DEFAULT.getDesc());
            bookStock.setCreatePersonal(operator);
            bookStock.setStatus(StatusEnums.ONE.getDesc());
            bookStock.setMechanismId(requisition.getApproveDept());
            inStockBookList.add(bookStock);
        }
        int bookInStockNum = bookStockMapper.bookInStock(inStockBookList);
        if (bookInStockNum != inNum){
            throw new ServiceException("入库失败：数据操作异常！");
        }
        // 新增图书库存表记录（新增入库记录）
        List<BookStockLog> bookStockLogList = new ArrayList<>();
        for (BookStock bookStock : inStockBookList) {
            BookStockLog log = new BookStockLog();
            log.setId(log.getSnowID());
            log.setStockId(bookStock.getId());
            log.setIsLoan(LoanStyleEnums.WAREHOUSING.getDesc());
            log.setOperateType(BookStockOperatorType.ONLINE.getDesc());//线上操作
            log.setIsDel(DelEnums.NOT_DEL.getValue());
            log.setCreateTime(new Date());
            log.setCreatePersonal(operator);
            log.setOtherPersonal(operator);
            bookStockLogList.add(log);
        }
        bookStockLogMapper.insertLog(bookStockLogList);

        //修改入库单已入库数目
        BasePurchaseRequisitionSub querySub = new BasePurchaseRequisitionSub();
        querySub.setId(subPurchase.getId());
        Long overNum = subPurchase.getPurchaseOverNumber()+inNum;
        querySub.setPurchaseOverNumber(overNum);
        querySub.setUpdateTime(new Date());
        querySub.setUpdatePersonal(operator);

        basePurchaseRequisitionSubMapper.updateBasePurchaseRequisitionSub(querySub);

        subPurchase = basePurchaseRequisitionSubMapper.selectBasePurchaseRequisitionSubById(purchaseId);
        result.setSubPurchase(subPurchase);

        //@TODO 判断采购申请单是否完全被处理，并修改处理状态为已处理
        querySub = new BasePurchaseRequisitionSub();
        querySub.setGroupId(purchaseId);
        querySub.setStatus(StatusEnums.ONE.getDesc());
        List<BasePurchaseRequisitionSub> subList = basePurchaseRequisitionSubMapper.selectBasePurchaseRequisitionSubList(querySub);
        boolean isOVerPurchase = Boolean.TRUE;
        for (BasePurchaseRequisitionSub sub : subList) {
            if (sub.getPurchaseNumber()>sub.getPurchaseOverNumber()){
                isOVerPurchase = Boolean.FALSE;
                break;
            }
        }
        if (isOVerPurchase){
            BasePurchaseRequisition queryRequisition = new BasePurchaseRequisition();
            queryRequisition.setId(purchaseId);
            queryRequisition.setPurchaseStatus(PurchaseNodeStatusEnums.HAS_DO.getDesc());
            requisitionMapper.updateBasePurchaseRequisition(queryRequisition);
        }

        //获取当前详细的被入库的库位信息
        List<InStockDetailVO> stockVOList = new ArrayList<>();

        //@TODO 根据库位id数组查找库位列表，加入到<id,库位>集合
        List<LibraryStock> stocks = stockMapper.getStockListByIds(stockIds);
        if (null == stocks){
            throw new ServiceException("入库失败：查询结果异常！");
        }

        //@TODO 根据库位id数组查找书架层列表，加入到<id,书架层>集合
        Set<Long> storeyIdSet = new HashSet<>();
        for (LibraryStock stock : stocks) {
            storeyIdSet.add(stock.getStoreId());
        }
        Map<Long,StockStorey> storeyMap = new HashMap<>();
        Set<Long> shelfIds = new HashSet<>();
        if (storeyIdSet.size()>0) {
            List<StockStorey> storeyList = storeyMapper.getStoreyList(operatorCollection.toArray(storeyIdSet));
            if (null != stockList) {
                for (int index = 0; index < storeyList.size(); index++) {
                    StockStorey item = storeyList.get(index);
                    storeyMap.put(item.getId(), item);
                    shelfIds.add( item.getBookshelf() );
                }
            }
        }
        //@TODO 根据书架层id数组查找书架列表，加入到<id,书架>集合
        Map<Long,StockBookshelf> shelfMap = new HashMap<>();
        if (shelfIds.size()>0){
            List<StockBookshelf> shelfList = shelfMapper.selectStockBookshelfByIds(operatorCollection.toArray(shelfIds));
            if (null!=shelfList){
                for (StockBookshelf shelf : shelfList) {
                    shelfMap.put(shelf.getId(),shelf);
                }
            }
        }
        //@TODO 遍历库位id列表，封装结果集
        for (LibraryStock stock : stocks) {
            InStockDetailVO detailVO = new InStockDetailVO();

            detailVO.setStock(stock);

            StockStorey inStorey = storeyMap.get(stock.getStoreId());
            detailVO.setStorey(inStorey);

            detailVO.setShelf( shelfMap.get(inStorey.getBookshelf()));

            stockVOList.add(detailVO);
        }
        result.setStockList(stockVOList);
        result.setMsg(InStockStatus.SUCCEED.getMessage());
        result.setStatus(InStockStatus.SUCCEED.getStatus());
        return result;
    }

    /**
     * 新增采购申请子
     * 
     * @param basePurchaseRequisitionSub 采购申请子
     * @return 结果
     */
    @Override
    public int insertBasePurchaseRequisitionSub(BasePurchaseRequisitionSub basePurchaseRequisitionSub) {
        basePurchaseRequisitionSub.setCreateTime(DateUtils.getNowDate());
        return basePurchaseRequisitionSubMapper.insertBasePurchaseRequisitionSub(basePurchaseRequisitionSub);
    }

    /**
     * 修改采购申请子
     * 
     * @param basePurchaseRequisitionSub 采购申请子
     * @return 结果
     */
    @Override
    public int updateBasePurchaseRequisitionSub(BasePurchaseRequisitionSub basePurchaseRequisitionSub) {
        basePurchaseRequisitionSub.setUpdateTime(DateUtils.getNowDate());
        return basePurchaseRequisitionSubMapper.updateBasePurchaseRequisitionSub(basePurchaseRequisitionSub);
    }

    /**
     * 批量删除采购申请子
     * 
     * @param ids 需要删除的采购申请子主键
     * @return 结果
     */
    @Override
    public int deleteBasePurchaseRequisitionSubByIds(Long[] ids,Long operator) {
        return basePurchaseRequisitionSubMapper.deleteBasePurchaseRequisitionSubByIds(ids,operator);
    }

    /**
     * 删除采购申请子信息
     * 
     * @param basePurchaseRequisitionSub 采购申请子主键
     * @return 结果
     */
    @Override
    public int delByEntityForAnd(BasePurchaseRequisitionSub basePurchaseRequisitionSub) {
        return basePurchaseRequisitionSubMapper.delByEntityForAnd(basePurchaseRequisitionSub);
    }
}
