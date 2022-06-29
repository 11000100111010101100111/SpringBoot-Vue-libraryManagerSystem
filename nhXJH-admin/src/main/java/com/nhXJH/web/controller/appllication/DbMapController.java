package com.nhXJH.web.controller.appllication;

import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.nhXJH.web.domain.DbMap;
import com.nhXJH.web.service.IDbMapService;
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
 * 表信息映射Controller
 * 
 * @author xjh
 * @date 2022-01-25
 */
@RestController
@RequestMapping("/userApplication/map")
@Api(tags = {"表信息映射"})
public class DbMapController extends BaseController {
    @Autowired
    private IDbMapService dbMapService;

    /**
     * 查询表信息映射列表
     */
    @PreAuthorize("@ss.hasPermi('userApplication:map:list')")
    @GetMapping("/list")
    @ApiOperation(value ="查询表信息映射列表",notes = "查询表信息映射列表")
    public TableDataInfo list(DbMap dbMap) {
        startPage();
        List<DbMap> list = dbMapService.selectDbMapList(dbMap);
        return getDataTable(list);
    }

    /**
     * 导出表信息映射列表
     */
    @PreAuthorize("@ss.hasPermi('userApplication:map:export')")
    @Log(title = "表信息映射", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ApiOperation(value ="导出表信息映射列表",notes = "导出表信息映射列表")
    public void export(HttpServletResponse response, DbMap dbMap) {
        List<DbMap> list = dbMapService.selectDbMapList(dbMap);
        ExcelUtil<DbMap> util = new ExcelUtil<DbMap>(DbMap.class);
        util.exportExcel(response, list, "表信息映射数据");
    }

    /**
     * 获取表信息映射详细信息
     */
    @PreAuthorize("@ss.hasPermi('userApplication:map:query')")
    @GetMapping(value = "/{id}")
    @ApiOperation(value ="获取表信息映射详细信息",notes = "获取表信息映射详细信息")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(dbMapService.selectDbMapById(id));
    }

    /**
     * 新增表信息映射
     */
    @PreAuthorize("@ss.hasPermi('userApplication:map:add')")
    @Log(title = "表信息映射", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation(value ="新增表信息映射",notes = "新增表信息映射")
    public AjaxResult add(@RequestBody DbMap dbMap) {
        dbMap.setCreatePerson(getUserId());
        dbMap.setCreateTime(new Date());
        dbMap.setId(dbMap.getSnowID());
        return toAjax(dbMapService.insertDbMap(dbMap));
    }

    /**
     * 修改表信息映射
     */
    @PreAuthorize("@ss.hasPermi('userApplication:map:edit')")
    @Log(title = "表信息映射", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation(value ="修改表信息映射",notes = "修改表信息映射")
    public AjaxResult edit(@RequestBody DbMap dbMap) {
        dbMap.setUpdatePerson(getUserId());
        dbMap.setUpdateTime(new Date());
        return toAjax(dbMapService.updateDbMap(dbMap));
    }

    /**
     * 删除表信息映射
     */
    @PreAuthorize("@ss.hasPermi('userApplication:map:remove')")
    @Log(title = "表信息映射", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    @ApiOperation(value ="删除表信息映射",notes = "删除表信息映射")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(dbMapService.deleteDbMapByIds(ids));
    }
}
