package com.nhXJH.enums;

import com.nhXJH.enums.enumStructure.ValueDescEnum;
import io.swagger.models.auth.In;

/**
 * Created by IntelliJ IDEA.
 * User: xjh
 * Date: 2022/3/14
 * Time: 15:09
 **/
public enum HasBrowedEnums implements ValueDescEnum<Integer,String> {
    NOMAL(0,"0","借出，未归还"),
    BACKED(1,"1","已归还");

    private Integer code;
    private String desc;
    private String mark;
    HasBrowedEnums(Integer code,String desc,String mark){
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
