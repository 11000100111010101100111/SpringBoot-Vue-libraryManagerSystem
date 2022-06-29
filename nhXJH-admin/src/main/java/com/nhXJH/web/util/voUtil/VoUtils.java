package com.nhXJH.web.util.voUtil;

import com.nhXJH.common.core.domain.entity.SysUser;
import com.nhXJH.web.domain.vo.PressVO;
import org.apache.poi.ss.formula.functions.T;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: xjh
 * Date: 2022/2/16
 * Time: 17:14
 **/
public class VoUtils {
//    public static List<T> changePOtoVO(List list,Class poClass,Class voClass){
//        Set<Long> idSet = new HashSet<>();
//        list.forEach(press->{
//            idSet.add(press.getCreatePersonal());
//            idSet.add(press.getUpdatePersonal());
//        });
//
//        Long[] ids = new Long[idSet.size()];
//        int index = 0;
//        idSet.forEach(set->{
//            ids[index] = set;
//        });
//
//        List<SysUser> users = userMapper.selectUserByIds(ids);
//        Map<Long,SysUser> userMap = new HashMap<>();
//        users.forEach(u->{
//            userMap.put(u.getUserId(),u);
//        });
//        List<PressVO> result = new ArrayList<>();
//        pressList.forEach(press -> {
//            result.add(
//                    new PressVO(
//                            press,
//                            userMap.get(press.getCreatePersonal()),
//                            userMap.get(press.getUpdatePersonal())
//                    )
//            );
//        });
//
//        return result;
//        return null;
//    }
}
