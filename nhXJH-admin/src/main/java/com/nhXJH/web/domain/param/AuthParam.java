package com.nhXJH.web.domain.param;

import com.nhXJH.web.domain.AttachFile;
import com.nhXJH.web.domain.LibraryAuth;
import com.nhXJH.web.domain.entity.SysFile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: xjh
 * Date: 2022/2/15
 * Time: 11:21
 **/
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AuthParam extends LibraryAuth {
    List<AttachFile> picList;

    public LibraryAuth getAuth(){
        LibraryAuth auth = new LibraryAuth();
        auth.setId(this.getId());
        auth.setName(this.getName());
        auth.setSex(this.getSex());
        auth.setCountry(this.getCountry());
        auth.setNickName(this.getNickName());
        auth.setEnglishName(this.getEnglishName());
        auth.setSynopsis(this.getSynopsis());
        auth.setIdentity(this.getIdentity());
        auth.setStatus(this.getStatus());
        auth.setCreatePersonal(this.getCreatePersonal());
        auth.setCreateTime(this.getCreateTime());
        auth.setUpdatePersonal(this.getUpdatePersonal());
        auth.setUpdateTime(this.getUpdateTime());
        auth.setRemark(this.getRemark());
        return auth;
    }
}
