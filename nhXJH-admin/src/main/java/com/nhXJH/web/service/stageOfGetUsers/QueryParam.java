package com.nhXJH.web.service.stageOfGetUsers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by IntelliJ IDEA.
 * User: xjh
 * Date: 2022/3/4
 * Time: 10:38
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryParam {
    Long deptId;
    Long roleId;
    Long postId;
}
