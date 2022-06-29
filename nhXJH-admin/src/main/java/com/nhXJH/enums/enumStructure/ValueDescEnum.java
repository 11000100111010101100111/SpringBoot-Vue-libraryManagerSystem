package com.nhXJH.enums.enumStructure;

import org.apache.poi.ss.formula.functions.T;

/**
 * Created by IntelliJ IDEA.
 * User: xjh
 * Date: 2022/2/19
 * Time: 15:48
 * 实现次接口的枚举含有value属性和desc属性
 **/
public interface ValueDescEnum<T,V> extends ValueEnum<T> {
    /**
     * 获取枚举值
     * @return T 枚举值
     * */
    V getDesc();
}
