package com.nhXJH.web.service.stageOfGetUsers;

import com.nhXJH.common.core.domain.entity.SysUser;
import com.nhXJH.common.exception.ServiceException;
import com.nhXJH.system.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: xjh
 * Date: 2022/3/4
 * Time: 10:39
 **/
@Service("byDept")
public class GetByDept implements IStageOfUser {
    @Autowired
    private SysUserMapper userMapper;
    @Override
    public List<SysUser> stage(QueryParam param) {
        if (null == param || null == param.getDeptId()){
            throw new ServiceException("没有要查询的部门条件");
        }
        SysUser query = new SysUser();
        query.setDeptId(param.getDeptId());
        return userMapper.selectUserList(query);
    }
}
