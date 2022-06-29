package com.nhXJH.web.domain.param.purchase;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: xjh
 * Date: 2022/3/6
 * Time: 14:40
 **/
public class NodeList {
    String id;
    String status;
    List<String> statusOptions;//
    List<NodeList> next;//子节点（分支流程可多个）
    NodeList pre;//父结点
    PurchaseNode value;
}
