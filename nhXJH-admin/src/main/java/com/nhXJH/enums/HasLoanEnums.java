package com.nhXJH.enums;

import com.nhXJH.enums.enumStructure.ValueDescEnum;
import io.swagger.models.auth.In;

/**
 * Created by IntelliJ IDEA.
 * User: xjh
 * Date: 2022/3/14
 * Time: 11:12
 **/
public enum HasLoanEnums implements ValueDescEnum<Integer,String> {
    LOAN(1,"1","已经借出"),
    NORMAL(0,"0","正常在库，未借出"),
    DESTORY(-1,"-1","库存销毁");

    Integer code;
    String desc;
    String mark;
    HasLoanEnums(Integer code,String desc,String mark){
        this.code = code;
        this.desc = desc;
        this.mark = mark;
    }
    @Override
    public String getDesc() {
        return this.desc;
    }

    @Override
    public Integer getValue() {
        return this.code;
    }
}
