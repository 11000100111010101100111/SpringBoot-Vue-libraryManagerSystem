package com.nhXJH.common.core.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.nhXJH.common.config.SnowFlakeUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity基类
 * 
 * @author nhXJH
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 搜索值 */
    private String searchValue;

    /** 创建者 */
    private String createBy;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 更新者 */
    private String updateBy;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /** 备注 */
    private String remark;

    /** 是否被删除 */
    private String isDel;
    /** 状态，默认1 */

    /** 创建人 */
    private Long createPersonal;

    /** 更新人 */
    private Long updatePersonal;


    /** 请求参数 */
    private Map<String, Object> params;

    public Map<String, Object> getParams() {
        if (params == null) {
            params = new HashMap<>();
        }
        return params;
    }

    public Long getSnowID(){
        try {
            Thread.sleep(1);
            SnowFlakeUtil snowFlakeUtil = new SnowFlakeUtil();
            return snowFlakeUtil.snowflakeId();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return getSnowID();
    }
}
