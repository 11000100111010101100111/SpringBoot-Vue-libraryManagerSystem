package com.nhXJH.enums;

import com.nhXJH.enums.enumStructure.ValueDescEnum;

/**
 * Created by IntelliJ IDEA.
 * User: xjh
 * Date: 2022/3/6
 * Time: 16:19
 * 流程结点信息状态 类型
 **/
public enum LinkNodeType implements ValueDescEnum<Integer,String> {
    TIMER(1,"start","开始结点"),
    ING(2,"timer","过程结点"),
    OVER(3,"over","完成"),
    END(4,"end","流程结束"),

    ;
    Integer value;
    String desc;
    String mark;
    LinkNodeType(Integer value,String desc,String mark){
        this.value = value;
        this.desc = desc;
        this.mark = mark;
    }
    @Override
    public String getDesc() {
        return this.desc;
    }
    public String getMark() {
        return this.mark;
    }

    @Override
    public Integer getValue() {
        return this.value;
    }
}
