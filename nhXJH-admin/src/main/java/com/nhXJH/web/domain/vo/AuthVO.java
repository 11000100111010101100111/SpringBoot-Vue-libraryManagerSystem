package com.nhXJH.web.domain.vo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.nhXJH.common.core.domain.entity.SysUser;
import com.nhXJH.web.domain.LibraryAuth;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by IntelliJ IDEA.
 * User: xjh
 * Date: 2022/2/16
 * Time: 14:27
 * 作者信息实体返回结果集
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthVO extends LibraryAuth{
    SysUser createUser;
    SysUser updateUser;
    public LibraryAuth getAuth(){
        LibraryAuth auth = new LibraryAuth();
        auth.setId (this.getId());
        auth.setName (this.getName());
        auth.setSex (this.getSex());
        auth.setCountry (this.getCountry());
        auth.setNickName (this.getNickName());
        auth.setEnglishName (this.getEnglishName());
        auth.setSynopsis (this.getSynopsis ());
        auth.setIdentity (this.getIdentity());
        auth.setStatus (this.getStatus());
        auth.setCreatePersonal (this.getCreatePersonal());
        auth.setUpdatePersonal (this.getUpdatePersonal());
        auth.setCreateTime(this.getCreateTime());
        auth.setUpdateTime(this.getUpdateTime());
        return auth;
    }
    public void setAuth(LibraryAuth auth){
        this.setId (auth.getId());
        this.setName (auth.getName());
        this.setSex (auth.getSex());
        this.setCountry (auth.getCountry());
        this.setNickName (auth.getNickName());
        this.setEnglishName (auth.getEnglishName());
        this.setSynopsis (auth.getSynopsis ());
        this.setIdentity (auth.getIdentity());
        this.setStatus (auth.getStatus());
        this.setCreatePersonal (auth.getCreatePersonal());
        this.setUpdatePersonal (auth.getUpdatePersonal());
        this.setCreateTime(auth.getCreateTime());
        this.setUpdateTime(auth.getUpdateTime());
    }
    public AuthVO(LibraryAuth auth,SysUser createUser,SysUser updateUser){
        this.setCreateUser(createUser);
        this.setAuth(auth);
        this.setUpdateUser(updateUser);
    }
}
