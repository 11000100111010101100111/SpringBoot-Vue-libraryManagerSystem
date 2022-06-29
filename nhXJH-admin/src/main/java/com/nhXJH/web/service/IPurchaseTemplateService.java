package com.nhXJH.web.service;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;
import com.nhXJH.web.domain.PurchaseRequisition;
import com.nhXJH.web.domain.PurchaseTemplate;

/**
 * 审批流程模板Service接口
 * 
 * @author xjh
 * @date 2022-01-25
 */
public interface IPurchaseTemplateService extends IService<PurchaseTemplate>{
    /**
     * 查询审批流程模板
     * 
     * @param id 审批流程模板主键
     * @return 审批流程模板
     */
    public PurchaseTemplate selectPurchaseTemplateById(Long id);
    public Integer getCount(PurchaseTemplate purchaseTemplate);
    /**
     * 查询审批流程模板列表
     * 
     * @param purchaseTemplate 审批流程模板
     * @return 审批流程模板集合
     */
    public List<PurchaseTemplate> selectPurchaseTemplateList(PurchaseTemplate purchaseTemplate);

    /**
     * 新增审批流程模板
     * 
     * @param purchaseTemplate 审批流程模板
     * @return 结果
     */
    public int insertPurchaseTemplate(PurchaseTemplate purchaseTemplate);

    /**
     * 修改审批流程模板
     * 
     * @param purchaseTemplate 审批流程模板
     * @return 结果
     */
    public int updatePurchaseTemplate(PurchaseTemplate purchaseTemplate);

    /**
     * 批量删除审批流程模板
     * 
     * @param ids 需要删除的审批流程模板主键集合
     * @return 结果
     */
    public int deletePurchaseTemplateByIds(Long[] ids);

    /**
     * 删除审批流程模板信息
     * 
     * @param id 审批流程模板主键
     * @return 结果
     */
    public int deletePurchaseTemplateById(Long id);
}
