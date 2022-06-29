package com.nhXJH.web.domain.vo;

import com.nhXJH.common.core.domain.entity.SysUser;
import com.nhXJH.system.domain.po.BaseBookClassCn;
import com.nhXJH.web.domain.StockBookshelf;
import com.nhXJH.web.domain.StockStorey;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by IntelliJ IDEA.
 * User: xjh
 * Date: 2022/2/23
 * Time: 14:26
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StoreyVO extends StockStorey {
    SysUser createUser;
    SysUser updateUser;
    BaseBookClassCn bookClass;
    StockBookshelf bookShelf;

    public void setStorey(StockStorey storey){
        this.setId(storey.getId());
        this.setCode(storey.getCode());
        this.setName(storey.getName());
        this.setTitle(storey.getTitle());
        this.setBookType(storey.getBookType());
        this.setMark(storey.getMark());
        this.setStatus(storey.getStatus());
        this.setBookshelf(storey.getBookshelf());
        this.setCreatePersonal(storey.getCreatePersonal());
        this.setCreateTime(storey.getCreateTime());
        this.setUpdatePersonal(storey.getUpdatePersonal());
        this.setUpdateTime(storey.getUpdateTime());
        this.setIsDel(storey.getIsDel());
    }

    public StockStorey getStorey(){
        StockStorey storey = new StockStorey();
        storey.setId(this.getId());
        storey.setCode(this.getCode());
        storey.setName(this.getName());
        storey.setTitle(this.getTitle());
        storey.setBookType(this.getBookType());
        storey.setMark(this.getMark());
        storey.setStatus(this.getStatus());
        storey.setBookshelf(this.getBookshelf());
        storey.setCreatePersonal(this.getCreatePersonal());
        storey.setCreateTime(this.getCreateTime());
        storey.setUpdatePersonal(this.getUpdatePersonal());
        storey.setUpdateTime(this.getUpdateTime());
        storey.setIsDel(this.getIsDel());
        return storey;
    }

    public StoreyVO(
            SysUser createUser,
            SysUser updateUser,
            BaseBookClassCn bookClass,
            StockBookshelf bookShelf,
            StockStorey storey) {
        this.createUser = createUser;
        this.updateUser = updateUser;
        this.bookClass = bookClass;
        this.bookShelf = bookShelf;
        this.setStorey(storey);
    }
}
