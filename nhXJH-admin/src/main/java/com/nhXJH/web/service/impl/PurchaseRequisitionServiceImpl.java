package com.nhXJH.web.service.impl;

import java.util.List;

import com.nhXJH.web.domain.PurchaseRequisition;
import com.nhXJH.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nhXJH.web.mapper.PurchaseRequisitionMapper;
//import com.nhXJH.web.domain.PurchaseRequisition;
import com.nhXJH.web.service.IPurchaseRequisitionService;

/**
 * 采购申请信息Service业务层处理
 * 
 * @author xjh
 * @date 2022-01-25
 */
@Service
public class PurchaseRequisitionServiceImpl extends ServiceImpl<PurchaseRequisitionMapper, PurchaseRequisition>
        implements IPurchaseRequisitionService  {
    @Autowired
    private PurchaseRequisitionMapper purchaseRequisitionMapper;

    /**
     * 查询采购申请信息
     * 
     * @param id 采购申请信息主键
     * @return 采购申请信息
     */
    @Override
    public PurchaseRequisition selectPurchaseRequisitionById(Long id) {
        return purchaseRequisitionMapper.selectPurchaseRequisitionById(id);
    }

    @Override
    public Integer getCount(PurchaseRequisition purchaseRequisition) {
        return purchaseRequisitionMapper.getCount(purchaseRequisition);
    }

    /**
     * 查询采购申请信息列表
     * 
     * @param purchaseRequisition 采购申请信息
     * @return 采购申请信息
     */
    @Override
    public List<PurchaseRequisition> selectPurchaseRequisitionList(PurchaseRequisition purchaseRequisition) {
        return purchaseRequisitionMapper.selectPurchaseRequisitionList(purchaseRequisition);
    }

    /**
     * 新增采购申请信息
     * 
     * @param purchaseRequisition 采购申请信息
     * @return 结果
     */
    @Override
    public int insertPurchaseRequisition(PurchaseRequisition purchaseRequisition) {
        purchaseRequisition.setCreateTime(DateUtils.getNowDate());
        return purchaseRequisitionMapper.insertPurchaseRequisition(purchaseRequisition);
    }

    /**
     * 修改采购申请信息
     * 
     * @param purchaseRequisition 采购申请信息
     * @return 结果
     */
    @Override
    public int updatePurchaseRequisition(PurchaseRequisition purchaseRequisition) {
        purchaseRequisition.setUpdateTime(DateUtils.getNowDate());
        return purchaseRequisitionMapper.updatePurchaseRequisition(purchaseRequisition);
    }

    /**
     * 批量删除采购申请信息
     * 
     * @param ids 需要删除的采购申请信息主键
     * @return 结果
     */
    @Override
    public int deletePurchaseRequisitionByIds(Long[] ids) {
        return purchaseRequisitionMapper.deletePurchaseRequisitionByIds(ids);
    }

    /**
     * 删除采购申请信息信息
     * 
     * @param id 采购申请信息主键
     * @return 结果
     */
    @Override
    public int deletePurchaseRequisitionById(Long id) {
        return purchaseRequisitionMapper.deletePurchaseRequisitionById(id);
    }
}
