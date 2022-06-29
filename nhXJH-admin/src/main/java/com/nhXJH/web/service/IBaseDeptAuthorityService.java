package com.nhXJH.web.service;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;
import com.nhXJH.common.core.domain.entity.SysDept;
import com.nhXJH.web.domain.BaseCountry;
import com.nhXJH.web.domain.BaseDeptAuthority;
import com.nhXJH.web.domain.param.DeptAuthorityParam;
import com.nhXJH.web.domain.vo.DeptAuthorityVO;

/**
 * 用户可访问部门信息Service接口
 * 
 * @author xjh
 * @date 2022-02-27
 */
public interface IBaseDeptAuthorityService extends IService<BaseDeptAuthority>{
    /**
     * 查询用户可访问部门信息
     * 
     * @param id 用户可访问部门信息主键
     * @return 用户可访问部门信息
     */
    public BaseDeptAuthority selectBaseDeptAuthorityById(Long id);
    public Integer getCount(BaseDeptAuthority baseDeptAuthority);
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
     * @param list 用户可访问部门信息
     * @return 用户可访问部门信息集合
     */
    public List<DeptAuthorityVO> selectBaseDeptAuthorityVOList(List<BaseDeptAuthority> list);

    public List<SysDept> getDepts(Long uId);
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
     * @param ids 用户可访问部门信息
     * @return 结果
     */
    public String insertBaseDeptAuthoritys(Long[] ids,Long createUser);
    /**
     * 新增用户可访问部门信息
     *
     * @param param 用户可访问部门信息
     * @return 结果
     */
    public String insertBaseDeptAuthoritys(DeptAuthorityParam param);
    /**
     * 修改用户可访问部门信息
     * 
     * @param baseDeptAuthority 用户可访问部门信息
     * @return 结果
     */
    public int updateBaseDeptAuthority(BaseDeptAuthority baseDeptAuthority);

    /**
     * 批量删除用户可访问部门信息
     * 
     * @param ids 需要删除的用户可访问部门信息主键集合
     * @return 结果
     */
    public int deleteBaseDeptAuthorityByIds(Long[] ids,Long operator);

    /**
     * 删除用户可访问部门信息信息
     * 
     * @param id 用户可访问部门信息主键
     * @return 结果
     */
    public int deleteBaseDeptAuthorityById(Long id,Long operator);
}
