package com.nhXJH.enums;

import com.nhXJH.enums.enumStructure.ValueDescEnum;
import io.swagger.models.auth.In;

/**
 * Created by IntelliJ IDEA.
 * User: xjh
 * Date: 2022/3/2
 * Time: 22:41
 **/
public enum PurchaseNodeStatusEnums implements ValueDescEnum {
    NOT_SUBMIT(0,"0","暂未提交"),
    HAS_SUBMIT(1,"1","提交申请"),
    HAS_AGREE(2,"2","已通过"),
    HAS_DO(3,"3","处理完成"),
    HAS_REFUSE(-1,"-1","已拒绝"),
    HAS_OVERDUE(-2,"-2","已过期"),

    ;
    Integer value;
    String desc;
    String mark;
    PurchaseNodeStatusEnums(Integer value,String desc,String mark){
        this.value = value;
        this.desc = desc;
        this.mark = mark;
    }
    @Override
    public String getDesc() {
        return this.desc;
    }
    public String getMark() {
        return this.mark;
    }

    @Override
    public Integer getValue() {
        return this.value;
    }
}
