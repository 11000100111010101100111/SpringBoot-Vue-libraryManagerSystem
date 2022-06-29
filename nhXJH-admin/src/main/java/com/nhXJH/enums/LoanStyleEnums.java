package com.nhXJH.enums;

import com.nhXJH.enums.enumStructure.ValueDescEnum;
import com.sun.org.apache.bcel.internal.generic.RETURN;

/**
 * Created by IntelliJ IDEA.
 * User: xjh
 * Date: 2022/2/26
 * Time: 16:40
 * 图书出入库类型
 **/
public enum LoanStyleEnums implements ValueDescEnum {
    BORROW_BOOK(-1,"-1","借书"),
    RETURN_BOOK(1,"1","还书"),
    WAREHOUSING(2,"2","入库"),
    EXIT_WAREHOUSE(-2,"-2","出库"),
    DEFAULT(0,"0","默认初始入库");
    private Integer value;
    private String desc;
    private String mark;
    LoanStyleEnums(Integer value,String desc,String mark){
        this.value = value;
        this.desc = desc;
        this.mark = mark;
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
