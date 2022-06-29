package com.nhXJH.web.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.nhXJH.common.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import io.swagger.annotations.ApiModelProperty;
import com.nhXJH.common.core.domain.BaseEntity;

import java.util.Date;

/**
* 附件实体对应信息对象 base_file
*
* @author xjh
* @date 2022-01-25
*/
@TableName("base_file")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BaseFile extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 主键 */
    @ApiModelProperty("id")
    @TableId
    private Long fileId;

    /** 附件模块id */
//    @Excel(name = "附件模块id")
    @ApiModelProperty("附件模块id")
    private Long modelId;

    /** 附件所属表的表代码 */
//    @Excel(name = "附件所属表的表代码")
    @ApiModelProperty("附件所属表的表代码")
    private Long dbId;

    /** 附件路径 */
    @Excel(name = "附件路径")
    @ApiModelProperty("附件路径")
    private String url;

    /** 附件类型 取值参照FileTypeEnums */
    @Excel(name = "附件类型",readConverterExp = "1=图片,2=文档,3=pdf,4=视频,99=其他文件")
    @ApiModelProperty("附件类型")
    private Integer type;

    /** 附件说明 */
    @Excel(name = "附件说明")
    @ApiModelProperty("附件说明")
    private String mark;

    /** 状态，默认1，删除用0表示 */
    @Excel(name = "状态",readConverterExp="0=失效,1=有效")
    @ApiModelProperty("状态，默认1，删除用0表示")
    private String status;

    /** 创建人id */
//    @Excel(name = "创建人id")
    @ApiModelProperty("创建人id")
    private Long createPerson;

    /** 更新人id */
//    @Excel(name = "更新人id")
    @ApiModelProperty("更新人id")
    private Long updatePerson;
}
