package com.nhXJH.web.service.impl;

import java.util.*;
import java.util.stream.Collectors;

import com.nhXJH.common.core.domain.entity.SysDept;
import com.nhXJH.common.core.domain.entity.SysUser;
import com.nhXJH.common.exception.ServiceException;
import com.nhXJH.common.utils.DateUtils;
import com.nhXJH.common.utils.StringUtils;
import com.nhXJH.enums.*;
import com.nhXJH.system.mapper.SysDeptMapper;
import com.nhXJH.system.mapper.SysUserMapper;
import com.nhXJH.web.domain.*;
import com.nhXJH.web.domain.param.PurchaseParam;
import com.nhXJH.web.domain.param.purchase.NextNode;
import com.nhXJH.web.domain.param.purchase.PurchaseNode;
import com.nhXJH.web.domain.param.purchase.TemplateParam;
import com.nhXJH.web.domain.vo.BasePurchaseRequisitionVO;
import com.nhXJH.web.domain.vo.MyMatterVO;
import com.nhXJH.web.mapper.*;
import com.nhXJH.web.service.IApprovalQueueService;
import com.nhXJH.web.service.IBasePurchaseRequisitionService;
import com.nhXJH.web.service.IBasePurchaseRequisitionSubService;
import com.nhXJH.web.util.TemplateUtils;
import com.nhXJH.web.util.collections.OperatorCollection;
import io.minio.errors.ServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.crypto.Data;

/**
 * 采购申请信息Service业务层处理
 * 
 * @author xjh
 * @date 2022-02-28
 */
@Service
public class BasePurchaseRequisitionServiceImpl extends ServiceImpl<BasePurchaseRequisitionMapper, BasePurchaseRequisition>
        implements IBasePurchaseRequisitionService {
    @Autowired
    private BasePurchaseRequisitionMapper basePurchaseRequisitionMapper;
    @Autowired
    private BasePurchaseRequisitionSubMapper subPurchaseMapper;
    @Autowired
    private BaseFileMapper fileMapper;
    @Autowired
    private PurchaseLinkMapper purchaseLinkMapper;
    @Autowired
    private PurchaseTemplateMapper templateMapper;
    @Autowired
    private BasePurchaseNodeMapper nodeMapper;
    @Autowired
    private IApprovalQueueService queueService;
    @Autowired
    private SysUserMapper userMapper;
    @Autowired
    private SysDeptMapper deptMapper;
    @Autowired
    private OperatorCollection operatorCollection;
    @Autowired
    private IBasePurchaseRequisitionService basePurchaseRequisitionService;

    /**
     * 查询采购申请信息
     * 
     * @param id 采购申请信息主键
     * @return 采购申请信息
     */
    @Override
    public BasePurchaseRequisition selectBasePurchaseRequisitionById(Long id) {
        BasePurchaseRequisition requisition = basePurchaseRequisitionMapper.selectBasePurchaseRequisitionById(id);
        List<BasePurchaseRequisition> list = new ArrayList<>();
        list.add(requisition);
        list = basePurchaseRequisitionService.updateTimeout(list);
        requisition = list.get(0);
        return requisition;
    }

    @Override
    public Integer getCount(BasePurchaseRequisition basePurchaseRequisition) {
        return basePurchaseRequisitionMapper.getCount(basePurchaseRequisition);
    }

    /**获取我的代办事务**/
    @Override
    public List<MyMatterVO> getMyMatter(BasePurchaseRequisition basePurchaseRequisition){
        List<String> type = new ArrayList<>();
        if (StringUtils.isNull(basePurchaseRequisition)){
            throw new ServiceException("失败");
        }
        if(StringUtils.isNotNull(basePurchaseRequisition.getPurchaseStatus())){
            type =Arrays.asList(basePurchaseRequisition.getPurchaseStatus().split(","));

        }
        List<MyMatterVO> list = basePurchaseRequisitionMapper.getMyMatter(basePurchaseRequisition.getCreatePersonal());

        List<MyMatterVO> result = new ArrayList<>();
        if (StringUtils.isNotNull(list)){
            for (MyMatterVO myMatterVO : list) {
                if (myMatterVO.getHasRead().equals(StatusEnums.ZERO.getDesc())){
                    myMatterVO.setResult("-1");
                }
                if (type.contains(myMatterVO.getResult())){
                    result.add(myMatterVO);
                }
            }
        }
        return result;
    }

    /**
     * 查询采购申请信息
     *
     * @param purchase 采购申请信息
     * @return 采购申请信息
     */
    public List<BasePurchaseRequisition> selectApproved(BasePurchaseRequisition purchase){
        purchase.setStatus(StatusEnums.ONE.getDesc());
        purchase.setIsDel(DelEnums.NOT_DEL.getValue());
        purchase.setPurchaseStatus(PurchaseNodeStatusEnums.HAS_AGREE.getDesc());
        return basePurchaseRequisitionMapper.selectApproved(purchase);
    }

    /**
     * 查询采购申请信息列表
     * 
     * @param basePurchaseRequisition 采购申请信息
     * @return 采购申请信息
     */
    @Override
    public List<BasePurchaseRequisition> selectBasePurchaseRequisitionList(BasePurchaseRequisition basePurchaseRequisition) {
        return basePurchaseRequisitionMapper.selectBasePurchaseRequisitionList(basePurchaseRequisition);
    }

    @Override
    public List<BasePurchaseRequisition> updateTimeout(List<BasePurchaseRequisition> list){
        Set<Long> ids = new HashSet<>();
        list.stream().forEach(purchase->{
            Date termValidity = purchase.getTermValidity();
            String status = purchase.getPurchaseStatus();
            if ( ("1".equals(status) || "2".equals(status)) && termValidity.before(new Date())){
                ids.add(purchase.getId());
                purchase.setPurchaseStatus("-2");
            }
        });
        if (ids.size()>0) {
            Long[] idArr = new Long[ids.size()];
            int index = 0;
            for (Long id : ids) {
                idArr[index] = id;
                index++;
            }
            basePurchaseRequisitionMapper.updateTimeOut(idArr);
        }
        return list;
    }

    @Override
    public List<BasePurchaseRequisitionVO> selectBasePurchaseRequisitionVOList(BasePurchaseRequisition purchase) {
        purchase.setStatus(StatusEnums.ONE.getDesc());
        purchase.setIsDel(DelEnums.NOT_DEL.getValue());
        purchase.setPurchaseStatus(PurchaseNodeStatusEnums.HAS_AGREE.getDesc());
        List<BasePurchaseRequisition> list = basePurchaseRequisitionMapper.selectApproved(purchase);
        List<BasePurchaseRequisitionVO> vo = new ArrayList<>();
        if (null == list || list.size()<1){
            return vo;
        }
        Set<Long> uIdSet = new HashSet<>();
        Set<Long> deptSet = new HashSet<>();
        for (BasePurchaseRequisition requisition : list) {
            uIdSet.add(requisition.getApprovePersonal());
            deptSet.add(requisition.getApproveDept());
        }
        List<SysUser> userList = userMapper.selectUserByIds(operatorCollection.toArray(uIdSet));
        Map<Long,SysUser> userMap = new HashMap<>();
        if (null!=userList){
            for (SysUser user : userList) {
                userMap.put(user.getUserId(),user);
            }
        }
        List<SysDept> deptList = deptMapper.selectDeptByIds(operatorCollection.toArray(deptSet));
        Map<Long,SysDept> deptMap = new HashMap<>();
        if (null!=deptList){
            for (SysDept dept : deptList) {
                deptMap.put(dept.getDeptId(),dept);
            }
        }
        for (BasePurchaseRequisition requisition : list) {
            vo.add(
                new BasePurchaseRequisitionVO(
                        requisition.getId(),
                        requisition,
                        deptMap.get(requisition.getApproveDept()),
                        userMap.get(requisition.getApprovePersonal())
                )
            );
        }
        return vo;
    }

    /** 查询已通过的采购申请信息列表
     *
     * @param basePurchaseRequisition 采购申请信息
     * @return 采购申请信息集合
     */
    public List<BasePurchaseRequisition> approved(BasePurchaseRequisition basePurchaseRequisition){
        basePurchaseRequisition.setPurchaseStatus(PurchaseNodeStatusEnums.HAS_AGREE.getDesc());
        return basePurchaseRequisitionMapper.selectBasePurchaseRequisitionList(basePurchaseRequisition);
    }
    /**
     * 新增采购申请信息
     * 
     * @param purchaseParam 采购申请信息
     * @return 结果
     */
    @Override
    @Transactional
    public int insertBasePurchaseRequisition(PurchaseParam purchaseParam) {

        BasePurchaseRequisition purchase = purchaseParam.getPurchase();
        int result =basePurchaseRequisitionMapper.insertBasePurchaseRequisition(purchase);

        //@ TODO 批量插入申请子表
        List<BasePurchaseRequisitionSub> list = purchaseParam.getSubPurchase();
        if (null != list && list.size()>0) {
            result = subPurchaseMapper.insertList(list);
        }
        return  result;
    }

    /**
     * 修改采购申请信息
     * 
     * @param purchaseParam 采购申请信息
     * @return 结果
     */
    @Override
    @Transactional
    public int updateBasePurchaseRequisition(PurchaseParam purchaseParam) {
        BasePurchaseRequisition purchase = purchaseParam.getPurchase();
        String submitType = purchaseParam.getSubmitType();

        PurchaseLink link = new PurchaseLink();//审批链起始节点
        Boolean flag = null != submitType && !"".equals(submitType) && submitType.matches("^(\\-)?\\d{1,2}$");
        if (flag){
            purchase.setPurchaseStatus(submitType);
            if(PurchaseNodeStatusEnums.HAS_SUBMIT.getDesc().equals(submitType)){
                //保存并提交申请
                purchase.setStartTime(new Date());


                link.setId(link.getSnowID());
                link.setCreatePersonal(purchase.getCreatePersonal());//设置提交申请信息人Id
                link.setUpdateTime(new Date());
                link.setHandlePersonal(purchase.getUpdatePersonal());
                link.setHandleResult(PurchaseNodeStatusEnums.HAS_SUBMIT.getDesc());
                link.setMark(PurchaseNodeStatusEnums.HAS_SUBMIT.getMark());
                link.setIsDel(DelEnums.NOT_DEL.getValue());
                link.setStatus(StatusEnums.ONE.getDesc());

            }
        }


        if (null != purchase.getId() && purchase.getId()>0l) {

            BasePurchaseRequisition queryPurchase = new BasePurchaseRequisition();
            queryPurchase.setId(purchase.getId());
            queryPurchase.setIsDel(DelEnums.IS_DEL.getValue());


            queryPurchase.setUpdatePersonal(queryPurchase.getUpdatePersonal());
            queryPurchase.setUpdateTime(new Date());
            basePurchaseRequisitionMapper.updateBasePurchaseRequisition(queryPurchase);

            purchase.setAgoId(purchase.getId());
        }else{
            purchase.setAgoId(0l);
        }


        List<BasePurchaseRequisitionSub> subList = purchaseParam.getSubPurchase();
        //@ TODO 批量delete申请子表old Data
        if (null != subList && subList.size()>0) {
            BasePurchaseRequisitionSub item = subList.get(0);
            BasePurchaseRequisitionSub deleteQuery = new BasePurchaseRequisitionSub();
            deleteQuery.setGroupId(purchase.getId());
            deleteQuery.setBatchId(item.getBatchId());
            subPurchaseMapper.delByEntityForAnd(deleteQuery);
        }

        purchase.setId(purchase.getSnowID());
        basePurchaseRequisitionMapper.insertBasePurchaseRequisition(purchase);

        if (null != subList && subList.size()>0){
            Long groupID = purchase.getId();
            Long newBatchID = purchase.getSnowID();
            Date now = new Date();
            for (BasePurchaseRequisitionSub purchaseSub : subList) {
                if (null == purchaseSub.getCreatePersonal() || purchaseSub.getCreatePersonal()<1l){
                    purchaseSub.setCreatePersonal(purchase.getUpdatePersonal());
                    purchaseSub.setCreateTime(new Date());
                }
                purchaseSub.setId(purchaseSub.getSnowID());
                purchaseSub.setGroupId(groupID);
                purchaseSub.setUpdatePersonal(purchase.getUpdatePersonal());
                purchaseSub.setBatchId(newBatchID);//更新时批次ID更新
                purchaseSub.setUpdateTime(now);
            }
        }
        //@ TODO 批量插入申请子表new data

        int result=0;
        if (null != subList && subList.size()>0) {
            result = subPurchaseMapper.insertList(subList);
        }


        if (flag && PurchaseNodeStatusEnums.HAS_SUBMIT.getDesc().equals(submitType)) {
            if (null == purchase.getPurchaseTemplate()) {
                throw new ServiceException("流程模板未选择");
            }
            PurchaseTemplate template = templateMapper.selectPurchaseTemplateById(purchase.getPurchaseTemplate());

            TemplateParam templateParam = TemplateUtils.JsonParseTemplate(template.getTemplate());

            if(null == templateParam){
                throw new ServiceException("错误的流程模板，没有结点信息");
            }

            PurchaseNode firstNode = TemplateUtils.getFirstNode(templateParam);

            if(null == firstNode){
                throw new ServiceException("错误的流程模板，没有结点信息");
            }

            BasePurchaseNode basePurchaseNode = new BasePurchaseNode();
            basePurchaseNode.setNodeCode(firstNode.getId());
            basePurchaseNode.setPurchaseTemplate(template.getId());
            List<BasePurchaseNode> basePurchaseNodeList = nodeMapper.selectBasePurchaseNodeList(basePurchaseNode);
            if (null == basePurchaseNodeList || basePurchaseNodeList.size() != 1) {
                throw new ServiceException("流程模板结点信息不存在，失效的模板");
            }
            basePurchaseNode = basePurchaseNodeList.get(0);

            link.setApproveId(purchase.getId());
            link.setPurchaseNode(basePurchaseNode.getId());
            purchaseLinkMapper.insertPurchaseLink(link);


            //处理子节点
            List<NextNode> subNode = TemplateUtils.getNextNode(templateParam,firstNode.getId());
            //@TODO 使用消息队列发送这个审批过程到相关人员
            if(null != subNode && subNode.size()>0){
                Long groupId = purchase.getSnowID();
                List<ApprovalQueue> queueList = new ArrayList<>();

                for (NextNode nextNode : subNode) {
                    List<Long> handelUser = nextNode.getNode().getUserId();
                    ApprovalQueue queue = new ApprovalQueue();
                    queue.setGroupId(groupId);
                    queue.setQueueType(QueueTypeEnum.PURCHASE.getDesc());
                    queue.setPurchaseId(purchase.getId());

                    basePurchaseNode = new BasePurchaseNode();
                    basePurchaseNode.setNodeCode(nextNode.getNode().getId());
                    basePurchaseNode.setPurchaseTemplate(purchase.getPurchaseTemplate());
                    basePurchaseNodeList = nodeMapper.selectBasePurchaseNodeList(basePurchaseNode);
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

                if (null != queueList && queueList.size()>0){
                    queueService.factoryOfApproveQueueHandel(queueList);
                }
            }
        }




        return  result;
    }

    /**
     * 批量删除采购申请信息
     * 
     * @param ids 需要删除的采购申请信息主键
     * @return 结果
     */
    @Override
    public int deleteBasePurchaseRequisitionByIds(Long[] ids,Long operator) {
        return basePurchaseRequisitionMapper.deleteBasePurchaseRequisitionByIds(ids,operator);
    }

    /**
     * 删除采购申请信息信息
     * 
     * @param id 采购申请信息主键
     * @return 结果
     */
    @Override
    public int deleteBasePurchaseRequisitionById(Long id,Long operator) {
        return basePurchaseRequisitionMapper.deleteBasePurchaseRequisitionById(id,operator);
    }
}
