package com.nhXJH.enums;

import com.nhXJH.enums.enumStructure.ValueDescEnum;

/**
 * Created by IntelliJ IDEA.
 * User: xjh
 * Date: 2022/2/26
 * Time: 14:34
 **/
public enum DelEnums implements ValueDescEnum {
    IS_DEL("1","已删除"),
    NOT_DEL("0","未删除");
    private String value;
    private String desc;
    DelEnums(String value,String desc){
        this.desc = desc;
        this.value = value;
    }
    @Override
    public String getDesc() {
        return this.desc;
    }

    @Override
    public String getValue() {
        return this.value;
    }
}
