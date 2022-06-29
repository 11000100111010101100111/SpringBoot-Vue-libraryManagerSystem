package com.nhXJH.web.domain;

import com.nhXJH.common.annotation.Excel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import io.swagger.annotations.ApiModelProperty;
import com.nhXJH.common.core.domain.BaseEntity;

/**
* 表信息映射对象 base_db_map
*
* @author xjh
* @date 2022-01-25
*/
@TableName("base_db_map")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DbMap extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 表映射信息表 */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty("id")
    private Long id;

    /** 表名 */
    @Excel(name = "表名")
    @ApiModelProperty("表名")
    private String name;

    /** 表代码 */
    @Excel(name = "表代码")
    @ApiModelProperty("表代码")
    private String code;

    /** 表说明 */
    @Excel(name = "表说明")
    @ApiModelProperty("表说明")
    private String mark;

    /** 创建人 */
    @Excel(name = "创建人")
    @ApiModelProperty("创建人")
    private Long createPerson;

    /** 更新人 */
    @Excel(name = "更新人")
    @ApiModelProperty("更新人")
    private Long updatePerson;



}
