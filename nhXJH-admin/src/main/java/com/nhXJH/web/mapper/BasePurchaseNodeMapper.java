package com.nhXJH.web.mapper;

import java.util.List;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nhXJH.web.domain.BaseFile;
import com.nhXJH.web.domain.BasePurchaseNode;
import com.nhXJH.web.domain.BasePurchaseRequisitionSub;
import org.apache.ibatis.annotations.Param;

/**
 * 审批流程结点信息Mapper接口
 * 
 * @author xjh
 * @date 2022-03-03
 */
public interface BasePurchaseNodeMapper  extends BaseMapper<BasePurchaseNode> {
    /**
     * 查询审批流程结点信息
     * 
     * @param id 审批流程结点信息主键
     * @return 审批流程结点信息
     */
    public BasePurchaseNode selectBasePurchaseNodeById(Long id);
    public Integer getCount(BasePurchaseNode basePurchaseNode);
    /**
     * 查询审批流程结点信息列表
     * 
     * @param basePurchaseNode 审批流程结点信息
     * @return 审批流程结点信息集合
     */
    public List<BasePurchaseNode> selectBasePurchaseNodeList(BasePurchaseNode basePurchaseNode);

    /**
     * 查询审批流程结点信息列表
     *
     * @param array 审批流程结点信息
     * @return 审批流程结点信息集合
     */
    public List<BasePurchaseNode> selectBasePurchaseNodeByIds(Long [] array);

    /**
     * 新增审批流程结点信息
     * 
     * @param basePurchaseNode 审批流程结点信息
     * @return 结果
     */
    public int insertBasePurchaseNode(BasePurchaseNode basePurchaseNode);
    public int insertList(@Param("list") List<BasePurchaseNode> list);
    /**
     * 修改审批流程结点信息
     * 
     * @param basePurchaseNode 审批流程结点信息
     * @return 结果
     */
    public int updateBasePurchaseNode(BasePurchaseNode basePurchaseNode);

    /**
     * 删除审批流程结点信息
     * 
     * @param id 审批流程结点信息主键
     * @return 结果
     */
    public int deleteBasePurchaseNodeById(Long id,Long operator);

    /**
     * 批量删除审批流程结点信息
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBasePurchaseNodeByIds(Long[] ids,Long operator);
}
