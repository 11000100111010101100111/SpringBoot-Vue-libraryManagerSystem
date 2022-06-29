package com.nhXJH.web.mapper;

import java.util.List;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nhXJH.web.domain.DbMap;
import com.nhXJH.web.domain.LibraryAuth;
import org.apache.ibatis.annotations.Mapper;

/**
 * 作信息者Mapper接口
 * 
 * @author xjh
 * @date 2022-01-25
 */
@Mapper
public interface LibraryAuthMapper  extends BaseMapper<LibraryAuth> {
    /**
     * 查询作信息者
     * 
     * @param id 作信息者主键
     * @return 作信息者
     */
    public LibraryAuth selectLibraryAuthById(Long id);
    public Integer getCount(LibraryAuth libraryAuth);
    /**
     * 查询作信息者列表
     * 
     * @param libraryAuth 作信息者
     * @return 作信息者集合
     */
    public List<LibraryAuth> selectLibraryAuthList(LibraryAuth libraryAuth);

    /**
     * 新增作信息者
     * 
     * @param libraryAuth 作信息者
     * @return 结果
     */
    public int insertLibraryAuth(LibraryAuth libraryAuth);

    /**
     * 修改作信息者
     * 
     * @param libraryAuth 作信息者
     * @return 结果
     */
    public int updateLibraryAuth(LibraryAuth libraryAuth);

    /**
     * 删除作信息者
     * 
     * @param id 作信息者主键
     * @param operator 操作人id
     * @return 结果
     */
    public int deleteLibraryAuthById(Long id,Long operator);

    /**
     * 批量删除作信息者
     * 
     * @param ids 需要删除的数据主键集合
     * @param operator 操作人id
     * @return 结果
     */
    public int deleteLibraryAuthByIds(Long[] ids,Long operator);
}
