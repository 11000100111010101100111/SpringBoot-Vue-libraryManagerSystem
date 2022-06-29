package com.nhXJH.web.domain.param.purchase;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by IntelliJ IDEA.
 * User: xjh
 * Date: 2022/3/6
 * Time: 15:33
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NextNode {
    String label;//执行条件
    PurchaseNode node;//下一结点信息
    Boolean isEnd;
}
