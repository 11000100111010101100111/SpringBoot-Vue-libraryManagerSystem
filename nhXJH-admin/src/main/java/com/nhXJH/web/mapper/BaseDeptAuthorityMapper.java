package com.nhXJH.web.mapper;

import java.util.List;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nhXJH.web.domain.BaseCountry;
import com.nhXJH.web.domain.BaseDeptAuthority;
import com.nhXJH.web.mapper.base.MyBase;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;

/**
 * 用户可访问部门信息Mapper接口
 * 
 * @author xjh
 * @date 2022-02-27
 */
@Mapper
public interface BaseDeptAuthorityMapper  extends BaseMapper<BaseDeptAuthority> {
    /**
     * 查询用户可访问部门信息
     * 
     * @param id 用户可访问部门信息主键
     * @return 用户可访问部门信息
     */
    public BaseDeptAuthority selectBaseDeptAuthorityById(Long id);
    public Integer getCount(BaseDeptAuthority baseDeptAuthority);
    public List<Long> getDepts(Long uid);
    public List<BaseDeptAuthority> getList();
    /**
     * 查询用户可访问部门信息列表
     * 
     * @param baseDeptAuthority 用户可访问部门信息
     * @return 用户可访问部门信息集合
     */
    public List<BaseDeptAuthority> selectBaseDeptAuthorityList(BaseDeptAuthority baseDeptAuthority);

    /**
     * 查询用户可访问部门信息列表
     *
     * @param ids 用户可访问部门信息
     * @return 用户可访问部门信息集合
     */
    public List<BaseDeptAuthority> selectBaseDeptAuthorityByIds(@Param("array")Long[] ids);

    /**
     * 查询用户可访问部门信息列表
     *
     * @param userIds 用户可访问部门信息
     * @return 用户可访问部门信息集合
     */
    public List<BaseDeptAuthority> selectBaseDeptAuthorityByUserIds(@Param("array") Long[] userIds);

    /**
     * 新增用户可访问部门信息
     * 
     * @param baseDeptAuthority 用户可访问部门信息
     * @return 结果
     */
    public int insertBaseDeptAuthority(BaseDeptAuthority baseDeptAuthority);
    /**
     * 新增用户可访问部门信息
     *
     * @param list 用户可访问部门信息
     * @return 结果
     */
    public int insertList(List<BaseDeptAuthority> list);

    /**
     * 修改用户可访问部门信息
     * 
     * @param baseDeptAuthority 用户可访问部门信息
     * @return 结果
     */
    public int updateBaseDeptAuthority(BaseDeptAuthority baseDeptAuthority);

    /**
     * 删除用户可访问部门信息
     * 
     * @param id 用户可访问部门信息主键
     * @return 结果
     */
    public int deleteBaseDeptAuthorityById(@Param("id") Long id,@Param("operator") Long operatorUser);

    /**
     * 批量删除用户可访问部门信息
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBaseDeptAuthorityByIds(@Param("array") Long[] ids,@Param("operator") Long operatorUser);

    /**
     * 批量删除用户可访问部门信息
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBaseDeptAuthorityByUserId(@Param("userId") Long ids,@Param("operator") Long operatorUser);
}
