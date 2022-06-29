package com.nhXJH.web.domain;

import com.nhXJH.common.annotation.Excels;
import com.nhXJH.common.core.domain.entity.SysDept;
import com.nhXJH.common.core.domain.entity.SysUser;
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
* 用户可访问部门信息对象 base_dept_authority
*
* @author xjh
* @date 2022-02-27
*/
@TableName("base_dept_authority")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BaseDeptAuthority extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 主键 */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty("id")
    private Long id;

    /** 部门id */
//    @Excel(name = "部门id")
    @ApiModelProperty("部门id")
    private Long deptId;

    @Excels({
            @Excel(name = "可访问部门",targetAttr = "deptName",type = Excel.Type.EXPORT)
    })
    private SysDept dept;

    /** 用户id */
//    @Excel(name = "用户id")
    @ApiModelProperty("用户id")
    private Long userId;

    @Excels({
            @Excel(name = "用户名",targetAttr = "userName",type = Excel.Type.EXPORT)
    })
    private SysUser sysUser;

    @Excel(name = "状态",readConverterExp="0=失效,1=有效")
    private String status;

    public boolean equalsUser(Long uid){
        return this.userId.equals(uid);
    }



}
