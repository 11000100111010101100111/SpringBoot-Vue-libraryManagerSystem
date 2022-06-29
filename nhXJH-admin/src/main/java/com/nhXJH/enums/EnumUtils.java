package com.nhXJH.enums;

import com.nhXJH.enums.enumStructure.ValueDescEnum;
import com.nhXJH.enums.enumStructure.ValueEnum;
import org.apache.poi.ss.formula.functions.T;

/**
 * Created by IntelliJ IDEA.
 * User: xjh
 * Date: 2022/2/19
 * Time: 15:49
 **/
public class EnumUtils {
    /**
     * 判断是否包含value的枚举
     * */
    public static <T> boolean isExist(ValueEnum<T>[] valueEnum, T value){
        if (null == value){
            return Boolean.FALSE;
        }
        for (ValueEnum<T> enumItem : valueEnum){
            if (value.equals(enumItem.getValue())){
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }

    /**
     * 判断枚举值是否存在于指定枚举类中
     * @param eClass 枚举类
     * @param value 枚举值
     * @param <E> 枚举类类型
     * @param <V> 枚举值类型
     * @return boolean 是否存在
     * */
    @SuppressWarnings("unchecked")
    public static <E extends Enum<? extends ValueEnum<V>>,V> boolean isExit(Class<E> eClass,V value){
        for (Enum<? extends ValueEnum<V>> enumConstant : eClass.getEnumConstants()) {
            if ( ( ( ValueEnum<V> ) enumConstant).getValue().equals(value)){
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }

    /**
     * 根据枚举值获取枚举描述
     * @param enums 枚举列表
     * @param value 枚举值
     * @return <T> 枚举描述
     *
     * */
    public static <V>V getDescByValue(ValueDescEnum<T,V>[] enums, V value){
        if (null == value) {
            return null;
        }
        for (ValueDescEnum<T,V> anEnum : enums) {
            if (value.equals(anEnum.getValue())){
                return anEnum.getDesc();
            }
        }
        return null;
    }

    /**
     * 根据枚举描述获取枚举值
     * @param enums 枚举列表
     * @param desc 枚举描述
     * @return <T> 枚举值
     *
     * */
    public static <T,V>T getValueByDesc(ValueDescEnum<T,V>[] enums,V desc){
        if (null == desc) {
            return null;
        }
        for (ValueDescEnum<T,V> anEnum : enums) {
            if (desc.equals(anEnum.getDesc())){
                return anEnum.getValue();
            }
        }
        return null;
    }
    /**
     * 根据枚举值获取枚举对象
     * @param enums 枚举类型
     * @param desc 枚举值
     * @return 枚举对象
     *
     * */
    public static <E extends Enum<? extends ValueDescEnum<T,V>>,V> E getEnumByDesc(E[] enums, V desc){
        if (null == desc) {
            return null;
        }
        for (E e : enums) {
            if ( desc.equals(( ( ValueDescEnum<T,V> ) e).getDesc() )){
                return e;
            }
        }
        return null;
    }
    public static <E extends Enum<? extends ValueDescEnum<T,V> >, V> E getEnumByDesc(Class<E> eClass,V desc){
        return EnumUtils.getEnumByDesc(eClass.getEnumConstants(),desc);
    }
    /**
     * 根据枚举值获取枚举对象
     * @param enums 枚举列表
     * @param value 枚举值
     * @return  枚举对象
     *
     * */
    @SuppressWarnings("unchecked")
    public static <E extends Enum<? extends ValueEnum<V>>,V> E getEnumByValue(E[] enums, V value){
        if (null == value) {
            return null;
        }
        for (E e : enums) {
            if ( value.equals(( ( ValueEnum<V> ) e).getValue() )){
                return e;
            }
        }
        return null;
    }

    /**
     * 根据枚举值获取枚举对象
     * @param eClass 枚举类型
     * @param value 枚举值
     * @return 枚举对象
     *
     * */
    public static <E extends Enum<? extends ValueEnum<V>>,V> E getEnumByValue(Class<E> eClass,V value){
        return EnumUtils.getEnumByValue(eClass.getEnumConstants(),value);
    }

    public static FileTypeEnums getEnumByDesc(FileTypeEnums[] enumConstants, String type) {
        if (null == enumConstants) {
            return null;
        }
        for (FileTypeEnums e : enumConstants) {
            if ( type.equals(e.getDesc() )){
                return e;
            }
        }
        return null;
    }
}
