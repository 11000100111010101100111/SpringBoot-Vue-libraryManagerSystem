package com.nhXJH.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nhXJH.system.domain.po.BaseBookClassCn;
import com.nhXJH.system.utlis.TreeSelect;

import java.util.List;

/**
 * 中国标准图书分类规则Service接口
 * 
 * @author xjh
 * @date 2022-01-07
 */
public interface IBaseBookClassCnService extends IService<BaseBookClassCn> {
    /**
     * 查询中国标准图书分类规则
     * 
     * @param id 中国标准图书分类规则主键
     * @return 中国标准图书分类规则
     */
    public BaseBookClassCn selectBaseBookClassCnById(Long id);

    /**
     * 查询中国标准图书分类规则列表
     * 
     * @param baseBookClassCn 中国标准图书分类规则
     * @return 中国标准图书分类规则集合
     */
    public List<BaseBookClassCn> selectBaseBookClassCnList(BaseBookClassCn baseBookClassCn);

    /**
     * 新增中国标准图书分类规则
     * 
     * @param baseBookClassCn 中国标准图书分类规则
     * @return 结果
     */
    public int insertBaseBookClassCn(BaseBookClassCn baseBookClassCn);

    /**
     * 修改中国标准图书分类规则
     * 
     * @param baseBookClassCn 中国标准图书分类规则
     * @return 结果
     */
    public int updateBaseBookClassCn(BaseBookClassCn baseBookClassCn);

    /**
     * 批量删除中国标准图书分类规则
     * 
     * @param ids 需要删除的中国标准图书分类规则主键集合
     * @return 结果
     */
    public int deleteBaseBookClassCnByIds(Long[] ids);

    /**
     * 删除中国标准图书分类规则信息
     * 
     * @param id 中国标准图书分类规则主键
     * @return 结果
     */
    public int deleteBaseBookClassCnById(Long id);

    public void importData();

    public List<BaseBookClassCn> getBookClassListSub(Long parentId);
    public List<BaseBookClassCn> getBookClassListFather();

    public int updateStatus(BaseBookClassCn baseBookClassCn);

    public List<TreeSelect> tree();
    public List<TreeSelect> treeBook();
    public List<TreeSelect> treeResource();
}
