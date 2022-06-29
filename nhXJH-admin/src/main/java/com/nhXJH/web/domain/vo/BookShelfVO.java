package com.nhXJH.web.domain.vo;

import com.nhXJH.common.core.domain.entity.SysDept;
import com.nhXJH.common.core.domain.entity.SysUser;
import com.nhXJH.system.domain.po.BaseBookClassCn;
import com.nhXJH.web.domain.StockBookshelf;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by IntelliJ IDEA.
 * User: xjh
 * Date: 2022/2/22
 * Time: 10:07
 * 图书书架返回结果实体
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookShelfVO extends StockBookshelf {
    SysUser createUser;
    SysUser updateUser;
    SysDept sysDept;
    BaseBookClassCn bookClassCn;

    public BookShelfVO(
            SysUser createUser,
            SysUser updateUser,
            SysDept sysDept,
            BaseBookClassCn bookClassCn,
            StockBookshelf bookshelf) {
        this.createUser = createUser;
        this.updateUser = updateUser;
        this.sysDept = sysDept;
        this.bookClassCn = bookClassCn;
        this.setStockShelf(bookshelf);
    }
    public void setStockShelf(StockBookshelf stockShelf){
       this.setId (stockShelf.getId());
       this.setCode (stockShelf.getCode());
       this.setName (stockShelf.getName());
       this.setTitle (stockShelf.getTitle());
       this.setBookType (stockShelf.getBookType());
       this.setMark (stockShelf.getMark());
       this.setStatus (stockShelf.getStatus());
       this.setDept (stockShelf.getDept());
       this.setCreatePersonal (stockShelf.getCreatePersonal());
       this.setUpdatePersonal (stockShelf.getUpdatePersonal());
       this.setCreateTime (stockShelf.getCreateTime());
       this.setUpdateTime (stockShelf.getUpdateTime());
    }
}
