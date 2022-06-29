package com.nhXJH.web.domain.vo;

import com.nhXJH.common.core.domain.entity.SysUser;
import com.nhXJH.web.domain.LibraryPress;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: xjh
 * Date: 2022/2/16
 * Time: 17:02
 * 出版社返回结果集
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PressVO extends LibraryPress {
    SysUser createUser;
    SysUser updateUser;
    public PressVO(LibraryPress press,SysUser createUser,SysUser updateUser){
        this.createUser = createUser;
        this.updateUser = updateUser;
        this.setPress(press);
    }
    public void setPress(LibraryPress press){
        this.setId(press.getId());
        this.setName(press.getName());
        this.setAddr(press.getAddr());
        this.setMark(press.getMark());
        this.setEmail(press.getEmail());
        this.setPhone(press.getPhone());
        this.setStatus(press.getStatus());
        this.setCreatePersonal(press.getCreatePersonal());
        this.setUpdatePersonal(press.getUpdatePersonal());
        this.setCreateTime(press.getCreateTime());
        this.setUpdateTime(press.getUpdateTime());
        this.setDelTime(press.getDelTime());
        this.setDelPersonal(press.getDelPersonal());
    }
    public LibraryPress getPress(){
        LibraryPress press = new LibraryPress();
        press.setId(press.getId());
        press.setName(this.getName());
        press.setAddr(this.getAddr());
        press.setMark(this.getMark());
        press.setEmail(this.getEmail());
        press.setPhone(this.getPhone());
        press.setStatus(this.getStatus());
        press.setCreatePersonal(this.getCreatePersonal());
        press.setUpdatePersonal(this.getUpdatePersonal());
        press.setCreateTime(this.getCreateTime());
        press.setUpdateTime(this.getUpdateTime());
        press.setDelTime(this.getDelTime());
        press.setDelPersonal(this.getDelPersonal());
        return press;
    }
}
