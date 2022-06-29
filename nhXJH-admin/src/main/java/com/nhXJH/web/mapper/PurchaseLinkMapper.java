package com.nhXJH.web.mapper;

import java.util.List;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nhXJH.web.domain.MeasurementUtil;
import com.nhXJH.web.domain.PurchaseLink;
import org.apache.ibatis.annotations.Mapper;

/**
 * 采购申请结点处理Mapper接口
 * 
 * @author xjh
 * @date 2022-01-25
 */
@Mapper
public interface PurchaseLinkMapper  extends BaseMapper<PurchaseLink> {
    /**
     * 查询采购申请结点处理
     * 
     * @param id 采购申请结点处理主键
     * @return 采购申请结点处理
     */
    public PurchaseLink selectPurchaseLinkById(Long id);
    public Integer getCount(PurchaseLink purchaseLink);
    /**
     * 查询采购申请结点处理
     *
     * @param array 采购申请结点处理主键
     * @return 采购申请结点处理
     */
    public PurchaseLink selectPurchaseLinkByIds(Long[] array);

    /**
     * 查询采购申请结点处理列表
     * 
     * @param purchaseLink 采购申请结点处理
     * @return 采购申请结点处理集合
     */
    public List<PurchaseLink> selectPurchaseLinkList(PurchaseLink purchaseLink);

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
     * 删除采购申请结点处理
     * 
     * @param id 采购申请结点处理主键
     * @return 结果
     */
    public int deletePurchaseLinkById(Long id,Long operator);

    /**
     * 批量删除采购申请结点处理
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePurchaseLinkByIds(Long[] ids,Long operator);
}
