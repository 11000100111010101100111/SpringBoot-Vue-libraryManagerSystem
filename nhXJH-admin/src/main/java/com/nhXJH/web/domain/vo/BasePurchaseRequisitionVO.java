package com.nhXJH.web.domain.vo;

import com.nhXJH.common.core.domain.entity.SysDept;
import com.nhXJH.common.core.domain.entity.SysUser;
import com.nhXJH.web.domain.BasePurchaseRequisition;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by IntelliJ IDEA.
 * User: xjh
 * Date: 2022/3/10
 * Time: 13:31
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BasePurchaseRequisitionVO {
    Long id;
    BasePurchaseRequisition purchase;
    SysDept dept;
    SysUser createPersonal;
}
