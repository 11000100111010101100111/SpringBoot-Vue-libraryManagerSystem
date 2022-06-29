package com.nhXJH.web.service.impl;

import java.util.List;

import com.nhXJH.web.domain.DbMap;
import com.nhXJH.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nhXJH.web.mapper.DbMapMapper;
//import com.nhXJH.web.domain.DbMap;
import com.nhXJH.web.service.IDbMapService;

/**
 * 表信息映射Service业务层处理
 * 
 * @author xjh
 * @date 2022-01-25
 */
@Service
public class DbMapServiceImpl extends ServiceImpl<DbMapMapper, DbMap>
        implements IDbMapService  {
    @Autowired
    private DbMapMapper dbMapMapper;

    /**
     * 查询表信息映射
     * 
     * @param id 表信息映射主键
     * @return 表信息映射
     */
    @Override
    public DbMap selectDbMapById(Long id) {
        return dbMapMapper.selectDbMapById(id);
    }

    @Override
    public Integer getCount(DbMap dbMap) {
        return dbMapMapper.getCount(dbMap);
    }

    /**
     * 查询表信息映射列表
     * 
     * @param dbMap 表信息映射
     * @return 表信息映射
     */
    @Override
    public List<DbMap> selectDbMapList(DbMap dbMap) {
        return dbMapMapper.selectDbMapList(dbMap);
    }

    /**
     * 新增表信息映射
     * 
     * @param dbMap 表信息映射
     * @return 结果
     */
    @Override
    public int insertDbMap(DbMap dbMap) {
        dbMap.setCreateTime(DateUtils.getNowDate());
        return dbMapMapper.insertDbMap(dbMap);
    }

    /**
     * 修改表信息映射
     * 
     * @param dbMap 表信息映射
     * @return 结果
     */
    @Override
    public int updateDbMap(DbMap dbMap) {
        dbMap.setUpdateTime(DateUtils.getNowDate());
        return dbMapMapper.updateDbMap(dbMap);
    }

    /**
     * 批量删除表信息映射
     * 
     * @param ids 需要删除的表信息映射主键
     * @return 结果
     */
    @Override
    public int deleteDbMapByIds(Long[] ids) {
        return dbMapMapper.deleteDbMapByIds(ids);
    }

    /**
     * 删除表信息映射信息
     * 
     * @param id 表信息映射主键
     * @return 结果
     */
    @Override
    public int deleteDbMapById(Long id) {
        return dbMapMapper.deleteDbMapById(id);
    }
}
