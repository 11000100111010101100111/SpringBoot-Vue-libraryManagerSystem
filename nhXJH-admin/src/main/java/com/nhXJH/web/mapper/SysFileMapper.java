package com.nhXJH.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nhXJH.web.domain.AttachFile;
import com.nhXJH.web.domain.StockStorey;
import com.nhXJH.web.domain.entity.SysFile;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * (AttachFile)表数据库访问层
 *
 * @author makejava
 * @since 2022-01-17 14:23:34
 */
@Mapper
public interface SysFileMapper extends BaseMapper<AttachFile> {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    AttachFile queryById(Long id);
    public Integer getCount(AttachFile sysFile);
    /**
     * 查询指定行数据
     *
     * @param AttachFile 查询条件
     * @param pageable         分页对象
     * @return 对象列表
     */
    List<AttachFile> queryAllByLimit(AttachFile AttachFile, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param sysFile 查询条件
     * @return 总行数
     */
    long count(AttachFile sysFile);

    /**
     * 新增数据
     *
     * @param sysFile 实例对象
     * @return 影响行数
     */
    int insert(AttachFile sysFile);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<AttachFile> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<AttachFile> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<AttachFile> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<AttachFile> entities);

    /**
     * 修改数据
     *
     * @param sysFile 实例对象
     * @return 影响行数
     */
    int update(AttachFile sysFile);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

}

