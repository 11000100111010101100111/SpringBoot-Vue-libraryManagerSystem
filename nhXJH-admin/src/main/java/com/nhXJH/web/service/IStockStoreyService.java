package com.nhXJH.web.service;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;
import com.nhXJH.web.domain.PurchaseTemplate;
import com.nhXJH.web.domain.StockStorey;
import com.nhXJH.web.domain.vo.StoreyVO;

/**
 * 书架层信息Service接口
 * 
 * @author xjh
 * @date 2022-01-25
 */
public interface IStockStoreyService extends IService<StockStorey>{
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
     * 查询书架层结果集信息列表
     *
     * @param stockStorey 书架层信息
     * @return 书架层信息集合
     */
    public List<StoreyVO> selectStockStoreyVOList(StockStorey stockStorey);
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
     * 批量删除书架层信息
     * 
     * @param ids 需要删除的书架层信息主键集合
     * @return 结果
     */
    public List<StockStorey> deleteStockStoreyByIds(Long[] ids);

    /**
     * 删除书架层信息信息
     * 
     * @param id 书架层信息主键
     * @return 结果
     */
    public int deleteStockStoreyById(Long id);


    /**
     * 查询当前书架是否存放图书
     *
     * @param id 书架ID
     * @return 结果>0存在，否则不存在
     */
    public Integer hasBook(Long id);
}
