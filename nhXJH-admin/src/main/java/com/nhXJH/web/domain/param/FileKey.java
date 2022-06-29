package com.nhXJH.web.domain.param;

import io.swagger.annotations.ApiModelProperty;

/**
 * Created by IntelliJ IDEA.
 * User: xjh
 * Date: 2022/1/21
 * Time: 9:53
 **/
public class FileKey {
    /**
     * 服务账号
     */
    @ApiModelProperty(name = "服务账号",required = true)
    private String account;
    /**
     * 服务秘钥
     */
    @ApiModelProperty(name = "服务秘钥",required = true)
    private String key;
}
