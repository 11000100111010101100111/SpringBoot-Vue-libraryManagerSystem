package com.nhXJH.web.controller.appllication;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.nhXJH.web.domain.BaseCountry;
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
import com.nhXJH.web.service.IBaseCountryService;
import com.nhXJH.common.utils.poi.ExcelUtil;
import com.nhXJH.common.core.page.TableDataInfo;

/**
 * 国家信息Controller
 * 
 * @author xjh
 * @date 2022-03-01
 */
@RestController
@RequestMapping("/userApplication/country")
@Api(tags = {"国家信息"})
public class BaseCountryController extends BaseController {
    @Autowired
    private IBaseCountryService baseCountryService;

    /**
     * 查询国家信息列表
     */
    @PreAuthorize("@ss.hasPermi('userApplication:country:list')")
    @GetMapping("/list")
    @ApiOperation(value ="查询国家信息列表",notes = "查询国家信息列表")
    public TableDataInfo list(BaseCountry baseCountry) {
        startPage();
        List<BaseCountry> list = baseCountryService.selectBaseCountryList(baseCountry);
        return getDataTable(list);
    }

    /**
     * 导出国家信息列表
     */
    @PreAuthorize("@ss.hasPermi('userApplication:country:export')")
    @Log(title = "国家信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ApiOperation(value ="导出国家信息列表",notes = "导出国家信息列表")
    public void export(HttpServletResponse response, BaseCountry baseCountry) {
        List<BaseCountry> list = baseCountryService.selectBaseCountryList(baseCountry);
        ExcelUtil<BaseCountry> util = new ExcelUtil<BaseCountry>(BaseCountry.class);
        util.exportExcel(response, list, "国家信息数据");
    }

    /**
     * 获取国家信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('userApplication:country:query')")
    @GetMapping(value = "/{id}")
    @ApiOperation(value ="获取国家信息详细信息",notes = "获取国家信息详细信息")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(baseCountryService.selectBaseCountryById(id));
    }

    /**
     * 新增国家信息
     */
    @PreAuthorize("@ss.hasPermi('userApplication:country:add')")
    @Log(title = "国家信息", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation(value ="新增国家信息",notes = "新增国家信息")
    public AjaxResult add(@RequestBody BaseCountry baseCountry) {
        return toAjax(baseCountryService.insertBaseCountry(baseCountry));
    }

    /**
     * 修改国家信息
     */
    @PreAuthorize("@ss.hasPermi('userApplication:country:edit')")
    @Log(title = "国家信息", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation(value ="修改国家信息",notes = "修改国家信息")
    public AjaxResult edit(@RequestBody BaseCountry baseCountry) {
        return toAjax(baseCountryService.updateBaseCountry(baseCountry));
    }

    /**
     * 删除国家信息
     */
    @PreAuthorize("@ss.hasPermi('userApplication:country:remove')")
    @Log(title = "国家信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    @ApiOperation(value ="删除国家信息",notes = "删除国家信息")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(baseCountryService.deleteBaseCountryByIds(ids));
    }
}
