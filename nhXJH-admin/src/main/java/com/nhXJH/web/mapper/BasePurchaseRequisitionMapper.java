package com.nhXJH.web.mapper;

import java.util.List;
import java.util.Set;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nhXJH.web.domain.BasePurchaseNode;
import com.nhXJH.web.domain.BasePurchaseRequisition;
import com.nhXJH.web.domain.vo.MyMatterVO;
import org.apache.ibatis.annotations.Param;

/**
 * 采购申请信息Mapper接口
 * 
 * @author xjh
 * @date 2022-02-28
 */
public interface BasePurchaseRequisitionMapper  extends BaseMapper<BasePurchaseRequisition> {
    /**
     * 查询采购申请信息
     * 
     * @param id 采购申请信息主键
     * @return 采购申请信息
     */
    public BasePurchaseRequisition selectBasePurchaseRequisitionById(Long id);
    public Integer getCount(BasePurchaseRequisition purchase);

    public List<MyMatterVO> getMyMatter(Long userId);
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
    public List<BasePurchaseRequisition> selectBasePurchaseRequisitionList(BasePurchaseRequisition basePurchaseRequisition);

    /**
     * 新增采购申请信息
     * 
     * @param basePurchaseRequisition 采购申请信息
     * @return 结果
     */
    public int insertBasePurchaseRequisition(BasePurchaseRequisition basePurchaseRequisition);

    /**
     * 修改采购申请信息
     * 
     * @param basePurchaseRequisition 采购申请信息
     * @return 结果
     */
    public int updateBasePurchaseRequisition(BasePurchaseRequisition basePurchaseRequisition);
    public int updateTimeOut(@Param("array") Long[] array);

    /**
     * 删除采购申请信息
     * 
     * @param id 采购申请信息主键
     * @return 结果
     */
    public int deleteBasePurchaseRequisitionById(@Param("id") Long id, @Param("operator")Long operator);

    /**
     * 批量删除采购申请信息
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBasePurchaseRequisitionByIds(@Param("array") Long[] ids, @Param("operator")Long operator);
}
