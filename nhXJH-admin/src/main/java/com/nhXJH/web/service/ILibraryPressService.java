package com.nhXJH.web.service;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;
import com.nhXJH.web.domain.LibraryPress;
import com.nhXJH.web.domain.param.MechanismParam;
import com.nhXJH.web.domain.vo.PressVO;

/**
 * 出版社信息Service接口
 * 
 * @author xjh
 * @date 2022-01-25
 */
public interface ILibraryPressService extends IService<LibraryPress>{
    /**
     * 查询出版社信息
     * 
     * @param id 出版社信息主键
     * @return 出版社信息
     */
    public PressVO selectLibraryPressById(Long id);
    public Integer getCount(LibraryPress libraryPress);
    /**
     * 查询出版社信息列表
     * 
     * @param libraryPress 出版社信息
     * @return 出版社信息集合
     */
    public List<PressVO> selectLibraryPressList(LibraryPress libraryPress);

    /**
     * 新增出版社信息
     * 
     * @param libraryPress 出版社信息
     * @return 结果
     */
    public int insertLibraryPress(LibraryPress libraryPress);

    /**
     * 修改出版社信息
     * 
     * @param libraryPress 出版社信息
     * @return 结果
     */
    public int updateLibraryPress(LibraryPress libraryPress);

    /**
     * 批量删除出版社信息
     * 
     * @param ids 需要删除的出版社信息主键集合
     * @return 结果
     */
    public int deleteLibraryPressByIds(Long[] ids,Long operator);

    /**
     * 删除出版社信息信息
     * 
     * @param id 出版社信息主键
     * @return 结果
     */
    public int deleteLibraryPressById(Long id,Long operator);

    /**
     * 状态修改
     */
    public int updateUserStatus(LibraryPress press);
}
