package com.nhXJH.web.domain.param.purchase;

import com.nhXJH.common.core.domain.entity.SysUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
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
public class PurchaseNode {
    String id;
    String ico;
    String top;
    String left;
    String name;
    String type;
    String mark;
    String state;
    Boolean viewOnly;
    List<String> stakeholder;
    List<String> stakeholderName;
    List<SysUser> user;
    List<Long> userId;
//    public List<Long> getUserId(){
//        List<Long> ids = new ArrayList<>();
//        if (null != this.userId ){
//            for (String s : userId) {
//                ids.add(Long.parseLong(s.replaceAll("\"","")));
//            }
//        }
//        return ids;
//    }
}
