package com.nhXJH.web.service.impl;

import java.util.*;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.nhXJH.common.core.domain.entity.SysUser;
import com.nhXJH.common.exception.ServiceException;
import com.nhXJH.common.utils.StringUtils;
import com.nhXJH.enums.*;
import com.nhXJH.system.mapper.SysUserMapper;
import com.nhXJH.web.domain.BasePurchaseNode;
import com.nhXJH.web.domain.BasePurchaseRequisition;
import com.nhXJH.web.domain.PurchaseTemplate;
import com.nhXJH.common.utils.DateUtils;
import com.nhXJH.web.domain.param.purchase.PurchaseNode;
import com.nhXJH.web.domain.param.purchase.TemplateParam;
import com.nhXJH.web.mapper.BasePurchaseNodeMapper;
import com.nhXJH.web.mapper.BasePurchaseRequisitionMapper;
import com.nhXJH.web.mapper.PurchaseRequisitionMapper;
import com.nhXJH.web.util.TemplateUtils;
import com.nhXJH.web.util.collections.OperatorCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nhXJH.web.mapper.PurchaseTemplateMapper;
//import com.nhXJH.web.domain.PurchaseTemplate;
import com.nhXJH.web.service.IPurchaseTemplateService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 审批流程模板Service业务层处理
 * 
 * @author xjh
 * @date 2022-01-25
 */
@Service
public class PurchaseTemplateServiceImpl extends ServiceImpl<PurchaseTemplateMapper, PurchaseTemplate>
        implements IPurchaseTemplateService  {
    @Autowired
    private PurchaseTemplateMapper purchaseTemplateMapper;
    @Autowired
    private SysUserMapper userMapper;
    @Autowired
    private OperatorCollection operatorCollection;
    @Autowired
    private BasePurchaseRequisitionMapper purchaseRequisitionMapper;
    @Autowired
    private BasePurchaseNodeMapper nodeMapper;

    /**
     * 查询审批流程模板
     * 
     * @param id 审批流程模板主键
     * @return 审批流程模板
     */
    @Override
    public PurchaseTemplate selectPurchaseTemplateById(Long id) {
        PurchaseTemplate template = purchaseTemplateMapper.selectPurchaseTemplateById(id);
        if (null == template){
            return new PurchaseTemplate();
        }

        String json = template.getTemplate();//StringUtils.replaceBlank(template.getTemplate());
        TemplateParam param = JSONObject.parseObject(json,new TypeReference<TemplateParam>() {});
        Set<Long> idSet = this.getUserId(param);
        Map<Long, SysUser> userMap = null;
        if (null != idSet && idSet.size()>0) {
            List<SysUser> userList = userMapper.selectUserByIds(operatorCollection.toArray(idSet));
            userMap = this.getUserMap(userList);
        }
        this.setUser(template,userMap);
        return template;
    }

    @Override
    public Integer getCount(PurchaseTemplate purchaseTemplate) {
        return purchaseTemplateMapper.getCount(purchaseTemplate);
    }

    /**
     * 查询审批流程模板列表
     * 
     * @param purchaseTemplate 审批流程模板
     * @return 审批流程模板
     */
    @Override
    public List<PurchaseTemplate> selectPurchaseTemplateList(PurchaseTemplate purchaseTemplate) {
        return purchaseTemplateMapper.selectPurchaseTemplateList(purchaseTemplate);
    }

    /**
     * 新增审批流程模板
     * 
     * @param purchaseTemplate 审批流程模板
     * @return 结果
     */
    @Override
    public int insertPurchaseTemplate(PurchaseTemplate purchaseTemplate) {
        purchaseTemplate.setCreateTime(DateUtils.getNowDate());
        if (null == purchaseTemplate.getTemplate()){
            TemplateParam param = new TemplateParam();
            param.setName(purchaseTemplate.getTitle());
            param.setLineList(new ArrayList<>());
            param.setNodeList(new ArrayList<>());
            purchaseTemplate.setTemplate(JSON.toJSONString(param));
        }
        return purchaseTemplateMapper.insertPurchaseTemplate(purchaseTemplate);
    }

    /**
     * 修改审批流程模板
     * 
     * @param purchaseTemplate 审批流程模板
     * @return 结果
     */
    @Override
    @Transactional
    public int updatePurchaseTemplate(PurchaseTemplate purchaseTemplate) {

        boolean onlyChangeMsg = true;//只能修改基本信息，不能修改模板信息
        if (null != purchaseTemplate && null != purchaseTemplate.getId()) {
            BasePurchaseRequisition query = new BasePurchaseRequisition();
            query.setStatus(StatusEnums.ONE.getDesc());
            query.setPurchaseTemplate(purchaseTemplate.getId());
            List<BasePurchaseRequisition> list = purchaseRequisitionMapper.selectBasePurchaseRequisitionList(query);
            onlyChangeMsg = null !=list && list.size()>0;

            PurchaseTemplate template = purchaseTemplateMapper.selectPurchaseTemplateById(purchaseTemplate.getId());
//            onlyChangeMsg = null!=template
//                    && null!=template.getTemplate()
//                    && (!TemplateUtils.EMPTY_OF_DEFAULT_TEMPLATE.equals(template.getTemplate())
//                    || !TemplateUtils.UNKNOW_TEMPLATE.equals(template.getTemplate()) );
            if (onlyChangeMsg) {//只能修改基本信息时，流程模板不变
                purchaseTemplate.setTemplate(template.getTemplate());
            }
        }
        this.setUser(purchaseTemplate, null);
        purchaseTemplate.setUpdateTime(DateUtils.getNowDate());

        if(!onlyChangeMsg) {
            BasePurchaseNode query = new BasePurchaseNode();
            query.setPurchaseTemplate(purchaseTemplate.getId());
            query.setIsDel(DelEnums.IS_DEL.getValue());
            List<BasePurchaseNode> nodeList = nodeMapper.selectBasePurchaseNodeList(query);
            onlyChangeMsg = null != nodeList && nodeList.size()>0;
            if(onlyChangeMsg) {
                Long[] nodeIds = new Long[nodeList.size()];
                for (int i = 0; i < nodeList.size(); i++) {
                    nodeIds[i] = nodeList.get(i).getId();
                }
                nodeMapper.deleteBasePurchaseNodeByIds(nodeIds, purchaseTemplate.getUpdatePersonal());
            }

                TemplateParam param = TemplateUtils.JsonParseTemplate(purchaseTemplate.getTemplate());
                if (null == param) {
                    throw new ServiceException("模板格式不正确");
                }
                List<PurchaseNode> nodes = param.getNodeList();
                nodeList = new ArrayList<>();
                if (null != nodes) {
                    for (PurchaseNode node : nodes) {
                        BasePurchaseNode item = new BasePurchaseNode();
                        item.setId(item.getSnowID());
                        item.setPurchaseTemplate(purchaseTemplate.getId());
                        item.setName(node.getName());
                        item.setMark(node.getMark());
                        item.setNodeCode(node.getId());
                        Integer type = EnumUtils.getValueByDesc(LinkNodeType.class.getEnumConstants(), node.getType());
                        item.setType(null == type ? LinkNodeType.OVER.getValue().toString() : type.toString());
                        item.setIsDel(DelEnums.NOT_DEL.getValue());
                        item.setStatus(StatusEnums.ONE.getDesc());
                        item.setCreatePersonal(purchaseTemplate.getUpdatePersonal());
                        item.setUpdateTime(new Date());
                        item.setUpdatePersonal(purchaseTemplate.getUpdatePersonal());
                        nodeList.add(item);
                    }

                    nodeMapper.insertList(nodeList);
                }
        }

        return purchaseTemplateMapper.updatePurchaseTemplate(purchaseTemplate);
    }

    /**
     * 批量删除审批流程模板
     * 
     * @param ids 需要删除的审批流程模板主键
     * @return 结果
     */
    @Override
    public int deletePurchaseTemplateByIds(Long[] ids) {
        return purchaseTemplateMapper.deletePurchaseTemplateByIds(ids);
    }

    /**
     * 删除审批流程模板信息
     * 
     * @param id 审批流程模板主键
     * @return 结果
     */
    @Override
    public int deletePurchaseTemplateById(Long id) {
        return purchaseTemplateMapper.deletePurchaseTemplateById(id);
    }

    void setUser(PurchaseTemplate purchaseTemplate,Map<Long,SysUser> userMap){
        Boolean flag = null == userMap;
        String template = flag? StringUtils.replaceBlank(purchaseTemplate.getTemplate()) :purchaseTemplate.getTemplate();
        TemplateParam param = JSONObject.parseObject(template,new TypeReference<TemplateParam>() {});
        List<PurchaseNode> nodes = param.getNodeList();
        if (null != nodes) {
            for (PurchaseNode node : nodes) {
                node.setUser(flag ? new ArrayList<>() : this.getUser(node.getUserId(), userMap));
            }
        }else{
            param.setNodeList(new ArrayList<>());
            param.setLineList(new ArrayList<>());
        }
        purchaseTemplate.setTemplate(JSON.toJSONString(param));
    }
    List<SysUser> getUser(List<Long> userId, Map<Long,SysUser> map){
        List<SysUser> list = new ArrayList<>();
        if (null == userId || null == map){
            return list;
        }
        for (Long aLong : userId) {
            list.add(map.get(aLong));
        }
        return list;
    }
    Set<Long> getUserId(TemplateParam param){
        Set<Long> set = new HashSet();
        if (null==param||null ==param.getNodeList()){
            return set;
        }
        List<PurchaseNode> node = param.getNodeList();
        for (PurchaseNode n : node) {
            if (null!=n && null !=n.getUserId()) {
                for(Long id : n.getUserId()){
                    set.add(id);
                }
            }
        }
        return set;
    }

    Map<Long,SysUser> getUserMap(List<SysUser> userList){
        Map<Long,SysUser> map = new HashMap<>();
        if (null == userList){
            return map;
        }
        for (SysUser u : userList) {
            map.put(u.getUserId(),u);
        }
        return map;
    }
}
