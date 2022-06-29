package com.nhXJH.web.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by IntelliJ IDEA.
 * User: xjh
 * Date: 2022/3/14
 * Time: 10:05
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BrowBookCode {
    Long stockId;
    String code;
    Long userId;
}
