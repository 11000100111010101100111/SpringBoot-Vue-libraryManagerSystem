package com.nhXJH.enums;

import com.nhXJH.enums.enumStructure.ValueDescEnum;

/**
 * Created by IntelliJ IDEA.
 * User: xjh
 * Date: 2022/2/26
 * Time: 18:02
 * 图书的保存、公开级别
 **/
public enum BookLeaveEnums implements ValueDescEnum {
    ;
    private Integer value;
    private String desc;
    private String mark;
    BookLeaveEnums(Integer value,String desc,String mark){
        this.value = value;
        this.desc = desc;
        this.mark = mark;
    }
    @Override
    public String getDesc() {
        return this.desc;
    }

    @Override
    public Integer getValue() {
        return this.value;
    }
}
