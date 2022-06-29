package com.nhXJH.system.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.nhXJH.common.annotation.Excel;
import com.nhXJH.common.core.domain.BaseEntity;
import com.nhXJH.common.core.domain.entity.SysDept;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 中国标准图书分类规则对象 base_book_class_cn
 * 
 * @author xjh
 * @date 2022-01-07
 */
@TableName("base_book_class_cn")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BaseBookClassCn extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 图书类别编号ID */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "UserIdentityGenerator")
//    @GenericGenerator(name = "UserIdentityGenerator", strategy = "com.demo.UserIdentityGenerator")
    private Long id;

    /** 图书类别编号 */
    @Excel(name = "类别编号")
    private String code;

    /** 图书类别名称 */
    @Excel(name = "类别名称")
    private String name;

    /** 父级类别ID */
    @Excel(name = "父级类别")
    private Long parentId;

    /** 图书类别级别 */
    @Excel(name = "图书类别级别")
    @TableField("class")
    private Integer clasz;

    /** 图书类别别名 */
    @Excel(name = "图书类别别名")
    private String aliName;

    /** 状态是否禁用（默认1，禁用0） */
    @Excel(name = "状态",readConverterExp="0=失效,1=有效")
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


    /** 子类别 */
    private List<BaseBookClassCn> children = new ArrayList<BaseBookClassCn>();
    public List<BaseBookClassCn> getChildren() {
        return children;
    }
    public void setChildren(List<BaseBookClassCn> children) {
        this.children = children;
    }
//    /** 删除时间 */
//    @JsonFormat(pattern = "yyyy-MM-dd")
//    @Excel(name = "删除时间", width = 30, dateFormat = "yyyy-MM-dd")
//    private Date delTime;
//
//    /** 删除人ID */
//    @Excel(name = "删除人ID")
//    private String delPersonal;


//    @Override
//    public String toString() {
//        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
//            .append("id", getId())
//            .append("code", getCode())
//            .append("name", getName())
//            .append("clasz", getClasz())
//            .append("aliName", getAliName())
//            .append("status", getStatus())
//            .append("createTime", getCreateTime())
//            .append("createPersonal", getCreatePersonal())
//            .append("updateTime", getUpdateTime())
//            .append("updatePersonal", getUpdatePersonal())
//            .append("delTime", getDelTime())
//            .append("delPersonal", getDelPersonal())
//            .toString();
//    }
}
