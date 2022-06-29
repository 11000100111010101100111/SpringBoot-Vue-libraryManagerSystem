package com.nhXJH.web.mapper;

import java.util.List;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nhXJH.web.domain.StockBookshelf;
import com.nhXJH.web.domain.StockStorey;
import org.apache.ibatis.annotations.Mapper;

/**
 * 书架层信息Mapper接口
 * 
 * @author xjh
 * @date 2022-01-25
 */
@Mapper
public interface StockStoreyMapper  extends BaseMapper<StockStorey> {
    /**
     * 查询书架层信息
     * 
     * @param id 书架层信息主键
     * @return 书架层信息
     */
    public StockStorey selectStockStoreyById(Long id);
    public Integer getCount(StockStorey stockStorey);
    /**
     * 查询书架层信息列表
     * 
     * @param stockStorey 书架层信息
     * @return 书架层信息集合
     */
    public List<StockStorey> selectStockStoreyList(StockStorey stockStorey);
    /**
     * 查询书架层信息列表
     *
     * @param array 书架层信息
     * @return 书架层信息集合
     */
    public List<StockStorey> getStoreyList(Long[] array);

    /**
     * 新增书架层信息
     * 
     * @param stockStorey 书架层信息
     * @return 结果
     */
    public int insertStockStorey(StockStorey stockStorey);

    /**
     * 修改书架层信息
     * 
     * @param stockStorey 书架层信息
     * @return 结果
     */
    public int updateStockStorey(StockStorey stockStorey);

    /**
     * 删除书架层信息
     * 
     * @param id 书架层信息主键
     * @return 结果
     */
    public int deleteStockStoreyById(Long id);

    /**
     * 批量删除书架层信息
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteStockStoreyByIds(Long[] ids);

    /**
     * 查询当前书架是否存放图书
     *
     * @param id 书架ID
     * @return 结果>0存在，否则不存在
     */
    public Integer hasBook(Long id);

    /**
     * 查询当前书架是否存放图书
     *
     * @param id 书架ID
     * @return 结果>0存在，否则不存在
     */
    public List<StockStorey> hasBooks(Long[] id);
}
