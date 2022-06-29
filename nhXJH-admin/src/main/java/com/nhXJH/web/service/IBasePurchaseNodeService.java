package com.nhXJH.web.service;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;
import com.nhXJH.web.domain.BaseDeptAuthority;
import com.nhXJH.web.domain.BasePurchaseNode;

/**
 * 审批流程结点信息Service接口
 * 
 * @author xjh
 * @date 2022-03-03
 */
public interface IBasePurchaseNodeService extends IService<BasePurchaseNode>{
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
     * 新增审批流程结点信息
     * 
     * @param basePurchaseNode 审批流程结点信息
     * @return 结果
     */
    public int insertBasePurchaseNode(BasePurchaseNode basePurchaseNode);

    /**
     * 修改审批流程结点信息
     * 
     * @param basePurchaseNode 审批流程结点信息
     * @return 结果
     */
    public int updateBasePurchaseNode(BasePurchaseNode basePurchaseNode);

    /**
     * 批量删除审批流程结点信息
     * 
     * @param ids 需要删除的审批流程结点信息主键集合
     * @return 结果
     */
    public int deleteBasePurchaseNodeByIds(Long[] ids,Long operator);

    /**
     * 删除审批流程结点信息信息
     * 
     * @param id 审批流程结点信息主键
     * @return 结果
     */
    public int deleteBasePurchaseNodeById(Long id,Long operator);
}
