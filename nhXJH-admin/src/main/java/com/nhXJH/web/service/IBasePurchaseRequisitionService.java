package com.nhXJH.web.service;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;
import com.nhXJH.enums.PurchaseNodeStatusEnums;
import com.nhXJH.web.domain.BasePurchaseNode;
import com.nhXJH.web.domain.BasePurchaseRequisition;
import com.nhXJH.web.domain.param.PurchaseParam;
import com.nhXJH.web.domain.vo.BasePurchaseRequisitionVO;
import com.nhXJH.web.domain.vo.MyMatterVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 采购申请信息Service接口
 * 
 * @author xjh
 * @date 2022-02-28
 */
@Mapper
public interface IBasePurchaseRequisitionService extends IService<BasePurchaseRequisition>{
    /**
     * 查询采购申请信息
     * 
     * @param id 采购申请信息主键
     * @return 采购申请信息
     */
    public BasePurchaseRequisition selectBasePurchaseRequisitionById(Long id);
    public Integer getCount(BasePurchaseRequisition basePurchaseRequisition);

    public List<MyMatterVO> getMyMatter(BasePurchaseRequisition basePurchaseRequisition);
    /**
     * 查询采购申请信息列表
     * 
     * @param basePurchaseRequisition 采购申请信息
     * @return 采购申请信息集合
     */
    public List<BasePurchaseRequisition> selectBasePurchaseRequisitionList(BasePurchaseRequisition basePurchaseRequisition);
    /**
     * 更新过期
     *
     * @param list 采购申请信息列表
     * @return 采购申请信息集合
     */
    List<BasePurchaseRequisition> updateTimeout(List<BasePurchaseRequisition> list);

    /**
     * 查询采购申请信息列表
     *
     * @param basePurchaseRequisition 采购申请信息
     * @return 采购申请信息集合
     */
    public List<BasePurchaseRequisitionVO> selectBasePurchaseRequisitionVOList(BasePurchaseRequisition basePurchaseRequisition);

    /**
     * 查询采购申请信息
     *
     * @param purchase 采购申请信息
     * @return 采购申请信息
     */
    public List<BasePurchaseRequisition> selectApproved(BasePurchaseRequisition purchase);
    /**
     * 查询采购申请信息列表
     *
     * @param basePurchaseRequisition 采购申请信息
     * @return 采购申请信息集合
     */
    public List<BasePurchaseRequisition> approved(BasePurchaseRequisition basePurchaseRequisition);

    /**
     * 新增采购申请信息
     * 
     * @param purchaseParam 采购申请信息
     * @return 结果
     */
    public int insertBasePurchaseRequisition(PurchaseParam purchaseParam);

    /**
     * 修改采购申请信息
     * 
     * @param purchaseParam 采购申请信息
     * @return 结果
     */
    public int updateBasePurchaseRequisition(PurchaseParam purchaseParam);

    /**
     * 批量删除采购申请信息
     * 
     * @param ids 需要删除的采购申请信息主键集合
     * @return 结果
     */
    public int deleteBasePurchaseRequisitionByIds(Long[] ids,Long operator);

    /**
     * 删除采购申请信息信息
     * 
     * @param id 采购申请信息主键
     * @return 结果
     */
    public int deleteBasePurchaseRequisitionById(Long id,Long operator);
}
