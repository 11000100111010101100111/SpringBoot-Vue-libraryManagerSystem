package com.nhXJH.web.domain.param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by IntelliJ IDEA.
 * User: xjh
 * Date: 2022/2/20
 * Time: 20:01
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RefulshMechaismFileParam {
    private String name;
    private String url;
    private Long fileId;
    private Long modelId;
    private Boolean idDel;
    private Boolean isNew;
    private String mark;
}
