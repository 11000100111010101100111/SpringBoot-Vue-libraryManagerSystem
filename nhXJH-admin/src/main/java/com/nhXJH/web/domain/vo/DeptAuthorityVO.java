package com.nhXJH.web.domain.vo;

import com.nhXJH.common.core.domain.entity.SysDept;
import com.nhXJH.common.core.domain.entity.SysUser;
import com.nhXJH.web.domain.BaseDeptAuthority;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by IntelliJ IDEA.
 * User: xjh
 * Date: 2022/2/27
 * Time: 10:25
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeptAuthorityVO extends BaseDeptAuthority {

    private SysUser createUser;
    private SysUser updateUser;
    private SysUser user;
    private SysDept dept;
}
