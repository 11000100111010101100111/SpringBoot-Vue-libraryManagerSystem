package com.nhXJH.enums;

import com.nhXJH.enums.enumStructure.ValueDescEnum;
import lombok.AllArgsConstructor;

/**
 * Created by IntelliJ IDEA.
 * User: xjh
 * Date: 2022/1/17
 * Time: 16:12
 * 枚举：状态类型
 **/
@AllArgsConstructor
public enum StatusEnums implements ValueDescEnum {
    NEG_ONE(-1, "-1"),
    ONE(1, "1"),
    ZERO(0, "0"),
    TWO(2, "2"),
    ;

    /*** 编码 ***/
    private Integer value;
    /*** 描述 ***/
    private String desc;

    @Override
    public String getDesc() {
        return desc;
    }
    @Override
    public Integer getValue() {
        return this.value;
    }
}
