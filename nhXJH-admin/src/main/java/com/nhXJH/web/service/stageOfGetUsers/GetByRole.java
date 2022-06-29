package com.nhXJH.web.service.stageOfGetUsers;

import com.nhXJH.common.core.domain.entity.SysUser;
import com.nhXJH.common.exception.ServiceException;
import com.nhXJH.system.domain.SysUserRole;
import com.nhXJH.system.mapper.SysUserMapper;
import com.nhXJH.system.mapper.SysUserRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: xjh
 * Date: 2022/3/4
 * Time: 10:39
 **/
@Service("byRole")
public class GetByRole implements IStageOfUser {
    @Autowired
    private SysUserMapper userMapper;
    @Autowired
    private SysUserRoleMapper roleMapper;
    @Override
    public List<SysUser> stage(QueryParam param) {
        if (null == param || null == param.getRoleId()){
            throw new ServiceException("没有要查询的职称条件");
        }
        SysUserRole query = new SysUserRole();
        query.setRoleId(param.getRoleId());
        List<SysUserRole> userId = roleMapper.getList(query);

        if(null == userId){
            return new ArrayList<>();
        }

        Long[] ids = new Long[userId.size()];
        for (int i = 0; i < userId.size(); i++) {
            ids[i] = userId.get(i).getUserId();
        }
        return userMapper.selectUserByIds(ids);
    }
}
