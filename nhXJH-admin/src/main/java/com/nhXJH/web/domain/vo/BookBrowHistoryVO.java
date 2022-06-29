package com.nhXJH.web.domain.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.nhXJH.common.annotation.Excel;
import com.nhXJH.common.core.domain.entity.SysUser;
import com.nhXJH.web.domain.BookStockBrowHistory;
import com.nhXJH.web.domain.LibraryBook;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by IntelliJ IDEA.
 * User: xjh
 * Date: 2022/3/14
 * Time: 20:38
 * 图书借阅历史返回结果
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookBrowHistoryVO extends BookStockBrowHistory{

    /** 图书名称 */
    @Excel(name = "图书名称")
    @ApiModelProperty("图书名称")
    private String bookName;

    /** 图书编码 */
    @Excel(name = "图书编码")
    @ApiModelProperty("图书编码")
    private String bookCode;

    /** 用户账号 */
    @Excel(name = "登录名称")
    private String userName;

    /** 用户昵称 */
    @Excel(name = "用户名称")
    private String userNickName;

    /** 用户邮箱 */
    @Excel(name = "用户邮箱")
    private String userEmail;

    /** 手机号码 */
    @Excel(name = "手机号码")
    private String userPhoneNumber;

    /** 用户性别 */
    @Excel(name = "用户性别", readConverterExp = "0=男,1=女,2=未知")
    private String userSex;

    /** 用户头像 */
    private String userAvatar;

    private String totalTime;
}
