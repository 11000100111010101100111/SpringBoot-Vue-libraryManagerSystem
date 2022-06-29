package com.nhXJH.web.service.impl;

import java.util.List;

import com.nhXJH.web.domain.UnitConversion;
import com.nhXJH.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nhXJH.web.mapper.UnitConversionMapper;
//import com.nhXJH.web.domain.UnitConversion;
import com.nhXJH.web.service.IUnitConversionService;

/**
 * 计量单位换算信息Service业务层处理
 * 
 * @author xjh
 * @date 2022-01-25
 */
@Service
public class UnitConversionServiceImpl extends  ServiceImpl<UnitConversionMapper, UnitConversion>
        implements IUnitConversionService  {
    @Autowired
    private UnitConversionMapper unitConversionMapper;

    /**
     * 查询计量单位换算信息
     * 
     * @param id 计量单位换算信息主键
     * @return 计量单位换算信息
     */
    @Override
    public UnitConversion selectUnitConversionById(Long id) {
        return unitConversionMapper.selectUnitConversionById(id);
    }

    @Override
    public Integer getCount(UnitConversion unitConversion) {
        return unitConversionMapper.getCount(unitConversion);
    }

    /**
     * 查询计量单位换算信息列表
     * 
     * @param unitConversion 计量单位换算信息
     * @return 计量单位换算信息
     */
    @Override
    public List<UnitConversion> selectUnitConversionList(UnitConversion unitConversion) {
        return unitConversionMapper.selectUnitConversionList(unitConversion);
    }

    /**
     * 新增计量单位换算信息
     * 
     * @param unitConversion 计量单位换算信息
     * @return 结果
     */
    @Override
    public int insertUnitConversion(UnitConversion unitConversion) {
        unitConversion.setCreateTime(DateUtils.getNowDate());
        return unitConversionMapper.insertUnitConversion(unitConversion);
    }

    /**
     * 修改计量单位换算信息
     * 
     * @param unitConversion 计量单位换算信息
     * @return 结果
     */
    @Override
    public int updateUnitConversion(UnitConversion unitConversion) {
        unitConversion.setUpdateTime(DateUtils.getNowDate());
        return unitConversionMapper.updateUnitConversion(unitConversion);
    }

    /**
     * 批量删除计量单位换算信息
     * 
     * @param ids 需要删除的计量单位换算信息主键
     * @return 结果
     */
    @Override
    public int deleteUnitConversionByIds(Long[] ids) {
        return unitConversionMapper.deleteUnitConversionByIds(ids);
    }

    /**
     * 删除计量单位换算信息信息
     * 
     * @param id 计量单位换算信息主键
     * @return 结果
     */
    @Override
    public int deleteUnitConversionById(Long id) {
        return unitConversionMapper.deleteUnitConversionById(id);
    }
}
