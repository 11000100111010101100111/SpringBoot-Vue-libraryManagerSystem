package com.nhXJH.web.service.stageOfGetUsers;

/**
 * Created by IntelliJ IDEA.
 * User: xjh
 * Date: 2022/3/4
 * Time: 10:41
 **/
public enum GetUserType {
    GET_BY_DEPT("byDept","根据部门获取用户列表"),
    GET_BY_ROLE("byRole","根据职称获取用户列表"),
    CET_BY_POST("byPost","根据岗位获取用户列表");
    private String name;
    private String mark;
    GetUserType(String name,String mark){
        this.mark = mark;
        this.name = name;
    }
}
