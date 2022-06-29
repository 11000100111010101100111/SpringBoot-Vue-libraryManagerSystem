package com.nhXJH.web.service;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;
import com.nhXJH.web.domain.LibraryStock;
import com.nhXJH.web.domain.MeasurementUtil;

/**
 * 物品单位信息Service接口
 * 
 * @author xjh
 * @date 2022-01-25
 */
public interface IMeasurementUtilService extends IService<MeasurementUtil>{
    /**
     * 查询物品单位信息
     * 
     * @param id 物品单位信息主键
     * @return 物品单位信息
     */
    public MeasurementUtil selectMeasurementUtilById(Long id);
    public Integer getCount(MeasurementUtil measurementUtil);
    /**
     * 查询物品单位信息列表
     * 
     * @param measurementUtil 物品单位信息
     * @return 物品单位信息集合
     */
    public List<MeasurementUtil> selectMeasurementUtilList(MeasurementUtil measurementUtil);

    /**
     * 新增物品单位信息
     * 
     * @param measurementUtil 物品单位信息
     * @return 结果
     */
    public int insertMeasurementUtil(MeasurementUtil measurementUtil);

    /**
     * 修改物品单位信息
     * 
     * @param measurementUtil 物品单位信息
     * @return 结果
     */
    public int updateMeasurementUtil(MeasurementUtil measurementUtil);

    /**
     * 批量删除物品单位信息
     * 
     * @param ids 需要删除的物品单位信息主键集合
     * @return 结果
     */
    public int deleteMeasurementUtilByIds(Long[] ids);

    /**
     * 删除物品单位信息信息
     * 
     * @param id 物品单位信息主键
     * @return 结果
     */
    public int deleteMeasurementUtilById(Long id);
}
