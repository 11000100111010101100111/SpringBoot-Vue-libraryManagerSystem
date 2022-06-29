package com.nhXJH.common.enums;

import java.util.Arrays;

/**
 * Created by IntelliJ IDEA.
 * User: xjh
 * Date: 2022/2/8
 * Time: 21:00
 * 图书类型，更改类型需要在：libraryBook.java中修改导出的等值换算
 **/
public enum BookTypeEnum {
    CLASS_ONE(1,"图书"),
    CLASS_TWO(0,"资料");
    /**
     * 编码
     */
    private Integer code;
    /**
     * 描述
     */
    private String desc;


    BookTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static BookTypeEnum getCode(Integer code) {
        for (BookTypeEnum element : BookTypeEnum.values()) {
            if (element.code.equals(code)) {
                return element;
            }
        }
        return null;
    }

    public static String getDescByCode(Integer code) {
        BookTypeEnum value = Arrays.stream(BookTypeEnum.values())
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
