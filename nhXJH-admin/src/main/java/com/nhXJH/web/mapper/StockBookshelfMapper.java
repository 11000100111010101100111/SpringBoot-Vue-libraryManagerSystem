package com.nhXJH.web.mapper;

import java.util.List;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nhXJH.web.domain.PurchaseTemplate;
import com.nhXJH.web.domain.StockBookshelf;
import org.apache.ibatis.annotations.Mapper;

/**
 * 书架信息Mapper接口
 * 
 * @author xjh
 * @date 2022-01-25
 */
@Mapper
public interface StockBookshelfMapper  extends BaseMapper<StockBookshelf> {
    /**
     * 查询书架信息
     * 
     * @param id 书架信息主键
     * @return 书架信息
     */
    public StockBookshelf selectStockBookshelfById(Long id);
    /**
     * 查询书架信息
     *
     * @param ids 书架信息主键
     * @return 书架信息
     */
    public List<StockBookshelf> selectStockBookshelfByIds(Long[] ids);
    /**
     * 查询书架信息列表
     * 
     * @param stockBookshelf 书架信息
     * @return 书架信息集合
     */
    public List<StockBookshelf> selectStockBookshelfList(StockBookshelf stockBookshelf);
    /**
     * 查询书架总数
     *
     * @param stockBookshelf 书架信息
     * @return 书架信息总数
     */
    public Long selectCount(StockBookshelf stockBookshelf);
    /**
     * 新增书架信息
     * 
     * @param stockBookshelf 书架信息
     * @return 结果
     */
    public int insertStockBookshelf(StockBookshelf stockBookshelf);

    /**
     * 修改书架信息
     * 
     * @param stockBookshelf 书架信息
     * @return 结果
     */
    public int updateStockBookshelf(StockBookshelf stockBookshelf);

    /**
     * 删除书架信息
     * 
     * @param id 书架信息主键
     * @return 结果
     */
    public int deleteStockBookshelfById(Long id);

    /**
     * 批量删除书架信息
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteStockBookshelfByIds(Long[] ids);

    public List<StockBookshelf> hasBookOnStock(Long[] ids);
}
