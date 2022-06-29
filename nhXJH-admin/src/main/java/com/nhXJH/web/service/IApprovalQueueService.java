package com.nhXJH.web.service;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;
import com.nhXJH.web.domain.ApprovalQueue;

/**
 * 申请队列信息Service接口
 * 
 * @author xjh
 * @date 2022-01-25
 */
public interface IApprovalQueueService extends IService<ApprovalQueue>{
    /**
     * 查询申请队列信息
     * 
     * @param id 申请队列信息主键
     * @return 申请队列信息
     */
    public ApprovalQueue selectApprovalQueueById(Long id);
    public Integer getCount(ApprovalQueue approvalQueue);
    /**
     * 查询申请队列信息列表
     * 
     * @param approvalQueue 申请队列信息
     * @return 申请队列信息集合
     */
    public List<ApprovalQueue> selectApprovalQueueList(ApprovalQueue approvalQueue);

    /*记录下一结点，并发送消息*/
    public int approvePurchase(ApprovalQueue approvalQueue);
    /**
     * 新增申请队列信息
     * 
     * @param approvalQueue 申请队列信息
     * @return 结果
     */
    public int insertApprovalQueue(ApprovalQueue approvalQueue);
    /**审批队列处理中心，实现消息队列化，消息通知，消息记录持久化
     * @param queueList 需要加入到消息队列的消息列表
     * **/
    public int factoryOfApproveQueueHandel(List<ApprovalQueue> queueList);
    /**
     * 修改申请队列信息
     * 
     * @param approvalQueue 申请队列信息
     * @return 结果
     */
    public int updateApprovalQueue(ApprovalQueue approvalQueue);

    /**
     * 修改申请队列信息
     *
     * @param approvalQueue 申请队列信息
     * @return 结果
     */
    public int approve(ApprovalQueue approvalQueue);

    /**
     * 批量删除申请队列信息
     * 
     * @param ids 需要删除的申请队列信息主键集合
     * @return 结果
     */
    public int deleteApprovalQueueByIds(Long[] ids,Long operator);

    /**
     * 已读
     *
     * @param ids 需要删除的申请队列信息主键集合
     * @return 结果
     */
    public int readAll(String ids,Long operator);

    /**
     * 删除申请队列信息信息
     * 
     * @param id 申请队列信息主键
     * @return 结果
     */
    public int deleteApprovalQueueById(Long id,Long operator);
}
