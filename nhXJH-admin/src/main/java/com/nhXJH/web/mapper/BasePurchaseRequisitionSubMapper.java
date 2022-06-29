package com.nhXJH.web.mapper;

import java.util.List;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nhXJH.web.domain.BasePurchaseRequisition;
import com.nhXJH.web.domain.BasePurchaseRequisitionSub;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 采购申请子Mapper接口
 * 
 * @author xjh
 * @date 2022-02-28
 */
@Mapper
public interface BasePurchaseRequisitionSubMapper  extends BaseMapper<BasePurchaseRequisitionSub> {
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
     * 新增采购申请子
     * 
     * @param basePurchaseRequisitionSub 采购申请子
     * @return 结果
     */
    public int insertBasePurchaseRequisitionSub(BasePurchaseRequisitionSub basePurchaseRequisitionSub);

    /**
     * 新增采购申请子
     *
     * @param list 采购申请子
     * @return 结果
     */
    public int insertList(@Param("list") List<BasePurchaseRequisitionSub> list);

    /**
     * 修改采购申请子
     * 
     * @param basePurchaseRequisitionSub 采购申请子
     * @return 结果
     */
    public int updateBasePurchaseRequisitionSub(BasePurchaseRequisitionSub basePurchaseRequisitionSub);

    /**
     * 删除采购申请子
     * 
     * @param basePurchaseRequisitionSub 采购申请子主键
     * @return 结果
     */
    public int delByEntityForAnd(BasePurchaseRequisitionSub basePurchaseRequisitionSub);

    /**
     * 批量删除采购申请子
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBasePurchaseRequisitionSubByIds(@Param("array") Long[] ids, @Param("operator")Long operator);
}
