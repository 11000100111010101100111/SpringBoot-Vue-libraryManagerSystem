package com.nhXJH.enums;

import com.nhXJH.enums.enumStructure.ValueDescEnum;
import com.nhXJH.enums.enumStructure.ValueEnum;

/**
 * Created by IntelliJ IDEA.
 * User: xjh
 * Date: 2022/2/24
 * Time: 22:13
 * 附件类型,更改类型需同步BaseFile.java的导出等值替换
 **/
public enum FileTypeEnums implements ValueDescEnum<Integer,String> {

    PICTURE(1,"图片","picture"),

    DOC(2,"doc|docx文档","doc"),

    PDF(3,"pdf","pdf"),

    VIDEO(4,"视频，mp4...","video"),

    OTHER(99,"其他文件信息","other"),
    ;

    private Integer value;
    private String mark;
    private String desc;
    FileTypeEnums(Integer value, String mark,String desc){
        this.value = value;
        this.desc = desc;
        this.mark = mark;
    }

    public String getMark(){
        return this.mark;
    }
    @Override
    public String getDesc() {
        return this.desc;
    }

    @Override
    public Integer getValue() {
        return this.value;
    }
}
