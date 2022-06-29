package com.nhXJH.web.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.nhXJH.common.core.domain.entity.SysDept;
import com.nhXJH.system.domain.po.BaseBookClassCn;
import com.nhXJH.web.domain.BaseFile;
import com.nhXJH.web.domain.LibraryAuth;
import com.nhXJH.web.domain.LibraryBook;
import com.nhXJH.web.domain.LibraryPress;
import com.nhXJH.web.domain.entity.SysFile;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: xjh
 * Date: 2022/3/10
 * Time: 20:12
 * 图书搜索返回结果集
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookVO {
    private Long bookId;
    private String bookName;
    private String bookCode;
    private String bookType;
    private String bookLeavel;
    private Long bookClaz;
    private Long bookAuthor;
    private Long bookMechanism;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date bookPublicationData;
    private String bookVersion;
    private String bookCirculation;
    private String bookISBN;
    private String bookSeries;
    private String bookKeyWords;
    private String bookPrice;
    private String bookMark;
    private String authName;
    private String authCountry;
    private String authNickName;
    private String authEnglishName;
    private String authSynopsis;
    private String authIdentity;
    private String clazCode;
    private String clazName;
    private String pressName;
    private String pressAddr;
    private String pressMark;
    private String pressEmail;
    private String pressPhone;
    private String deptName;
    private String deptLeader;
    private String deptPhone;
    private String deptEmail;
    private Long deptId;
    @ApiModelProperty("图书在库当前容量")
    private Integer nowNumber;
    @ApiModelProperty("图书在库总量容量")
    private Integer saveNumber;
    private List<SysDept> parentDept;
    private List<BaseFile> fileList;
    private List<String> srcList;
}
