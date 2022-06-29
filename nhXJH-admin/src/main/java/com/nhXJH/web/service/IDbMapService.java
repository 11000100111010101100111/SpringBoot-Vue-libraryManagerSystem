package com.nhXJH.web.service;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;
import com.nhXJH.web.domain.BookStock;
import com.nhXJH.web.domain.DbMap;

/**
 * 表信息映射Service接口
 * 
 * @author xjh
 * @date 2022-01-25
 */
public interface IDbMapService extends IService<DbMap>{
    /**
     * 查询表信息映射
     * 
     * @param id 表信息映射主键
     * @return 表信息映射
     */
    public DbMap selectDbMapById(Long id);
    public Integer getCount(DbMap dbMap);
    /**
     * 查询表信息映射列表
     * 
     * @param dbMap 表信息映射
     * @return 表信息映射集合
     */
    public List<DbMap> selectDbMapList(DbMap dbMap);

    /**
     * 新增表信息映射
     * 
     * @param dbMap 表信息映射
     * @return 结果
     */
    public int insertDbMap(DbMap dbMap);

    /**
     * 修改表信息映射
     * 
     * @param dbMap 表信息映射
     * @return 结果
     */
    public int updateDbMap(DbMap dbMap);

    /**
     * 批量删除表信息映射
     * 
     * @param ids 需要删除的表信息映射主键集合
     * @return 结果
     */
    public int deleteDbMapByIds(Long[] ids);

    /**
     * 删除表信息映射信息
     * 
     * @param id 表信息映射主键
     * @return 结果
     */
    public int deleteDbMapById(Long id);
}
