package com.nhXJH.web.domain.param.purchase;

import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: xjh
 * Date: 2022/3/5
 * Time: 13:45
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TemplateParam {
    String name;
    List<PurchaseNode> nodeList;
    List<PurchaserLine> lineList;
//    public String getJSON(){
//        return JSON.toJSONString(this);
//    }
}
