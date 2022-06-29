package com.nhXJH.common.enums.application;

import java.util.Arrays;

/**
 * Created by IntelliJ IDEA.
 * User: xjh
 * Date: 2022/1/17
 * Time: 16:12
 **/
public enum StatusEnums {
    NEG_ONE(-1, "-1"),
    ONE(1, "1"),
    ZERO(0, "0"),
    TWO(2, "2"),
    ;

    /**
     * 编码
     */
    private Integer code;
    /**
     * 描述
     */
    private String desc;


    StatusEnums(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static StatusEnums getCode(Integer code) {
        for (StatusEnums element : StatusEnums.values()) {
            if (element.code.equals(code)) {
                return element;
            }
        }
        return null;
    }

    public static String getDescByCode(Integer code) {
        StatusEnums value = Arrays.stream(StatusEnums.values())
                .filter(status -> status.getCode().equals(code))
                .findFirst().orElse(null);
        if (value != null) {
            return value.getDesc();
        }
        return "";
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
