package com.nhXJH.enums;

import com.nhXJH.enums.enumStructure.ValueDescEnum;

/**
 * Created by IntelliJ IDEA.
 * User: xjh
 * Date: 2022/2/26
 * Time: 16:50
 * 图书库存变更类型，更改同时需要：更改BookStockLog.java 的等值替换
 **/
public enum BookStockOperatorType implements ValueDescEnum {
    INLINE(1,"1","线下"),
    ONLINE(2,"2","线上"),
    BY_AGENCY(3,"3","代办"),
    OTHER(0,"0","其他方式");
    private Integer value;
    private String desc;
    private String mark;
    BookStockOperatorType(Integer value,String desc,String mark){
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
