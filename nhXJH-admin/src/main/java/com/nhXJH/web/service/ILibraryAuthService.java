package com.nhXJH.web.service;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;
import com.nhXJH.web.domain.BaseFile;
import com.nhXJH.web.domain.FileToken;
import com.nhXJH.web.domain.LibraryAuth;
import com.nhXJH.web.domain.LibraryBook;
import com.nhXJH.web.domain.param.AuthParam;
import com.nhXJH.web.domain.vo.AuthVO;

/**
 * 作信息者Service接口
 * 
 * @author xjh
 * @date 2022-01-25
 */
public interface ILibraryAuthService extends IService<LibraryAuth>{
    /**
     * 查询作信息者
     * 
     * @param id 作信息者主键
     * @return 作信息者
     */
    public AuthVO selectLibraryAuthById(Long id);
    public Integer getCount(LibraryAuth libraryAuth);
    /**
     * 查询作信息者列表
     * 
     * @param libraryAuth 作信息者
     * @return 作信息者集合
     */
    public List<AuthVO> selectLibraryAuthList(LibraryAuth libraryAuth);

    /**
     * 新增作信息者
     * 
     * @param libraryAuth 作信息者
     * @return 结果
     */
    public List<BaseFile> insertLibraryAuth(AuthParam libraryAuth);

    /**
     * 修改作信息者
     * 
     * @param libraryAuth 作信息者
     * @return 结果
     */
    public List<BaseFile> updateLibraryAuth(AuthParam libraryAuth);

    /**
     * 批量删除作信息者
     * 
     * @param ids 需要删除的作信息者主键集合
     * @return 结果
     */
    public int deleteLibraryAuthByIds(Long[] ids,Long operator);

    /**
     * 删除作信息者信息
     * 
     * @param id 作信息者主键
     * @return 结果
     */
    public int deleteLibraryAuthById(Long id,Long operator);

    /**
     * 修改状态
     *
     * @param auth 作者信息
     * @return 修改结果
     */
    public int updateUserStatus(LibraryAuth auth);
}
