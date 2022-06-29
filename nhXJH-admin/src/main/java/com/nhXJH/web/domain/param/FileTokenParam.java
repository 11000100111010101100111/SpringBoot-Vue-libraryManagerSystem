package com.nhXJH.web.domain.param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by IntelliJ IDEA.
 * User: xjh
 * Date: 2022/1/18
 * Time: 10:53
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileTokenParam {
    String account;
    String key;
    String pwd;
}
