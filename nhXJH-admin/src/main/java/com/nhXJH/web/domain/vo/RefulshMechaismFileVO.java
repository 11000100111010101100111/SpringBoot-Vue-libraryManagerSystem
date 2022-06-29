package com.nhXJH.web.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by IntelliJ IDEA.
 * User: xjh
 * Date: 2022/2/20
 * Time: 20:41
 * 图书管理机构文件变更结果
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RefulshMechaismFileVO {
    @ApiModelProperty("本次提交多少条")
    int submitNum;
    @ApiModelProperty("本次删除多少条")
    int delNum;
    @ApiModelProperty("本次新增多少条")
    int insertNum;
    @ApiModelProperty("之前存在多少条")
    int agoNum;
    @ApiModelProperty("现在共有多少条")
    int nowNum;
}
