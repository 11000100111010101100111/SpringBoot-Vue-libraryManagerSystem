package com.nhXJH.web.domain.vo;

import com.nhXJH.system.domain.po.BaseBookClassCn;
import com.nhXJH.web.domain.LibraryAuth;
import com.nhXJH.web.domain.LibraryBook;
import com.nhXJH.web.domain.LibraryPress;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: xjh
 * Date: 2022/2/26
 * Time: 17:47
 * 图书详细信息
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LibraryBookDetailVO {
    @ApiModelProperty("图书实体信息")
    LibraryBook book;
    @ApiModelProperty("作者实体信息")
    LibraryAuth auth;
    @ApiModelProperty("出版社实体信息")
    LibraryPress press;
    @ApiModelProperty("图书类别")
    BaseBookClassCn bookType;
    @ApiModelProperty("图书文件信息")
    BaseFileClassify bookFile;
    @ApiModelProperty("作者相关文件")
    BaseFileClassify authFile;
    @ApiModelProperty("图书图片路径列表")
    List<String> bookFileSrc;
    @ApiModelProperty("作者图片路径列表")
    List<String> authFileSrc;
}
