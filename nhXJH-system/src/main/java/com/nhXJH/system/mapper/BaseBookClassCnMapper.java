package com.nhXJH.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nhXJH.common.core.domain.entity.SysDept;
import com.nhXJH.system.domain.po.BaseBookClassCn;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 中国标准图书分类规则Mapper接口
 * 
 * @author xjh
 * @date 2022-01-07
 */
@Mapper
public interface BaseBookClassCnMapper extends BaseMapper<BaseBookClassCn> {
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
     * 删除中国标准图书分类规则
     * 
     * @param id 中国标准图书分类规则主键
     * @return 结果
     */
    public int deleteBaseBookClassCnById(Long id);

    /**
     * 批量删除中国标准图书分类规则
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBaseBookClassCnByIds(Long[] ids);

    public List<BaseBookClassCn> getBookClassList(Long parentId);

    public int updateStatus(BaseBookClassCn baseBookClassCn);

    public Integer selectCounts(@Param("id")Long id, @Param("name")String name,@Param("code") String code);

    /**
     * 根据ID批量获取中国标准图书分类规则
     *
     * @param ids id
     * @return 标准图书分类规则列表
     */
    public List<BaseBookClassCn> selectBookClassByIds(Long[] ids);
}
