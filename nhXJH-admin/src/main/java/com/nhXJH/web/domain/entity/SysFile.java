package com.nhXJH.web.domain.entity;

import java.util.Date;
import java.io.Serializable;

/**
 * (SysFile)实体类
 *
 * @author makejava
 * @since 2022-01-17 14:19:13
 */
public class SysFile implements Serializable {
    private static final long serialVersionUID = 439547504283151268L;
    /**
     * 文件id
     */
    private Long id;
    /**
     * 文件名称
     */
    private String name;
    /**
     * 新文件名称
     */
    private String newName;
    /**
     * 文件组id
     */
    private Long groudId;
    /**
     * 是否被删除，默认0，删除1
     */
    private Integer isDel;
    /**
     * 说明文字
     */
    private String mark;
    /**
     * 是否有效，默认1有效，无效为0
     */
    private Integer status;
    /**
     * 文件上传人id
     */
    private Long createPerson;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 删除时间
     */
    private Date delTime;
    /**
     * 删除人id
     */
    private Long delPerson;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNewName() {
        return newName;
    }

    public void setNewName(String newName) {
        this.newName = newName;
    }

    public Long getGroudId() {
        return groudId;
    }

    public void setGroudId(Long groudId) {
        this.groudId = groudId;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getCreatePerson() {
        return createPerson;
    }

    public void setCreatePerson(Long createPerson) {
        this.createPerson = createPerson;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getDelTime() {
        return delTime;
    }

    public void setDelTime(Date delTime) {
        this.delTime = delTime;
    }

    public Long getDelPerson() {
        return delPerson;
    }

    public void setDelPerson(Long delPerson) {
        this.delPerson = delPerson;
    }

}

