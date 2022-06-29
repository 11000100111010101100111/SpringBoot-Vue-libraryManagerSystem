package com.nhXJH.web.domain.po;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: xjh
 * Date: 2022/2/13
 * Time: 18:27xx
 * 審批流程結點
 **/
@Data
public class TemplateNode {
    @ApiModelProperty("待处理人ID列表")
    private List<Long> idList;//待处理人ID

    @ApiModelProperty("处理人ID")
    private Long uid = -1l;//处理人id

    @ApiModelProperty("处理说明")
    private String mark;//处理备注

    @ApiModelProperty("处理时间")
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date handelTime;//处理时间

    @ApiModelProperty("处理结果，-1拒绝，0默认未处理，1通过")
    private int status = 0;//处理结果，-1拒绝，0默认未处理，1通过

    private List<TemplateNode> next;//下一个处理结点

    public String toString(){
        return JSON.toJSONString(this);
    }
}
