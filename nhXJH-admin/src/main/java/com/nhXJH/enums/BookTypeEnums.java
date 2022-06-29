package com.nhXJH.enums;

import com.nhXJH.enums.enumStructure.ValueDescEnum;
import io.swagger.models.auth.In;

/**
 * Created by IntelliJ IDEA.
 * User: xjh
 * Date: 2022/2/26
 * Time: 17:59
 * 图书、资料等，book实体类型
 **/
public enum BookTypeEnums implements ValueDescEnum {
    BOOK(1,"1","图书"),
    DATA(2,"2","资料");
    private Integer value;
    private String desc;
    private String mark;
    BookTypeEnums(Integer v,String d,String m){
        this.value = v;
        this.desc = d;
        this.mark = m;
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
