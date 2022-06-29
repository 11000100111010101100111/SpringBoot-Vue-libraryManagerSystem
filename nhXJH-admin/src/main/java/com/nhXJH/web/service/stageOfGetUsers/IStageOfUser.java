package com.nhXJH.web.service.stageOfGetUsers;

import com.nhXJH.common.core.domain.entity.SysUser;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: xjh
 * Date: 2022/3/4
 * Time: 10:37
 **/
public interface IStageOfUser {
    public List<SysUser> stage(QueryParam param);
}
