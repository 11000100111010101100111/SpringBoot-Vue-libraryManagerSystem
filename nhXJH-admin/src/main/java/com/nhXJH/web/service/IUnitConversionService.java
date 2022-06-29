package com.nhXJH.web.service;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;
import com.nhXJH.web.domain.StockStorey;
import com.nhXJH.web.domain.UnitConversion;

/**
 * 计量单位换算信息Service接口
 * 
 * @author xjh
 * @date 2022-01-25
 */
public interface IUnitConversionService extends IService<UnitConversion>{
    /**
     * 查询计量单位换算信息
     * 
     * @param id 计量单位换算信息主键
     * @return 计量单位换算信息
     */
    public UnitConversion selectUnitConversionById(Long id);
    public Integer getCount(UnitConversion unitConversion);
    /**
     * 查询计量单位换算信息列表
     * 
     * @param unitConversion 计量单位换算信息
     * @return 计量单位换算信息集合
     */
    public List<UnitConversion> selectUnitConversionList(UnitConversion unitConversion);

    /**
     * 新增计量单位换算信息
     * 
     * @param unitConversion 计量单位换算信息
     * @return 结果
     */
    public int insertUnitConversion(UnitConversion unitConversion);

    /**
     * 修改计量单位换算信息
     * 
     * @param unitConversion 计量单位换算信息
     * @return 结果
     */
    public int updateUnitConversion(UnitConversion unitConversion);

    /**
     * 批量删除计量单位换算信息
     * 
     * @param ids 需要删除的计量单位换算信息主键集合
     * @return 结果
     */
    public int deleteUnitConversionByIds(Long[] ids);

    /**
     * 删除计量单位换算信息信息
     * 
     * @param id 计量单位换算信息主键
     * @return 结果
     */
    public int deleteUnitConversionById(Long id);
}
