package com.nhXJH.enums;

import com.nhXJH.enums.enumStructure.ValueDescEnum;

/**
 * Created by IntelliJ IDEA.
 * User: xjh
 * Date: 2022/3/7
 * Time: 11:24
 * 是否已读，默认未读0，已读使用1表示
 **/
public enum QueueReadType implements ValueDescEnum<Integer,String> {
    HAS_READ(1,"1","已读"),
    NOT_READ(0,"0","未读"),
    WAIT_READ(-1,"-1","待读"),

    ;
    Integer value;
    String desc;
    String mark;
    QueueReadType(Integer value,String desc,String mark){
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
