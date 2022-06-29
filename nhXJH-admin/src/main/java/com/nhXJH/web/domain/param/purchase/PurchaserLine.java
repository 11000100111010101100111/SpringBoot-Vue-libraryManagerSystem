package com.nhXJH.web.domain.param.purchase;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: xjh
 * Date: 2022/3/5
 * Time: 13:58
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaserLine {
    String to;
    String from;
    String label;
    String connector;
    List<String> anchors;
    String audioType;
    PurchaserPaintStyle paintStyle;
}
