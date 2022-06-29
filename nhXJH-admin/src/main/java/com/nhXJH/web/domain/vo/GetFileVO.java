package com.nhXJH.web.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by IntelliJ IDEA.
 * User: xjh
 * Date: 2022/2/18
 * Time: 16:11
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetFileVO {
    Long fileId;
    Long modelId;
    Long dbId;
    String name;
    String newName;
    Long groupId;
    String url;
    String mark;
}
