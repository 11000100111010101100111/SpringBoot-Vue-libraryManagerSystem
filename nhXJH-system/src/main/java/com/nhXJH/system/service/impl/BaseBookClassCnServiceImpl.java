package com.nhXJH.system.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nhXJH.common.enums.BookClaszEnum;
import com.nhXJH.common.exception.ServiceException;
import com.nhXJH.common.utils.DateUtils;
import com.nhXJH.common.utils.SecurityUtils;
import com.nhXJH.common.utils.StringUtils;
import com.nhXJH.system.domain.po.BaseBookClassCn;
import com.nhXJH.system.mapper.BaseBookClassCnMapper;
import com.nhXJH.system.service.IBaseBookClassCnService;
import com.nhXJH.system.utlis.TreeSelect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 中国标准图书分类规则Service业务层处理
 * 
 * @author xjh
 * @date 2022-01-07
 */
@Service
public class BaseBookClassCnServiceImpl extends ServiceImpl<BaseBookClassCnMapper, BaseBookClassCn>
        implements IBaseBookClassCnService {
    @Autowired
    private BaseBookClassCnMapper baseBookClassCnMapper;

    /**
     * 查询中国标准图书分类规则
     * 
     * @param id 中国标准图书分类规则主键
     * @return 中国标准图书分类规则
     */
    @Override
    public BaseBookClassCn selectBaseBookClassCnById(Long id) {
        return baseBookClassCnMapper.selectBaseBookClassCnById(id);
    }

    /**
     * 查询中国标准图书分类规则列表
     * 
     * @param baseBookClassCn 中国标准图书分类规则
     * @return 中国标准图书分类规则
     */
    @Override
    public List<BaseBookClassCn> selectBaseBookClassCnList(BaseBookClassCn baseBookClassCn) {
        return baseBookClassCnMapper.selectBaseBookClassCnList(baseBookClassCn);
    }

    /**
     * 新增中国标准图书分类规则
     * 
     * @param baseBookClassCn 中国标准图书分类规则
     * @return 结果
     */
    @Override
    public int insertBaseBookClassCn(BaseBookClassCn baseBookClassCn) {
        verifyEntity(baseBookClassCn);
        baseBookClassCn.setStatus("1");
        baseBookClassCn.setCreatePersonal(SecurityUtils.getUserId());
        baseBookClassCn.setCreateTime(DateUtils.getNowDate());
        return baseBookClassCnMapper.insertBaseBookClassCn(baseBookClassCn);
    }

    /**
     * 修改中国标准图书分类规则
     * 
     * @param baseBookClassCn 中国标准图书分类规则
     * @return 结果
     */
    @Override
    public int updateBaseBookClassCn(BaseBookClassCn baseBookClassCn) {
        verifyEntity(baseBookClassCn);
        baseBookClassCn.setUpdateTime(DateUtils.getNowDate());
        baseBookClassCn.setUpdatePersonal(SecurityUtils.getUserId());
        return baseBookClassCnMapper.updateBaseBookClassCn(baseBookClassCn);
    }

    boolean verifyEntity(BaseBookClassCn baseBookClassCn){
        if ( StringUtils.isEmpty(baseBookClassCn.getName()) ){
            throw new ServiceException("输入名称不能为空");
        }
        if ( StringUtils.isNull(baseBookClassCn.getClasz()) ||
                StringUtils.isEmpty( BookClaszEnum.getDescByCode(baseBookClassCn.getClasz()) )){
            throw new ServiceException("输入级别不能为空");
        }
        if ( StringUtils.isEmpty(baseBookClassCn.getCode()) ){
            throw new ServiceException("输入编码不能为空");
        }

        //@TODO 查询
//        QueryWrapper<BaseBookClassCn> wrapper = new QueryWrapper<>();
//        wrapper.eq("name",baseBookClassCn.getName())
//                .or().eq("code",baseBookClassCn.getCode());
//        List<BaseBookClassCn> list = baseBookClassCnMapper.selectList(wrapper);
//        if (!StringUtils.isNotNull(list)){
//            throw new ServiceException("输入内容校验失败！");
//        }
//        if ( (StringUtils.isNotNull(list)?list.size():0) > 0){
//            throw new ServiceException("输入内容重复，请重新输入！");
//        }
        int count = baseBookClassCnMapper.selectCounts(baseBookClassCn.getId(),baseBookClassCn.getName(),baseBookClassCn.getCode());
        if (count<0){
            throw new ServiceException("输入内容校验失败！");
        }
        if ( count > 0){
            throw new ServiceException("输入内容重复，请重新输入！");
        }
        return true;
    }
    /**
     * 批量删除中国标准图书分类规则
     * 
     * @param ids 需要删除的中国标准图书分类规则主键
     * @return 结果
     */
    @Override
    public int deleteBaseBookClassCnByIds(Long[] ids) {
        return baseBookClassCnMapper.deleteBaseBookClassCnByIds(ids);
    }

    /**
     * 删除中国标准图书分类规则信息
     * 
     * @param id 中国标准图书分类规则主键
     * @return 结果
     */
    @Override
    public int deleteBaseBookClassCnById(Long id) {
        return baseBookClassCnMapper.deleteBaseBookClassCnById(id);
    }

    @Override
    public void importData(){
        StringBuilder json = new StringBuilder();
        File file = new File("D:\\桌面\\毕设\\doc\\图书分类json.txt");
        BufferedReader reader = null;
        try {

            reader = new BufferedReader(new FileReader(file));
            String tempString = null;

            while ((tempString = reader.readLine()) != null) {
                json.append(tempString);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }

        List<BaseBookClassCn> classCnList = JSONObject.parseArray(json.toString(),BaseBookClassCn.class);
        this.saveBatch(classCnList);
    }

    /**
     * 根据父ID获取图书子类别列表
     * */
    public List<BaseBookClassCn> getBookClassList(Long parentId){
        QueryWrapper<BaseBookClassCn> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id" ,parentId).eq("status",1);
        List<BaseBookClassCn> list = baseBookClassCnMapper.selectList(wrapper);
        return list;
    }

    @Override
    public List<BaseBookClassCn> getBookClassListFather(){
        return this.getBookClassList(-1l);
    }

    @Override
    public List<BaseBookClassCn> getBookClassListSub(Long parentId){
        return getBookClassList(parentId);
    }

    /**
     * 修改圖書状态
     *
     * @param baseBookClassCn 圖書信息
     * @return 结果
     */
    @Override
    public int updateStatus(BaseBookClassCn baseBookClassCn) {
        return baseBookClassCnMapper.updateStatus(baseBookClassCn);
    }

    @Override
    public List<TreeSelect> tree(){
//        QueryWrapper<BaseBookClassCn> wrapper = new QueryWrapper<>();
//        wrapper.eq("status",1);
        BaseBookClassCn baseBookClassCn = new BaseBookClassCn();
        baseBookClassCn.setStatus("1");
        List<BaseBookClassCn> list = baseBookClassCnMapper.selectBaseBookClassCnList(baseBookClassCn);//baseBookClassCnMapper.selectList(wrapper);
        if (null == list){
            throw new ServiceException("无结果");
        }
        list = buildDeptTree(list);
//        List<String> value = new ArrayList<>();
//        for (BaseBookClassCn baseBookClassCn : list) {
//            value.add(
//                    (new StringBuilder())
//                            .append(baseBookClassCn.getName())
//                            .append("(").append(baseBookClassCn.getCode()).append(")")
//                    .toString());
//        }
        return list.stream().map(TreeSelect::new).collect(Collectors.toList());
    }
    @Override
    public List<TreeSelect> treeBook(){
//        QueryWrapper<BaseBookClassCn> wrapper = new QueryWrapper<>();
//        wrapper.eq("status",1);
        BaseBookClassCn baseBookClassCn = new BaseBookClassCn();
        baseBookClassCn.setStatus("1");
        List<BaseBookClassCn> list = baseBookClassCnMapper.selectBaseBookClassCnList(baseBookClassCn);//baseBookClassCnMapper.selectList(wrapper);
        if (null == list){
            throw new ServiceException("无结果");
        }
        List<BaseBookClassCn> listBook = list.stream().filter(claz->claz.getParentId() != -2L).collect(Collectors.toList());
        list = buildDeptTree(listBook);
//        List<String> value = new ArrayList<>();
//        for (BaseBookClassCn baseBookClassCn : list) {
//            value.add(
//                    (new StringBuilder())
//                            .append(baseBookClassCn.getName())
//                            .append("(").append(baseBookClassCn.getCode()).append(")")
//                    .toString());
//        }
        return list.stream().map(TreeSelect::new).collect(Collectors.toList());
    }
    @Override
    public List<TreeSelect> treeResource(){
//        QueryWrapper<BaseBookClassCn> wrapper = new QueryWrapper<>();
//        wrapper.eq("status",1);
        BaseBookClassCn baseBookClassCn = new BaseBookClassCn();
        baseBookClassCn.setStatus("1");
        List<BaseBookClassCn> list = baseBookClassCnMapper.selectBaseBookClassCnList(baseBookClassCn);//baseBookClassCnMapper.selectList(wrapper);
        if (null == list){
            throw new ServiceException("无结果");
        }
        List<BaseBookClassCn> listResource = list.stream().filter(claz->claz.getParentId() == -2L).collect(Collectors.toList());
        list = buildDeptTree(listResource);
//        List<String> value = new ArrayList<>();
//        for (BaseBookClassCn baseBookClassCn : list) {
//            value.add(
//                    (new StringBuilder())
//                            .append(baseBookClassCn.getName())
//                            .append("(").append(baseBookClassCn.getCode()).append(")")
//                    .toString());
//        }
        return list.stream().map(TreeSelect::new).collect(Collectors.toList());
    }

    /**
     * 构建前端所需要树结构
     *
     * @param bbc 部门列表
     * @return 树结构列表
     */
    public List<BaseBookClassCn> buildDeptTree(List<BaseBookClassCn> bbc) {
        List<BaseBookClassCn> returnList = new ArrayList<BaseBookClassCn>();
        List<Long> tempList = new ArrayList<Long>();
        for (BaseBookClassCn bbcc : bbc) {
            tempList.add(bbcc.getId());
        }
        for (Iterator<BaseBookClassCn> iterator = bbc.iterator(); iterator.hasNext();) {
            BaseBookClassCn bbcc = (BaseBookClassCn) iterator.next();
            // 如果是顶级节点, 遍历该父节点的所有子节点
            if (!tempList.contains(bbcc.getParentId())) {
                recursionFn(bbc, bbcc);
                returnList.add(bbcc);
            }
        }
        if (returnList.isEmpty()) {
            returnList = bbc;
        }
        return returnList;
    }
    /**
     * 递归列表
     */
    private void recursionFn(List<BaseBookClassCn> list, BaseBookClassCn t) {
        // 得到子节点列表
        List<BaseBookClassCn> childList = getChildList(list, t);
        t.setChildren(childList);
        for (BaseBookClassCn tChild : childList) {
            if (hasChild(list, tChild)) {
                recursionFn(list, tChild);
            }
        }
    }
    /**
     * 得到子节点列表
     */
    private List<BaseBookClassCn> getChildList(List<BaseBookClassCn> list, BaseBookClassCn t) {
        List<BaseBookClassCn> tlist = new ArrayList<BaseBookClassCn>();
        Iterator<BaseBookClassCn> it = list.iterator();
        while (it.hasNext()) {
            BaseBookClassCn n = (BaseBookClassCn) it.next();
            if (StringUtils.isNotNull(n.getParentId()) && n.getParentId().longValue() == t.getId().longValue()) {
                tlist.add(n);
            }
        }
        return tlist;
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<BaseBookClassCn> list, BaseBookClassCn t) {
        return getChildList(list, t).size() > 0;
    }
}
