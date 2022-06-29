package com.nhXJH.web.domain.param;

import com.nhXJH.web.domain.LibraryMechanism;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by IntelliJ IDEA.
 * User: xjh
 * Date: 2022/2/17
 * Time: 11:23
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MechanismParam extends LibraryMechanism {
    @ApiModelProperty("图书管理机构名称")
    String deptName;
    @ApiModelProperty("图书管理机构联系人名称")
    String contacts;
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
