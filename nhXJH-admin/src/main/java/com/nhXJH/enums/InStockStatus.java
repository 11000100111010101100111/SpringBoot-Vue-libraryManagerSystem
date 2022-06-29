package com.nhXJH.enums;

import io.swagger.models.auth.In;
import lombok.Data;

/**
 * Created by IntelliJ IDEA.
 * User: xjh
 * Date: 2022/3/10
 * Time: 10:49
 **/
public enum InStockStatus {
    ERROR(0,"入库失败"),
    SUCCEED(1,"入库成功");
    String msg;
    Integer code;
    InStockStatus(Integer code,String msg ){
        this.code = code;
        this.msg = msg;
    }
    public Integer getStatus(){
        return this.code;
    }
    public String getMessage(){
        return this.msg;
    }
}
