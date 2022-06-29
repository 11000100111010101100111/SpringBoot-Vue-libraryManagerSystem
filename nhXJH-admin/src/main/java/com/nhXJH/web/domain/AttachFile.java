package com.nhXJH.web.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.nhXJH.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.ibatis.type.Alias;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.Date;

/**
 * (SysFile)实体类
 *
 * @author xjh
 * @since 2022-01-17 14:19:54
 */
@TableName("sys_file")
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
public class AttachFile extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 254675489532877337L;
    /**
     * 文件id
     */
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    @Id
    private Long id;
    /**
     * 文件名称
     */
    private String name;
    /**
     * 新文件名称
     */
    private String newName;
    /**
     * 文件组id
     */
    private Long groudId;
    /**
     * 文件路径
     */
    private String url;
    /**
     * 文件类型
     */
    private String type;
//    /**
//     * 是否被删除，默认0，删除1
//     */
//    private Integer isDel;
    /**
     * 说明文字
     */
    private String mark;
    /**
     * 是否有效，默认1有效，无效为0
     */
    private Integer status;
    /**
     * 文件上传人id
     */
    private Long createPerson;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 删除时间
     */
    private Date delTime;
    /**
     * 删除人id
     */
    private Long delPerson;
}

