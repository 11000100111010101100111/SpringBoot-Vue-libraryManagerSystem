package com.nhXJH.common.enums.application;

import java.util.Arrays;

/**
 * Created by IntelliJ IDEA.
 * User: xjh
 * Date: 2022/1/24
 * Time: 15:31
 **/
public enum TableTypeEnums {
    PURCHASE(1l,"base_purchase_requisition", "采购申请信息"),
    AUTH(26l, "library_auth","作者信息表"),
    MECHANISM(4l,"library_mechanism","图书管理机构信息"),
    PRESS(5l,"library_press","出版社信息"),
    BOOK(25l,"library_book","图书信息表"),
    ;

    /**
     * 编码
     */
    private Long code;
    /**
    * 表名称
    * */
    private String tableName;
    /**
     * 描述
     */
    private String desc;


    TableTypeEnums(Long code,String name, String desc) {
        this.code = code;
        this.desc = desc;
        this.tableName = name;
    }

    public static TableTypeEnums getByCode(Long code) {
        for (TableTypeEnums element : TableTypeEnums.values()) {
            if (element.code.equals(code)) {
                return element;
            }
        }
        return null;
    }
    public static TableTypeEnums getByName(Long code) {
        for (TableTypeEnums element : TableTypeEnums.values()) {
            if (element.code.equals(code)) {
                return element;
            }
        }
        return null;
    }
    public static String getNameByCode(Long code) {
        TableTypeEnums value = Arrays.stream(TableTypeEnums.values())
                .filter(status -> status.getCode().equals(code))
                .findFirst().orElse(null);
        if (value != null) {
            return value.getTableName();
        }
        return "";
    }
    public static Long getCodeByName(String name) {
        TableTypeEnums value = Arrays.stream(TableTypeEnums.values())
                .filter(status -> status.getTableName().equals(name))
                .findFirst().orElse(null);
        if (value != null) {
            return value.getCode();
        }
        return -1l;
    }


    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}
