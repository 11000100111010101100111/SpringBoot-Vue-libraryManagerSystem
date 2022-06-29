package com.nhXJH.web.service;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;
import com.nhXJH.web.domain.PurchaseLink;
import com.nhXJH.web.domain.PurchaseRequisition;

/**
 * 采购申请信息Service接口
 * 
 * @author xjh
 * @date 2022-01-25
 */
public interface IPurchaseRequisitionService extends IService<PurchaseRequisition>{
    /**
     * 查询采购申请信息
     * 
     * @param id 采购申请信息主键
     * @return 采购申请信息
     */
    public PurchaseRequisition selectPurchaseRequisitionById(Long id);
    public Integer getCount(PurchaseRequisition purchaseRequisition);
    /**
     * 查询采购申请信息列表
     * 
     * @param purchaseRequisition 采购申请信息
     * @return 采购申请信息集合
     */
    public List<PurchaseRequisition> selectPurchaseRequisitionList(PurchaseRequisition purchaseRequisition);

    /**
     * 新增采购申请信息
     * 
     * @param purchaseRequisition 采购申请信息
     * @return 结果
     */
    public int insertPurchaseRequisition(PurchaseRequisition purchaseRequisition);

    /**
     * 修改采购申请信息
     * 
     * @param purchaseRequisition 采购申请信息
     * @return 结果
     */
    public int updatePurchaseRequisition(PurchaseRequisition purchaseRequisition);

    /**
     * 批量删除采购申请信息
     * 
     * @param ids 需要删除的采购申请信息主键集合
     * @return 结果
     */
    public int deletePurchaseRequisitionByIds(Long[] ids);

    /**
     * 删除采购申请信息信息
     * 
     * @param id 采购申请信息主键
     * @return 结果
     */
    public int deletePurchaseRequisitionById(Long id);
}
