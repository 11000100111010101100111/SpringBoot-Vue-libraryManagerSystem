package com.nhXJH.web.domain.param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: xjh
 * Date: 2022/2/27
 * Time: 15:46
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeptAuthorityParam {
    Long userId;
    List<Long> deptIds;
    String status;
    Long operatorUser;
}
