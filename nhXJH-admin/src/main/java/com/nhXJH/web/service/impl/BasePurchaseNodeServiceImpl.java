package com.nhXJH.web.service.impl;

import java.util.List;
import com.nhXJH.common.utils.DateUtils;
import com.nhXJH.web.domain.BasePurchaseNode;
import com.nhXJH.web.mapper.BasePurchaseNodeMapper;
import com.nhXJH.web.service.IBasePurchaseNodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * 审批流程结点信息Service业务层处理
 * 
 * @author xjh
 * @date 2022-03-03
 */
@Service
public class BasePurchaseNodeServiceImpl extends ServiceImpl<BasePurchaseNodeMapper, BasePurchaseNode>
        implements IBasePurchaseNodeService {
    @Autowired
    private BasePurchaseNodeMapper basePurchaseNodeMapper;

    /**
     * 查询审批流程结点信息
     * 
     * @param id 审批流程结点信息主键
     * @return 审批流程结点信息
     */
    @Override
    public BasePurchaseNode selectBasePurchaseNodeById(Long id) {
        return basePurchaseNodeMapper.selectBasePurchaseNodeById(id);
    }

    @Override
    public Integer getCount(BasePurchaseNode basePurchaseNode) {
        return basePurchaseNodeMapper.getCount(basePurchaseNode);
    }

    /**
     * 查询审批流程结点信息列表
     * 
     * @param basePurchaseNode 审批流程结点信息
     * @return 审批流程结点信息
     */
    @Override
    public List<BasePurchaseNode> selectBasePurchaseNodeList(BasePurchaseNode basePurchaseNode) {
        return basePurchaseNodeMapper.selectBasePurchaseNodeList(basePurchaseNode);
    }

    /**
     * 新增审批流程结点信息
     * 
     * @param basePurchaseNode 审批流程结点信息
     * @return 结果
     */
    @Override
    public int insertBasePurchaseNode(BasePurchaseNode basePurchaseNode) {
        basePurchaseNode.setCreateTime(DateUtils.getNowDate());
        return basePurchaseNodeMapper.insertBasePurchaseNode(basePurchaseNode);
    }

    /**
     * 修改审批流程结点信息
     * 
     * @param basePurchaseNode 审批流程结点信息
     * @return 结果
     */
    @Override
    public int updateBasePurchaseNode(BasePurchaseNode basePurchaseNode) {
        basePurchaseNode.setUpdateTime(DateUtils.getNowDate());
        return basePurchaseNodeMapper.updateBasePurchaseNode(basePurchaseNode);
    }

    /**
     * 批量删除审批流程结点信息
     * 
     * @param ids 需要删除的审批流程结点信息主键
     * @return 结果
     */
    @Override
    public int deleteBasePurchaseNodeByIds(Long[] ids,Long operator) {
        return basePurchaseNodeMapper.deleteBasePurchaseNodeByIds(ids,operator);
    }

    /**
     * 删除审批流程结点信息信息
     * 
     * @param id 审批流程结点信息主键
     * @return 结果
     */
    @Override
    public int deleteBasePurchaseNodeById(Long id,Long operator) {
        return basePurchaseNodeMapper.deleteBasePurchaseNodeById(id,operator);
    }
}
