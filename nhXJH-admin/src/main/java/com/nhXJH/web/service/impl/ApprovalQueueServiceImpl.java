package com.nhXJH.web.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.extension.api.R;
import com.nhXJH.common.exception.ServiceException;
import com.nhXJH.enums.*;
import com.nhXJH.web.domain.*;
import com.nhXJH.web.domain.param.purchase.NextNode;
import com.nhXJH.web.domain.param.purchase.TemplateParam;
import com.nhXJH.web.domain.po.TemplateNode;
import com.nhXJH.web.mapper.*;
import com.nhXJH.web.service.IApprovalQueueService;
import com.nhXJH.common.utils.DateUtils;
import com.nhXJH.web.util.TemplateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;

/**
 * 申请队列信息Service业务层处理
 * 
 * @author xjh
 * @date 2022-01-25
 */
@Service
public class ApprovalQueueServiceImpl extends ServiceImpl<ApprovalQueueMapper, ApprovalQueue>
        implements IApprovalQueueService {
    @Autowired
    private ApprovalQueueMapper approvalQueueMapper;
    @Autowired
    private BasePurchaseRequisitionMapper requisitionMapper;
    @Autowired
    private BasePurchaseNodeMapper nodeMapper;
    @Autowired
    private PurchaseTemplateMapper templateMapper;

    @Autowired
    private PurchaseLinkMapper linkMapper;

    @Autowired
    private IApprovalQueueService approvalQueueService;
    /**
     * 查询申请队列信息
     * 
     * @param id 申请队列信息主键
     * @return 申请队列信息
     */
    @Override
    public ApprovalQueue selectApprovalQueueById(Long id) {
        return approvalQueueMapper.selectApprovalQueueById(id);
    }

    @Override
    public Integer getCount(ApprovalQueue approvalQueue) {
        return approvalQueueMapper.getCount(approvalQueue);
    }

    /**
     * 查询申请队列信息列表
     * 
     * @param approvalQueue 申请队列信息
     * @return 申请队列信息
     */
    @Override
    public List<ApprovalQueue> selectApprovalQueueList(ApprovalQueue approvalQueue) {
        return approvalQueueMapper.selectApprovalQueueList(approvalQueue);
    }

    @Transactional
    public int approvePurchase(ApprovalQueue approvalQueue){
        String result = approvalQueue.getResult();
        BasePurchaseRequisition res = new BasePurchaseRequisition();
        res.setId(approvalQueue.getPurchaseId());
        if (result.equals(QueueResult.REFUSE.getDesc())){

            //申请被拒绝
            res.setPurchaseStatus(PurchaseNodeStatusEnums.HAS_REFUSE.getDesc());
            res.setUpdatePersonal(approvalQueue.getUpdatePersonal());
            res.setUpdateTime(new Date());
            requisitionMapper.updateBasePurchaseRequisition(res);
            return 0;
        }


        BasePurchaseRequisition purchase = requisitionMapper.selectBasePurchaseRequisitionById(approvalQueue.getPurchaseId());
        BasePurchaseNode purchaseNode = nodeMapper.selectBasePurchaseNodeById(approvalQueue.getHandelNode());
        PurchaseTemplate templateNode = templateMapper.selectPurchaseTemplateById(purchase.getPurchaseTemplate());
        TemplateParam template = TemplateUtils.JsonParseTemplate(templateNode.getTemplate());
        //处理子节点
        List<NextNode> subNode = TemplateUtils.getNextNode(template,purchaseNode.getNodeCode(),approvalQueue.getResult());

        //@TODO 使用消息队列发送这个审批过程到相关人员
        if(subNode.size()>0){
            NextNode endNode = subNode.get(0);
            if (null != endNode && endNode.getIsEnd()) {
                BasePurchaseRequisition purchaseRequisition = new BasePurchaseRequisition();
                purchaseRequisition.setId(approvalQueue.getPurchaseId());

                if ("1".equals(result)) {
                    purchaseRequisition.setPurchaseStatus("3");
                } else {
                    purchaseRequisition.setPurchaseStatus("-1");
                }
                requisitionMapper.updateBasePurchaseRequisition(purchaseRequisition);
            }else {

                Long groupId = purchase.getSnowID();
                List<ApprovalQueue> queueList = new ArrayList<>();

                for (NextNode nextNode : subNode) {
                    List<Long> handelUser = nextNode.getNode().getUserId();
                    ApprovalQueue queue = new ApprovalQueue();
                    queue.setGroupId(groupId);
                    queue.setQueueType(QueueTypeEnum.PURCHASE.getDesc());
                    queue.setPurchaseId(purchase.getId());

                    BasePurchaseNode basePurchaseNode = new BasePurchaseNode();
                    basePurchaseNode.setNodeCode(nextNode.getNode().getId());
                    basePurchaseNode.setPurchaseTemplate(purchase.getPurchaseTemplate());
                    List<BasePurchaseNode> basePurchaseNodeList = nodeMapper.selectBasePurchaseNodeList(basePurchaseNode);
                    if (null == basePurchaseNodeList || basePurchaseNodeList.size() != 1) {
                        throw new ServiceException("模板异常：找不到下一个流程处理角色，请维护新的流程模板");
                    }
                    queue.setHandelNode(basePurchaseNodeList.get(0).getId());
                    queue.setHasRead(QueueReadType.NOT_READ.getDesc());
                    queue.setResult(QueueResult.TIMER.getDesc());
                    queue.setIsDel(DelEnums.NOT_DEL.getValue());
                    queue.setStatus(StatusEnums.ONE.getDesc());
                    queue.setMark(nextNode.getLabel());
                    queue.setCreateTime(new Date());
                    queue.setCreatePersonal(purchase.getUpdatePersonal());

                    for (Long aLong : handelUser) {
                        ApprovalQueue copyQuery = queue.clone();
                        copyQuery.setId(copyQuery.getSnowID());
                        copyQuery.setHandelPersonal(aLong);
                        queueList.add(copyQuery);
                    }
                }

                if (null != queueList && queueList.size() > 0) {
                    approvalQueueMapper.insertList(queueList);
                }
            }
        }
        else{
            //申请流程已完成
            res.setPurchaseStatus(PurchaseNodeStatusEnums.HAS_DO.getDesc());
            res.setUpdatePersonal(approvalQueue.getUpdatePersonal());
            res.setUpdateTime(new Date());
            requisitionMapper.updateBasePurchaseRequisition(res);
            return 1;
        }
        return 1;
    }



    /**审批队列处理中心，实现消息队列化，消息通知，消息记录持久化
     * @param queueList 需要加入到消息队列的消息列表
     * **/
    public int factoryOfApproveQueueHandel(List<ApprovalQueue> queueList){
        //@TODO 消息通知

        return approvalQueueMapper.insertList(queueList);
    }
    /**
     * 新增申请队列信息
     * 
     * @param approvalQueue 申请队列信息
     * @return 结果
     */
    @Override
    public int insertApprovalQueue(ApprovalQueue approvalQueue) {
        approvalQueue.setCreateTime(DateUtils.getNowDate());
        return approvalQueueMapper.insertApprovalQueue(approvalQueue);
    }

    /**
     * 修改申请队列信息
     * 
     * @param approvalQueue 申请队列信息
     * @return 结果
     */
    @Override
    public int updateApprovalQueue(ApprovalQueue approvalQueue) {
        approvalQueue.setUpdateTime(DateUtils.getNowDate());
        return approvalQueueMapper.updateApprovalQueue(approvalQueue);
    }


    @Transactional
    @Override
    public int approve(ApprovalQueue approvalQueue){
         approvalQueueMapper.delGroup(new Long[]{approvalQueue.getGroupId()},approvalQueue.getUpdatePersonal());
         approvalQueueMapper.updateApprovalQueue(approvalQueue);
         PurchaseLink link = new PurchaseLink();
         link.setId(link.getSnowID());
         link.setApproveId(approvalQueue.getPurchaseId());
         link.setPurchaseNode(approvalQueue.getHandelNode());
         link.setHandlePersonal(approvalQueue.getUpdatePersonal());
         link.setHandleResult(
                 approvalQueue.getResult().equals("1")?
                         PurchaseNodeStatusEnums.HAS_AGREE.getDesc()
                         :PurchaseNodeStatusEnums.HAS_REFUSE.getDesc());
         link.setMark(approvalQueue.getMark());
         link.setIsDel(DelEnums.NOT_DEL.getValue());
         link.setStatus(StatusEnums.ONE.getDesc());
         link.setCreatePersonal(approvalQueue.getUpdatePersonal());
         link.setCreateTime(new Date());
         linkMapper.insertPurchaseLink(link);
         approvalQueueService.approvePurchase(approvalQueue);
         return 1;
    }

    /**
     * 批量删除申请队列信息
     * 
     * @param ids 需要删除的申请队列信息主键
     * @return 结果
     */
    @Override
    public int deleteApprovalQueueByIds(Long[] ids,Long operator) {
        return approvalQueueMapper.deleteApprovalQueueByIds(ids,operator);
    }

    /**
     * 已读
     *
     * @param ids 需要删除的申请队列信息主键集合
     * @return 结果
     */
    public int readAll(String ids, Long operator){
        if (null == ids || ids.trim().equals("")){
            return 0;
        }
        String[] id = ids.split(",");
        if (id.length>0){
            Long []array = new Long[id.length];
            for (int i = 0; i < id.length; i++) {
                try {
                    array[i] = Long.parseLong(id[i]);
                }catch (NumberFormatException e){
                    throw new ServiceException("参数异常");
                }
            }
            return approvalQueueMapper.readAll(array,operator);
        }
        return 0;
    }
    /**
     * 删除申请队列信息信息
     * 
     * @param id 申请队列信息主键
     * @return 结果
     */
    @Override
    public int deleteApprovalQueueById(Long id,Long operator) {
        return approvalQueueMapper.deleteApprovalQueueById(id,operator);
    }
}
