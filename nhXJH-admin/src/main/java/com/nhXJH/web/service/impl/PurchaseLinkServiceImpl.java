package com.nhXJH.web.service.impl;

import java.util.*;

import com.nhXJH.common.core.domain.entity.SysUser;
import com.nhXJH.system.mapper.SysUserMapper;
import com.nhXJH.web.domain.BasePurchaseNode;
import com.nhXJH.web.domain.BasePurchaseRequisition;
import com.nhXJH.web.domain.PurchaseLink;
import com.nhXJH.common.utils.DateUtils;
import com.nhXJH.web.domain.vo.PurchaseLinkNodeVO;
import com.nhXJH.web.mapper.BasePurchaseNodeMapper;
import com.nhXJH.web.mapper.BasePurchaseRequisitionMapper;
import com.nhXJH.web.util.FatherToChildUtils;
import com.nhXJH.web.util.collections.OperatorCollection;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nhXJH.web.mapper.PurchaseLinkMapper;
//import com.nhXJH.web.domain.PurchaseLink;
import com.nhXJH.web.service.IPurchaseLinkService;

/**
 * 采购申请结点处理Service业务层处理
 * 
 * @author xjh
 * @date 2022-01-25
 */
@Service
public class PurchaseLinkServiceImpl extends ServiceImpl<PurchaseLinkMapper, PurchaseLink>
        implements IPurchaseLinkService  {
    @Autowired
    private PurchaseLinkMapper purchaseLinkMapper;
    @Autowired
    private SysUserMapper userMapper;
    @Autowired
    private BasePurchaseRequisitionMapper requisitionMapper;
    @Autowired
    private BasePurchaseNodeMapper nodeMapper;
    @Autowired
    private OperatorCollection operatorCollection;

    /**
     * 查询采购申请结点处理
     * 
     * @param id 采购申请结点处理主键
     * @return 采购申请结点处理
     */
    @Override
    public PurchaseLink selectPurchaseLinkById(Long id) {
        return purchaseLinkMapper.selectPurchaseLinkById(id);
    }

    @Override
    public Integer getCount(PurchaseLink purchaseLink) {
        return purchaseLinkMapper.getCount(purchaseLink);
    }

    /**
     * 查询采购申请结点处理列表
     * 
     * @param purchaseLink 采购申请结点处理
     * @return 采购申请结点处理
     */
    @Override
    public List<PurchaseLink> selectPurchaseLinkList(PurchaseLink purchaseLink) {
        return purchaseLinkMapper.selectPurchaseLinkList(purchaseLink);
    }

    /**
     * 查询采购申请结点处理列表
     *
     * @param purchaseLink 采购申请结点处理
     * @return 采购申请结点处理
     */
    @Override
    public List<PurchaseLinkNodeVO> selectPurchaseLink(PurchaseLink purchaseLink) {
        List<PurchaseLinkNodeVO> vo = new ArrayList<>();
        List<PurchaseLink> list = purchaseLinkMapper.selectPurchaseLinkList(purchaseLink);
        if (null == list || list.size()<1){
            return vo;
        }
        Set<Long> userIdSet = new HashSet<>();//用户（创建人，更新人，处理人）
        Set<Long> approveIdSet = new HashSet<>();//申请信息
        Set<Long> nodeIdSet = new HashSet<>();//结点信息
        for (PurchaseLink link : list) {
            userIdSet.add(link.getCreatePersonal());
            userIdSet.add(link.getHandlePersonal());
            userIdSet.add(link.getUpdatePersonal());

            approveIdSet.add(link.getApproveId());

            nodeIdSet.add(link.getPurchaseNode());
        }

        Map<Long,SysUser> userMap = new HashMap<>();
        List<SysUser> userList = userMapper.selectUserByIds(operatorCollection.toArray(userIdSet));
        if(null != userList && userList.size()>0) {
            for (SysUser user : userList) {
                userMap.put(user.getUserId(),user);
            }
        }

        Map<Long,BasePurchaseNode> purchaseNodeMap = new HashMap<>();
        List<BasePurchaseNode> nodeList = nodeMapper.selectBasePurchaseNodeByIds(operatorCollection.toArray(nodeIdSet));
        if(null != nodeList && nodeList.size()>0) {
            for (BasePurchaseNode basePurchaseNode : nodeList) {
                purchaseNodeMap.put(basePurchaseNode.getId(),basePurchaseNode);
            }
        }


        for (PurchaseLink link : list) {
            PurchaseLinkNodeVO node = new PurchaseLinkNodeVO();
//            FatherToChildUtils.fatherToChild(link,node);
            node.setVO(link);
            node.setCreateUser(userMap.get(link.getCreatePersonal()));
            node.setUpdateUser(userMap.get(link.getUpdatePersonal()));
            node.setPurchaseNodeEntity(purchaseNodeMap.get(link.getPurchaseNode()));
            node.setHandelPersonalEntity(userMap.get(link.getHandlePersonal()));
            vo.add(node);
        }
        //if(null != approveIdSet && approveIdSet.size()>0) {
        //    PurchaseLinkNodeVO node = new PurchaseLinkNodeVO();
        //}

            return vo;
    }

    /**
     * 新增采购申请结点处理
     * 
     * @param purchaseLink 采购申请结点处理
     * @return 结果
     */
    @Override
    public int insertPurchaseLink(PurchaseLink purchaseLink) {
        purchaseLink.setCreateTime(DateUtils.getNowDate());
        return purchaseLinkMapper.insertPurchaseLink(purchaseLink);
    }

    /**
     * 修改采购申请结点处理
     * 
     * @param purchaseLink 采购申请结点处理
     * @return 结果
     */
    @Override
    public int updatePurchaseLink(PurchaseLink purchaseLink) {
        purchaseLink.setUpdateTime(DateUtils.getNowDate());
        return purchaseLinkMapper.updatePurchaseLink(purchaseLink);
    }

    /**
     * 批量删除采购申请结点处理
     * 
     * @param ids 需要删除的采购申请结点处理主键
     * @return 结果
     */
    @Override
    public int deletePurchaseLinkByIds(Long[] ids,Long operator) {
        return purchaseLinkMapper.deletePurchaseLinkByIds(ids,operator);
    }

    /**
     * 删除采购申请结点处理信息
     * 
     * @param id 采购申请结点处理主键
     * @return 结果
     */
    @Override
    public int deletePurchaseLinkById(Long id,Long operator) {
        return purchaseLinkMapper.deletePurchaseLinkById(id,operator);
    }
}
