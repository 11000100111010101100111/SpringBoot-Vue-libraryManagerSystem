package com.nhXJH.web.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.nhXJH.common.annotation.Excel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.nhXJH.common.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.nhXJH.common.core.domain.BaseEntity;

/**
* 借书信息对象 book_stock-brow_history
*
* @author xjh
* @date 2022-03-14
*/
@TableName("book_stock_brow_history")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BookStockBrowHistory extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** id */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty("id")
    private Long id;

    /** 借书人id */
    @Excel(name = "借书人id")
    @ApiModelProperty("借书人id")
    private Long userId;

    /** 书id */
    @Excel(name = "书id")
    @ApiModelProperty("书id")
    private Long bookId;

    /** 图书库存id */
    @Excel(name = "图书库存id")
    @ApiModelProperty("图书库存id")
    private Long stockId;

    /** 归还时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "归还时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("归还时间")
    private Date backTime;

    /** 是否归还 */
    @Excel(name = "是否归还")
    @ApiModelProperty("是否归还")
    private String hasBack;

    /** 状态 */
    @Excel(name = "状态")
    @ApiModelProperty("状态")
    private String status;

    /** 是否删除 */
    @Excel(name = "是否删除")
    @ApiModelProperty("是否删除")
    private String isDel;

    /** 借书人 */
    @Excel(name = "借书人")
    @ApiModelProperty("借书人")
    private Long createPersonal;

    /** 更新人 */
    @Excel(name = "更新人")
    @ApiModelProperty("更新人")
    private Long updatePersonal;

}
