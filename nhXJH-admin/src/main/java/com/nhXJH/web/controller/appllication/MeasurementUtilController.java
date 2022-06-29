package com.nhXJH.web.controller.appllication;

import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.nhXJH.web.domain.MeasurementUtil;
import com.nhXJH.web.service.IMeasurementUtilService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.nhXJH.common.annotation.Log;
import com.nhXJH.common.core.controller.BaseController;
import com.nhXJH.common.core.domain.AjaxResult;
import com.nhXJH.common.enums.BusinessType;
import com.nhXJH.common.utils.poi.ExcelUtil;
import com.nhXJH.common.core.page.TableDataInfo;

/**
 * 物品单位信息Controller
 * 
 * @author xjh
 * @date 2022-01-25
 */
@RestController
@RequestMapping("/userApplication/util")
@Api(tags = {"物品单位信息"})
public class MeasurementUtilController extends BaseController {
    @Autowired
    private IMeasurementUtilService measurementUtilService;

    /**
     * 查询物品单位信息列表
     */
    @PreAuthorize("@ss.hasPermi('userApplication:util:list')")
    @GetMapping("/list")
    @ApiOperation(value ="查询物品单位信息列表",notes = "查询物品单位信息列表")
    public TableDataInfo list(MeasurementUtil measurementUtil) {
        startPage();
        List<MeasurementUtil> list = measurementUtilService.selectMeasurementUtilList(measurementUtil);
        return getDataTable(list);
    }
    /**
     * 查询物品单位信息列表
     */
    @PreAuthorize("@ss.hasPermi('userApplication:util:list')")
    @PostMapping("/all")
    @ApiOperation(value ="查询物品单位信息列表",notes = "查询物品单位信息列表")
    public AjaxResult all(MeasurementUtil measurementUtil) {
        List<MeasurementUtil> list = measurementUtilService.selectMeasurementUtilList(measurementUtil);
        return AjaxResult.success(list);
    }
    /**
     * 导出物品单位信息列表
     */
    @PreAuthorize("@ss.hasPermi('userApplication:util:export')")
    @Log(title = "物品单位信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ApiOperation(value ="导出物品单位信息列表",notes = "导出物品单位信息列表")
    public void export(HttpServletResponse response, MeasurementUtil measurementUtil) {
        List<MeasurementUtil> list = measurementUtilService.selectMeasurementUtilList(measurementUtil);
        ExcelUtil<MeasurementUtil> util = new ExcelUtil<MeasurementUtil>(MeasurementUtil.class);
        util.exportExcel(response, list, "物品单位信息数据");
    }

    /**
     * 获取物品单位信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('userApplication:util:query')")
    @GetMapping(value = "/{id}")
    @ApiOperation(value ="获取物品单位信息详细信息",notes = "获取物品单位信息详细信息")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(measurementUtilService.selectMeasurementUtilById(id));
    }

    /**
     * 新增物品单位信息
     */
    @PreAuthorize("@ss.hasPermi('userApplication:util:add')")
    @Log(title = "物品单位信息", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation(value ="新增物品单位信息",notes = "新增物品单位信息")
    public AjaxResult add(@RequestBody MeasurementUtil measurementUtil) {
        measurementUtil.setCreateTime(new Date());
        measurementUtil.setCreatePersonal(getUserId());
        measurementUtil.setId(measurementUtil.getSnowID());
        return toAjax(measurementUtilService.insertMeasurementUtil(measurementUtil));
    }

    /**
     * 修改物品单位信息
     */
    @PreAuthorize("@ss.hasPermi('userApplication:util:edit')")
    @Log(title = "物品单位信息", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation(value ="修改物品单位信息",notes = "修改物品单位信息")
    public AjaxResult edit(@RequestBody MeasurementUtil measurementUtil) {
        measurementUtil.setUpdateTime(new Date());
        measurementUtil.setUpdatePersonal(getUserId());
        return toAjax(measurementUtilService.updateMeasurementUtil(measurementUtil));
    }

    /**
     * 删除物品单位信息
     */
    @PreAuthorize("@ss.hasPermi('userApplication:util:remove')")
    @Log(title = "物品单位信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    @ApiOperation(value ="删除物品单位信息",notes = "删除物品单位信息")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(measurementUtilService.deleteMeasurementUtilByIds(ids));
    }
}
