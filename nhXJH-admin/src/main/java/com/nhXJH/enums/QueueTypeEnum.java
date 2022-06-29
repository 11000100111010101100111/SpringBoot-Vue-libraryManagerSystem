package com.nhXJH.enums;

import com.nhXJH.enums.enumStructure.ValueDescEnum;

/**
 * Created by IntelliJ IDEA.
 * User: xjh
 * Date: 2022/3/7
 * Time: 11:19
 * 处理类型，1表示采购申请，2表示图书销毁，3表示图书封存，4表示图书公开申请，等其他类型其他表示
 **/
public enum  QueueTypeEnum implements ValueDescEnum<Integer,String> {
    PURCHASE(1,"1","采购申请"),
    DESTROY(2,"2","图书销毁"),
    NOT_SHOW(3,"3","图书封存"),
    SHOW(4,"4","图书公开申请"),
    OTHER(5,"5","其他类型"),
    ;
    Integer value;
    String desc;
    String mark;
    QueueTypeEnum(Integer value,String desc,String mark){
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
