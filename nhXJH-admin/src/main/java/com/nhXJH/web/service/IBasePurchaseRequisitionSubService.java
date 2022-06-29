package com.nhXJH.web.service;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;
import com.nhXJH.web.domain.BasePurchaseRequisition;
import com.nhXJH.web.domain.BasePurchaseRequisitionSub;
import com.nhXJH.web.domain.vo.InStockVO;

/**
 * 采购申请子Service接口
 * 
 * @author xjh
 * @date 2022-02-28
 */
public interface IBasePurchaseRequisitionSubService extends IService<BasePurchaseRequisitionSub>{
    /**
     * 查询采购申请子
     * 
     * @param id 采购申请子主键
     * @return 采购申请子
     */
    public BasePurchaseRequisitionSub selectBasePurchaseRequisitionSubById(Long id);
    public Integer getCount(BasePurchaseRequisitionSub basePurchaseRequisitionSub);
    /**
     * 查询采购申请子列表
     * 
     * @param basePurchaseRequisitionSub 采购申请子
     * @return 采购申请子集合
     */
    public List<BasePurchaseRequisitionSub> selectBasePurchaseRequisitionSubList(BasePurchaseRequisitionSub basePurchaseRequisitionSub);
    /**
     * 查询采购申请子列表
     *
     * @param basePurchaseRequisitionSub 采购申请子
     * @return 采购申请子集合
     */
    public List<BasePurchaseRequisitionSub> selectSubList(BasePurchaseRequisitionSub basePurchaseRequisitionSub);
    /**
     * 查询采购申请子列表
     *
     * @param basePurchaseRequisitionSub 采购申请子
     * @return 采购申请子集合
     */
    public InStockVO inStock(BasePurchaseRequisitionSub basePurchaseRequisitionSub);

    /**
     * 新增采购申请子
     * 
     * @param basePurchaseRequisitionSub 采购申请子
     * @return 结果
     */
    public int insertBasePurchaseRequisitionSub(BasePurchaseRequisitionSub basePurchaseRequisitionSub);

    /**
     * 修改采购申请子
     * 
     * @param basePurchaseRequisitionSub 采购申请子
     * @return 结果
     */
    public int updateBasePurchaseRequisitionSub(BasePurchaseRequisitionSub basePurchaseRequisitionSub);

    /**
     * 批量删除采购申请子
     * 
     * @param ids 需要删除的采购申请子主键集合
     * @return 结果
     */
    public int deleteBasePurchaseRequisitionSubByIds(Long[] ids,Long operator);

    /**
     * 删除采购申请子信息
     * 
     * @param basePurchaseRequisitionSub 采购申请子主键
     * @return 结果
     */
    public int delByEntityForAnd(BasePurchaseRequisitionSub basePurchaseRequisitionSub);
}
