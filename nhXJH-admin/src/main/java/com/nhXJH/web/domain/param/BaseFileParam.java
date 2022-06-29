package com.nhXJH.web.domain.param;

import com.nhXJH.web.domain.BaseFile;
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
 * Time: 11:00
 **/
@Data
@AllArgsConstructor
public class BaseFileParam extends BaseFile {
    List<SysFile> sysFile;
    public BaseFileParam() {
        this.setFileId(super.getFileId());
        this.setModelId( super.getModelId());
        this.setDbId (super.getDbId());
        this.setUrl (super.getUrl());
        this.setMark (super.getMark());
        this.setStatus (super.getStatus());
        this.setCreatePerson (super.getCreatePerson());
        this.setCreateTime(super.getCreateTime());
        this.setUpdatePerson (super.getUpdatePerson());
        this.setUpdateTime(super.getUpdateTime());
    }
    public BaseFile getBaseFile(){
        return new BaseFileParam();
    }
}
