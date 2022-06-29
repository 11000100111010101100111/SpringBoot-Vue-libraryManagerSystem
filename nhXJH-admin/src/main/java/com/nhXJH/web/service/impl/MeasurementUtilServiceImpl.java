package com.nhXJH.web.service.impl;

import java.util.List;

import com.nhXJH.web.domain.MeasurementUtil;
import com.nhXJH.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nhXJH.web.mapper.MeasurementUtilMapper;
//import com.nhXJH.web.domain.MeasurementUtil;
import com.nhXJH.web.service.IMeasurementUtilService;

/**
 * 物品单位信息Service业务层处理
 * 
 * @author xjh
 * @date 2022-01-25
 */
@Service
public class MeasurementUtilServiceImpl extends ServiceImpl<MeasurementUtilMapper, MeasurementUtil>
        implements IMeasurementUtilService  {
    @Autowired
    private MeasurementUtilMapper measurementUtilMapper;

    /**
     * 查询物品单位信息
     * 
     * @param id 物品单位信息主键
     * @return 物品单位信息
     */
    @Override
    public MeasurementUtil selectMeasurementUtilById(Long id) {
        return measurementUtilMapper.selectMeasurementUtilById(id);
    }

    @Override
    public Integer getCount(MeasurementUtil measurementUtil) {
        return measurementUtilMapper.getCount(measurementUtil);
    }

    /**
     * 查询物品单位信息列表
     * 
     * @param measurementUtil 物品单位信息
     * @return 物品单位信息
     */
    @Override
    public List<MeasurementUtil> selectMeasurementUtilList(MeasurementUtil measurementUtil) {
        return measurementUtilMapper.selectMeasurementUtilList(measurementUtil);
    }

    /**
     * 新增物品单位信息
     * 
     * @param measurementUtil 物品单位信息
     * @return 结果
     */
    @Override
    public int insertMeasurementUtil(MeasurementUtil measurementUtil) {
        measurementUtil.setCreateTime(DateUtils.getNowDate());
        return measurementUtilMapper.insertMeasurementUtil(measurementUtil);
    }

    /**
     * 修改物品单位信息
     * 
     * @param measurementUtil 物品单位信息
     * @return 结果
     */
    @Override
    public int updateMeasurementUtil(MeasurementUtil measurementUtil) {
        measurementUtil.setUpdateTime(DateUtils.getNowDate());
        return measurementUtilMapper.updateMeasurementUtil(measurementUtil);
    }

    /**
     * 批量删除物品单位信息
     * 
     * @param ids 需要删除的物品单位信息主键
     * @return 结果
     */
    @Override
    public int deleteMeasurementUtilByIds(Long[] ids) {
        return measurementUtilMapper.deleteMeasurementUtilByIds(ids);
    }

    /**
     * 删除物品单位信息信息
     * 
     * @param id 物品单位信息主键
     * @return 结果
     */
    @Override
    public int deleteMeasurementUtilById(Long id) {
        return measurementUtilMapper.deleteMeasurementUtilById(id);
    }
}
