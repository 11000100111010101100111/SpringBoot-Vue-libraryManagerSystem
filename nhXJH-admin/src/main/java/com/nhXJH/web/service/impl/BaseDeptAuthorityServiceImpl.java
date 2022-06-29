package com.nhXJH.web.service.impl;

import java.util.*;

import com.nhXJH.common.core.domain.entity.SysDept;
import com.nhXJH.common.core.domain.entity.SysUser;
import com.nhXJH.common.exception.ServiceException;
import com.nhXJH.common.utils.DateUtils;
import com.nhXJH.enums.DelEnums;
import com.nhXJH.enums.StatusEnums;
import com.nhXJH.system.mapper.SysDeptMapper;
import com.nhXJH.system.mapper.SysUserMapper;
import com.nhXJH.web.domain.BaseDeptAuthority;
import com.nhXJH.web.domain.param.DeptAuthorityParam;
import com.nhXJH.web.domain.vo.DeptAuthorityVO;
import com.nhXJH.web.mapper.BaseDeptAuthorityMapper;
import com.nhXJH.web.service.IBaseDeptAuthorityService;
import com.nhXJH.web.util.collections.OperatorCollection;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户可访问部门信息Service业务层处理
 * 
 * @author xjh
 * @date 2022-02-27
 */
@Service
public class BaseDeptAuthorityServiceImpl extends ServiceImpl<BaseDeptAuthorityMapper, BaseDeptAuthority>
        implements IBaseDeptAuthorityService {
    @Autowired
    private BaseDeptAuthorityMapper baseDeptAuthorityMapper;
    @Autowired
    private SysUserMapper userMapper;
    @Autowired
    private SysDeptMapper deptMapper;

    @Autowired
    private OperatorCollection operatorCollection;
    /**
     * 查询用户可访问部门信息
     * 
     * @param id 用户可访问部门信息主键
     * @return 用户可访问部门信息
     */
    @Override
    public BaseDeptAuthority selectBaseDeptAuthorityById(Long id) {
        return baseDeptAuthorityMapper.selectBaseDeptAuthorityById(id);
    }

    @Override
    public Integer getCount(BaseDeptAuthority baseDeptAuthority) {
        return baseDeptAuthorityMapper.getCount(baseDeptAuthority);
    }

    /**
     * 查询用户可访问部门信息列表
     * 
     * @param baseDeptAuthority 用户可访问部门信息
     * @return 用户可访问部门信息
     */
    @Override
    public List<BaseDeptAuthority> selectBaseDeptAuthorityList(BaseDeptAuthority baseDeptAuthority) {
        return baseDeptAuthorityMapper.selectBaseDeptAuthorityList(baseDeptAuthority);
    }

    public List<SysDept> getDepts(Long uId){
        if (null == uId){
            throw new ServiceException("查询失败");
        }
        List<Long> deptIds = baseDeptAuthorityMapper.getDepts(uId);
        Long[] arr = new Long[deptIds.size()];
        for (int i = 0; i < deptIds.size(); i++) {
            arr[i] = deptIds.get(i);
        }
        return deptMapper.selectDeptByIds(arr);
    }
    /**
     * 查询用户可访问部门信息列表
     *
     * @param list 用户可访问部门信息
     * @return 用户可访问部门信息集合
     */
    public List<DeptAuthorityVO> selectBaseDeptAuthorityVOList(List<BaseDeptAuthority> list){
//        List<BaseDeptAuthority> list = baseDeptAuthorityMapper.getList();
        if (null == list ||list.size()<=0){
            return new ArrayList<>();
        }
        List<BaseDeptAuthority> authorityList = new ArrayList<>();

        // @TODO 可优化，去重查询有权限的用户
        Map<Long,BaseDeptAuthority> listSet = new HashMap<>();
        for (BaseDeptAuthority authority : list) {
//            if (!listSet.containsKey(authority.getUserId())){
//                authorityList.add(authority);
                listSet.put(authority.getUserId(),authority);
//            }
        }
        listSet.forEach((k,v)->{
            authorityList.add(v);
        });

        Set<Long> uidSet = new HashSet<>();
        Set<Long> deptidSet = new HashSet<>();
        authorityList.forEach(a ->{
            uidSet.add(a.getUserId());
            uidSet.add(a.getCreatePersonal());
            uidSet.add(a.getUpdatePersonal());
            deptidSet.add(a.getDeptId());
        });

        List<SysUser> userList = userMapper.selectUserByIds(operatorCollection.toArray(uidSet));
        List<SysDept> deptList = deptMapper.selectDeptByIds(operatorCollection.toArray(deptidSet));

        Map<Long,SysUser> userMap = new HashMap<>();
        if (null != userList){
            userList.forEach(u->{
                userMap.put(u.getUserId(),u);
            });
        }

        Map<Long,SysDept> deptMap = new HashMap<>();
        if (null != deptList){
            deptList.forEach(d->deptMap.put(d.getDeptId(),d));
        }

        List<DeptAuthorityVO> result = new ArrayList<>();
        authorityList.forEach(a->{
            DeptAuthorityVO vo = new DeptAuthorityVO();
            vo.setCreateUser(userMap.get(a.getCreatePersonal()));
            vo.setUpdateUser(userMap.get(a.getUpdatePersonal()));
            vo.setDept(deptMap.get(a.getDeptId()));
            vo.setUser(userMap.get(a.getUserId()));
            vo.setStatus(a.getStatus());
            vo.setId(a.getId());
            vo.setUserId(a.getUserId());
            vo.setDeptId(a.getDeptId());
            result.add(vo);
        });

        return result;
    }
    /**
     * 新增用户可访问部门信息
     * 
     * @param baseDeptAuthority 用户可访问部门信息
     * @return 结果
     */
    @Override
    public int insertBaseDeptAuthority(BaseDeptAuthority baseDeptAuthority) {
        baseDeptAuthority.setCreateTime(DateUtils.getNowDate());
        return baseDeptAuthorityMapper.insertBaseDeptAuthority(baseDeptAuthority);
    }
    /**
     * 新增用户可访问部门信息
     *
     * @param ids 用户可访问部门信息
     * @return 结果
     */
    public String insertBaseDeptAuthoritys(Long[] ids,Long createUser){
        if (null == ids || ids.length<=0){
            throw new ServiceException("没有选择用户，新增失败");
        }
        StringBuffer message = new StringBuffer();
        message.append("本次成功添加：");
        StringBuffer error = new StringBuffer();
        error.append("重复记录：");
        List<BaseDeptAuthority> authorities = baseDeptAuthorityMapper.selectBaseDeptAuthorityByUserIds(ids);
        Set<Long> uIdSet = new HashSet<>();
        authorities.forEach(a->uIdSet.add(a.getUserId()));

        List<SysUser> users = userMapper.selectUserByIds(ids);
        List<BaseDeptAuthority> list = new ArrayList<>();
        for (SysUser u : users) {
            if (uIdSet.contains(u.getUserId())){
                error.append(u.getUserName()).append("、");
                continue;
            }
            BaseDeptAuthority authority = new BaseDeptAuthority();
            authority.setUserId(u.getUserId());
            authority.setDeptId(u.getDeptId());
            authority.setCreatePersonal(createUser);
            authority.setCreateTime(new Date());
            authority.setId(authority.getSnowID());
            list.add(authority);
            message.append(u.getUserName()).append("、");
        }

        error.setCharAt(error.length()-1,',');
        message.setCharAt(message.length()-1,',');


        int succeed = 0;
        if (null != list && list.size()>0){
            succeed = baseDeptAuthorityMapper.insertList(list);
        }

        message.append("共：").append(succeed).append("条");
        int errorCount = (ids.length-succeed);
        error.append("合计：").append(errorCount).append("条");

        message.append(";").append(error);
        return message.toString();
    }

    /**
     * 新增用户可访问部门信息
     *
     * @param param 用户可访问部门信息
     * @return 结果
     */
    @Transactional
    public String insertBaseDeptAuthoritys(DeptAuthorityParam param){
        if (null == param || null == param.getUserId() || null == param.getDeptIds()){
            throw new ServiceException("提交失败");
        }
        Long operatorUser = param.getOperatorUser();
        Long user = param.getUserId();
        //清空原有部门访问权限
        baseDeptAuthorityMapper.deleteBaseDeptAuthorityByUserId(user,operatorUser);

        List<Long> depts = param.getDeptIds();
        //新增新的部门访问权限
        if(depts.size()<=0){
            return  "操作成功";
        }

        List<BaseDeptAuthority> list = new ArrayList<>();

        for (Long dept :depts){
            BaseDeptAuthority authority = new BaseDeptAuthority();
            authority.setCreateTime(new Date());
            authority.setDeptId(dept);
            authority.setUserId(user);
            authority.setCreatePersonal(operatorUser);
            authority.setStatus(StatusEnums.ONE.getDesc());
            authority.setIsDel(DelEnums.NOT_DEL.getValue());
            list.add(authority);
        }

        baseDeptAuthorityMapper.insertList(list);

        return "操作成功";
    }

    /**
     * 修改用户可访问部门信息
     * 
     * @param baseDeptAuthority 用户可访问部门信息
     * @return 结果
     */
    @Override
    public int updateBaseDeptAuthority(BaseDeptAuthority baseDeptAuthority) {
        baseDeptAuthority.setUpdateTime(DateUtils.getNowDate());
        return baseDeptAuthorityMapper.updateBaseDeptAuthority(baseDeptAuthority);
    }

    /**
     * 批量删除用户可访问部门信息
     * 
     * @param ids 需要删除的用户可访问部门信息主键
     * @return 结果
     */
    @Override
    public int deleteBaseDeptAuthorityByIds(Long[] ids,Long operator) {
        return baseDeptAuthorityMapper.deleteBaseDeptAuthorityByIds(ids,operator);
    }

    /**
     * 删除用户可访问部门信息信息
     * 
     * @param id 用户可访问部门信息主键
     * @return 结果
     */
    @Override
    public int deleteBaseDeptAuthorityById(Long id,Long operator) {
        return baseDeptAuthorityMapper.deleteBaseDeptAuthorityById(id,operator);
    }
}
