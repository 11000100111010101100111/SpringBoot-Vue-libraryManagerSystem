package com.nhXJH.web.service;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;
import com.nhXJH.web.domain.BaseFile;
import com.nhXJH.web.domain.LibraryBook;
import com.nhXJH.web.domain.LibraryMechanism;
import com.nhXJH.web.domain.param.MechanismParam;
import com.nhXJH.web.domain.param.RefulshMechaismFileParam;
import com.nhXJH.web.domain.vo.MechanismVO;
import com.nhXJH.web.domain.vo.RefulshMechaismFileVO;

/**
 * 图书管理机构信息Service接口
 * 
 * @author xjh
 * @date 2022-01-25
 */
public interface ILibraryMechanismService extends IService<LibraryMechanism>{
    /**
     * 查询图书管理机构信息
     * 
     * @param id 图书管理机构信息主键
     * @return 图书管理机构信息
     */
    public MechanismVO selectLibraryMechanismById(Long id);
    public Integer getCount(MechanismParam libraryMechanism);
    /**
     * 查询图书管理机构信息列表
     * 
     * @param libraryMechanism 图书管理机构信息
     * @return 图书管理机构信息集合
     */
    public List<LibraryMechanism> selectLibraryMechanismList(MechanismParam libraryMechanism);
    /**
     * 查询图书管理机构信息列表
     *
     * @param libraryMechanism 图书管理机构信息
     * @return 图书管理机构信息集合
     */
    public List<MechanismVO> selectLibraryMechanismVOList(MechanismParam libraryMechanism);
    /**
     * 新增图书管理机构信息
     * 
     * @param libraryMechanism 图书管理机构信息
     * @return 结果
     */
    public int insertLibraryMechanism(MechanismParam libraryMechanism);

    /**
     * 修改图书管理机构信息
     * 
     * @param libraryMechanism 图书管理机构信息
     * @return 结果
     */
    public int updateLibraryMechanism(LibraryMechanism libraryMechanism);

    /**
     * 批量删除图书管理机构信息
     * 
     * @param ids 需要删除的图书管理机构信息主键集合
     * @return 结果
     */
    public int deleteLibraryMechanismByIds(Long[] ids);

    /**
     * 删除图书管理机构信息信息
     * 
     * @param id 图书管理机构信息主键
     * @return 结果
     */
    public int deleteLibraryMechanismById(Long id);

    /**
     * 修改状态
     *
     * @param mechanism 作者信息
     * @return 修改结果
     */
    public int updateUserStatus(LibraryMechanism mechanism);

    /**
     * 更新机构图片资源
     *
     * @param param 提交的资源信息
     * @param file 操作的实体
     * @return 修改结果
     */
    public RefulshMechaismFileVO reflushFile(List<RefulshMechaismFileParam> param, BaseFile file);
}
