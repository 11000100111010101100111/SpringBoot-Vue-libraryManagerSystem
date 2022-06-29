package com.nhXJH.enums.enumStructure;

/**
 * Created by IntelliJ IDEA.
 * User: xjh
 * Date: 2022/2/19
 * Time: 15:45
 * 实现次接口的枚举只含有value属性
 **/
public interface ValueEnum<T> {
    /**
     * 获取枚举值
     * @return T 枚举值
     * */
    T getValue();
}
