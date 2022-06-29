package com.nhXJH.enums;

import com.nhXJH.enums.enumStructure.ValueDescEnum;

/**
 * Created by IntelliJ IDEA.
 * User: xjh
 * Date: 2022/3/7
 * Time: 11:26
 * 处理结果，未处理0表示，已处理1表示
 **/
public enum QueueResult implements ValueDescEnum<Integer,String> {
    TIMER(0,"0","未处理"),
    ING(1,"1","已处理"),
    REFUSE(2,"2","拒绝"),
    ;
    Integer value;
    String desc;
    String mark;
    QueueResult(Integer value,String desc,String mark){
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
