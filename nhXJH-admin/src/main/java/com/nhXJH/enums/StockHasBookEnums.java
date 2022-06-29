package com.nhXJH.enums;

import com.nhXJH.enums.enumStructure.ValueDescEnum;
import lombok.AllArgsConstructor;

/**
 * Created by IntelliJ IDEA.
 * User: xjh
 * Date: 2022/2/24
 * Time: 16:31
 *
 **/
@AllArgsConstructor
public enum  StockHasBookEnums implements ValueDescEnum {
    HAS_BOOK(1,"1"),
    NO_BOOK_NOMAL(0,"0"),
    LOAN_BOOK(-1,"-1"),
    DISTORY(-2,"-2"),
        ;
    private Integer value;
    private String desc;
    @Override
    public String getDesc() {
        return this.desc;
    }

    @Override
    public Integer getValue() {
        return this.value;
    }
}
