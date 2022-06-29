package com.nhXJH.web.domain.param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.checkerframework.checker.units.qual.A;

/**
 * Created by IntelliJ IDEA.
 * User: xjh
 * Date: 2022/2/18
 * Time: 16:09
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetFileParam {
    Long fileId;
    Long modelId;
    Long dbId;
}
