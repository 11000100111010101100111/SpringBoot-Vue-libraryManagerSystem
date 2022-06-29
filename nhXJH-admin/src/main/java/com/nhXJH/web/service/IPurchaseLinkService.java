package com.nhXJH.web.service;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;
import com.nhXJH.web.domain.MeasurementUtil;
import com.nhXJH.web.domain.PurchaseLink;
import com.nhXJH.web.domain.vo.PurchaseLinkNodeVO;

/**
 * 采购申请结点处理Service接口
 * 
 * @author xjh
 * @date 2022-01-25
 */
public interface IPurchaseLinkService extends IService<PurchaseLink>{
    /**
     * 查询采购申请结点处理
     * 
     * @param id 采购申请结点处理主键
     * @return 采购申请结点处理
     */
    public PurchaseLink selectPurchaseLinkById(Long id);
    public Integer getCount(PurchaseLink purchaseLink);
    /**
     * 查询采购申请结点处理列表
     * 
     * @param purchaseLink 采购申请结点处理
     * @return 采购申请结点处理集合
     */
    public List<PurchaseLink> selectPurchaseLinkList(PurchaseLink purchaseLink);

    /**
     * 查询采购申请结点处理列表
     *
     * @param purchaseLink 采购申请结点处理
     * @return 采购申请结点处理集合
     */
    public List<PurchaseLinkNodeVO> selectPurchaseLink(PurchaseLink purchaseLink);

    /**
     * 新增采购申请结点处理
     * 
     * @param purchaseLink 采购申请结点处理
     * @return 结果
     */
    public int insertPurchaseLink(PurchaseLink purchaseLink);

    /**
     * 修改采购申请结点处理
     * 
     * @param purchaseLink 采购申请结点处理
     * @return 结果
     */
    public int updatePurchaseLink(PurchaseLink purchaseLink);

    /**
     * 批量删除采购申请结点处理
     * 
     * @param ids 需要删除的采购申请结点处理主键集合
     * @return 结果
     */
    public int deletePurchaseLinkByIds(Long[] ids,Long operator);

    /**
     * 删除采购申请结点处理信息
     * 
     * @param id 采购申请结点处理主键
     * @return 结果
     */
    public int deletePurchaseLinkById(Long id,Long operator);
}
