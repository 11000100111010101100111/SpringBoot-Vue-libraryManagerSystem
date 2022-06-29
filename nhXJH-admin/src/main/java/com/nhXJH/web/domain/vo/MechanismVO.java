package com.nhXJH.web.domain.vo;

import com.nhXJH.common.core.domain.entity.SysDept;
import com.nhXJH.common.core.domain.entity.SysUser;
import com.nhXJH.web.domain.LibraryMechanism;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by IntelliJ IDEA.
 * User: xjh
 * Date: 2022/2/17
 * Time: 9:06
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MechanismVO extends LibraryMechanism {
    SysUser createUser;
    SysUser updateUser;
    SysDept dept;
    public MechanismVO(LibraryMechanism mechanism, SysUser createUser, SysUser updateUser,SysDept dept){
        this.createUser = createUser;
        this.updateUser = updateUser;
        this.setMechanism(mechanism);
        this.setDept(dept);
    }
    public void setMechanism(LibraryMechanism mechanism){
        this.setId(mechanism.getId());
        this.setDeptId(mechanism.getDeptId());
        this.setAddr(mechanism.getAddr());
        this.setMark(mechanism.getMark());
        this.setEmail(mechanism.getEmail());
        this.setPhone(mechanism.getPhone());
        this.setStatus(mechanism.getStatus());
        this.setCreateTime(mechanism.getCreateTime());
        this.setUpdateTime(mechanism.getUpdateTime());
    }
    public LibraryMechanism getMechanism(){
        LibraryMechanism mechanism = new LibraryMechanism();
        mechanism.setId(mechanism.getId());
        mechanism.setDeptId(this.getDeptId());
        mechanism.setAddr(this.getAddr());
        mechanism.setMark(this.getMark());
        mechanism.setEmail(this.getEmail());
        mechanism.setPhone(this.getPhone());
        mechanism.setStatus(this.getStatus());
        mechanism.setCreateTime(this.getCreateTime());
        mechanism.setUpdateTime(this.getUpdateTime());
        return mechanism;
    }
}
