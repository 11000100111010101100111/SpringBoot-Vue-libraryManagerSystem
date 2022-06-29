package com.nhXJH.system.domain.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.nhXJH.common.annotation.Excel;
import com.nhXJH.common.enums.BookClaszEnum;
import com.nhXJH.system.domain.po.BaseBookClassCn;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: xjh
 * Date: 2022/1/30
 * Time: 15:20
 **/
@Data
public class BookClassVO{
    /** 图书类别编号ID */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "UserIdentityGenerator")
//    @GenericGenerator(name = "UserIdentityGenerator", strategy = "com.demo.UserIdentityGenerator")
    private Long id;

    /** 图书类别编号 */
    @Excel(name = "图书类别编号")
    private String code;

    /** 图书类别名称 */
    @Excel(name = "图书类别名称")
    private String name;

    /** 父级类别ID */
    @Excel(name = "父级类别ID")
    private Long parentId;

    /** 图书类别级别 */
    @Excel(name = "图书类别级别")
    @TableField("class")
    private Integer clasz;

    /** 图书类别别名 */
    @Excel(name = "图书类别别名")
    private String aliName;

    /** 状态是否禁用（默认1，禁用0） */
    @Excel(name = "状态是否禁用", readConverterExp = "默=认1，禁用0")
    private String status;

    /** 创建人id */
    @Excel(name = "创建人id")
    private Long createPersonal;
    /**
     * 状态
     */
    @Excel(name = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;


    /** 更新人id */
    @Excel(name = "更新人id")
    private Long updatePersonal;
    /**
     * 状态
     */

    @Excel(name = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;


    String claszName;

    public BookClassVO() {
        super();
    }
    public BookClassVO(BaseBookClassCn cn) {
        this.setId(cn.getId());
        this.setCode(cn.getCode());
        this.setName(cn.getName());
        this.setParentId(cn.getParentId());
        this.setClasz(cn.getClasz());
        this.setAliName(cn.getAliName());
        this.setStatus(cn.getStatus());
        this.setCreatePersonal(cn.getCreatePersonal());
        this.setCreateTime(cn.getCreateTime());
        this.setUpdatePersonal(cn.getUpdatePersonal());
        this.setUpdateTime(cn.getUpdateTime());
        claszName = BookClaszEnum.getDescByCode(cn.getClasz());
    }
}
