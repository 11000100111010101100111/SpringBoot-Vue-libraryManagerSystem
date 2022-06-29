package com.nhXJH.web.controller.appllication;

import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.nhXJH.web.domain.UnitConversion;
import com.nhXJH.web.service.IUnitConversionService;
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
 * 计量单位换算信息Controller
 * 
 * @author xjh
 * @date 2022-01-25
 */
@RestController
@RequestMapping("/userApplication/conversion")
@Api(tags = {"计量单位换算信息"})
public class UnitConversionController extends BaseController {
    @Autowired
    private IUnitConversionService unitConversionService;

    /**
     * 查询计量单位换算信息列表
     */
    @PreAuthorize("@ss.hasAnyRoles('libraryAdmin,admin')") //@PreAuthorize("@ss.hasPermi('userApplication:conversion:list')")
    @GetMapping("/list")
    @ApiOperation(value ="查询计量单位换算信息列表",notes = "查询计量单位换算信息列表")
    public TableDataInfo list(UnitConversion unitConversion) {
        startPage();
        List<UnitConversion> list = unitConversionService.selectUnitConversionList(unitConversion);
        return getDataTable(list);
    }

    /**
     * 导出计量单位换算信息列表
     */
    @PreAuthorize("@ss.hasAnyRoles('libraryAdmin,admin')") //@PreAuthorize("@ss.hasPermi('userApplication:conversion:export')")
    @Log(title = "计量单位换算信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ApiOperation(value ="导出计量单位换算信息列表",notes = "导出计量单位换算信息列表")
    public void export(HttpServletResponse response, UnitConversion unitConversion) {
        List<UnitConversion> list = unitConversionService.selectUnitConversionList(unitConversion);
        ExcelUtil<UnitConversion> util = new ExcelUtil<UnitConversion>(UnitConversion.class);
        util.exportExcel(response, list, "计量单位换算信息数据");
    }

    /**
     * 获取计量单位换算信息详细信息
     */
    @PreAuthorize("@ss.hasAnyRoles('libraryAdmin,admin')") //@PreAuthorize("@ss.hasPermi('userApplication:conversion:query')")
    @GetMapping(value = "/{id}")
    @ApiOperation(value ="获取计量单位换算信息详细信息",notes = "获取计量单位换算信息详细信息")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(unitConversionService.selectUnitConversionById(id));
    }

    /**
     * 新增计量单位换算信息
     */
    @PreAuthorize("@ss.hasAnyRoles('libraryAdmin,admin')") //@PreAuthorize("@ss.hasPermi('userApplication:conversion:add')")
    @Log(title = "计量单位换算信息", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation(value ="新增计量单位换算信息",notes = "新增计量单位换算信息")
    public AjaxResult add(@RequestBody UnitConversion unitConversion) {
        unitConversion.setCreateTime(new Date());
        unitConversion.setCreatePersonal(getUserId());
        unitConversion.setId(unitConversion.getSnowID());
        return toAjax(unitConversionService.insertUnitConversion(unitConversion));
    }

    /**
     * 修改计量单位换算信息
     */
    @PreAuthorize("@ss.hasAnyRoles('libraryAdmin,admin')") //@PreAuthorize("@ss.hasPermi('userApplication:conversion:edit')")
    @Log(title = "计量单位换算信息", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation(value ="修改计量单位换算信息",notes = "修改计量单位换算信息")
    public AjaxResult edit(@RequestBody UnitConversion unitConversion) {
        unitConversion.setUpdateTime(new Date());
        unitConversion.setUpdatePersonal(getUserId());
        return toAjax(unitConversionService.updateUnitConversion(unitConversion));
    }

    /**
     * 删除计量单位换算信息
     */
    @PreAuthorize("@ss.hasAnyRoles('libraryAdmin,admin')") //@PreAuthorize("@ss.hasPermi('userApplication:conversion:remove')")
    @Log(title = "计量单位换算信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    @ApiOperation(value ="删除计量单位换算信息",notes = "删除计量单位换算信息")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(unitConversionService.deleteUnitConversionByIds(ids));
    }
}
