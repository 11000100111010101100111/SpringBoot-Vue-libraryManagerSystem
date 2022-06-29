package com.nhXJH.web.util.collections;

import org.springframework.stereotype.Component;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: xjh
 * Date: 2022/2/22
 * Time: 10:36
 * 集合处理工具包
 **/
@Component("operatorCollection")
public class OperatorCollection {
    public Long[] toArray(Set<Long> collection){
        if (null == collection || collection.size()<=0){
            return null;
        }
        Long[] arr= new Long[collection.size()];
        Iterator<Long> iterator = collection.iterator();
        int index = 0;
        while (iterator.hasNext()){
            arr[index] = iterator.next();
            index++;
        }
        return arr;
    }
}
