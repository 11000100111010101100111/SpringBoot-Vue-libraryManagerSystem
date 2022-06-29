package com.nhXJH.common.enums;


import java.util.Arrays;

/**
 * Created by IntelliJ IDEA.
 * User: xjh
 * Date: 2022/1/30
 * Time: 15:22
 * 保密公开级别，更改级别需要在：Library.class中修改导出数据的等值转换，BaseBookClassCn.class中修改等值换算
 **/
public enum BookClaszEnum {
    CLASS_ONE(0,"一级类别"),
    CLASS_TWO(1,"二级类别");
    /**
     * 编码
     */
    private Integer code;
    /**
     * 描述
     */
    private String desc;


    BookClaszEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static BookClaszEnum getCode(Integer code) {
        for (BookClaszEnum element : BookClaszEnum.values()) {
            if (element.code.equals(code)) {
                return element;
            }
        }
        return null;
    }

    public static String getDescByCode(Integer code) {
        BookClaszEnum value = Arrays.stream(BookClaszEnum.values())
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
