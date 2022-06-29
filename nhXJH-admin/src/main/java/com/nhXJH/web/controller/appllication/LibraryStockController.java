package com.nhXJH.web.controller.appllication;

import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.nhXJH.web.domain.LibraryStock;
import com.nhXJH.web.domain.param.LibraryStockMoreParam;
import com.nhXJH.web.domain.vo.StockVO;
import com.nhXJH.web.service.ILibraryStockService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.nhXJH.common.annotation.Log;
import com.nhXJH.common.core.controller.BaseController;
import com.nhXJH.common.core.domain.AjaxResult;
import com.nhXJH.common.enums.BusinessType;
import com.nhXJH.common.utils.poi.ExcelUtil;
import com.nhXJH.common.core.page.TableDataInfo;

/**
 * 库位信息Controller
 * 
 * @author xjh
 * @date 2022-01-25
 */
@RestController
@RequestMapping("/userApplication/stock")
@Api(tags = {"库位信息"})
public class LibraryStockController extends BaseController {
    @Autowired
    private ILibraryStockService libraryStockService;

    /**
     * 查询库位信息列表
     */
    @PreAuthorize("@ss.hasAnyRoles('libraryAdmin,admin')") //@PreAuthorize("@ss.hasPermi('userApplication:stock:list')")
    @GetMapping("/list")
    @ApiOperation(value ="查询库位信息列表",notes = "查询库位信息列表")
    public TableDataInfo list(LibraryStock libraryStock) {
        startPage();
        List<LibraryStock> list = libraryStockService.selectLibraryStockList(libraryStock);
        return getDataTable(list);
    }

    /**
     * 查询库位详细信息
     */
    @PreAuthorize("@ss.hasAnyRoles('libraryAdmin,admin')") //@PreAuthorize("@ss.hasPermi('userApplication:stock:list')")
    @GetMapping("/detail/{id}")
    @ApiOperation(value ="查询库位详细信息",notes = "查询库位详细信息")
    public AjaxResult detail(@PathVariable("id")Long id) {
        StockVO vo = libraryStockService.selectLibraryStockVOById(id);
        return AjaxResult.success(vo);
    }

    /**
     * 导出库位信息列表
     */
    @PreAuthorize("@ss.hasPermi('userApplication:stock:export')")
    @Log(title = "库位信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ApiOperation(value ="导出库位信息列表",notes = "导出库位信息列表")
    public void export(HttpServletResponse response, LibraryStock libraryStock) {
        List<LibraryStock> list = libraryStockService.selectLibraryStockList(libraryStock);
        ExcelUtil<LibraryStock> util = new ExcelUtil<LibraryStock>(LibraryStock.class);
        util.exportExcel(response, list, "库位信息数据");
    }

    /**
     * 获取库位信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('userApplication:stock:query')")
    @GetMapping(value = "/{id}")
    @ApiOperation(value ="获取库位信息详细信息",notes = "获取库位信息详细信息")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(libraryStockService.selectLibraryStockById(id));
    }

    /**
     * 新增库位信息
     */
    @PreAuthorize("@ss.hasPermi('userApplication:stock:add')")
    @Log(title = "库位信息", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation(value ="新增库位信息",notes = "新增库位信息")
    public AjaxResult add(@RequestBody LibraryStock libraryStock) {
        libraryStock.setCreatePersonal(getUserId());
        libraryStock.setCreateTime(new Date());
        libraryStock.setId(libraryStock.getSnowID());
        return toAjax(libraryStockService.insertLibraryStock(libraryStock));
    }
    /**
     * 新增库位信息
     */
    @PreAuthorize("@ss.hasPermi('userApplication:stock:add')")
    @Log(title = "库位信息", businessType = BusinessType.INSERT)
    @PostMapping("/more")
    @ApiOperation(value ="新增库位信息",notes = "新增库位信息")
    public AjaxResult addMore(@RequestBody LibraryStockMoreParam libraryStock) {
        libraryStock.setCreatePersonal(getUserId());
        libraryStock.setCreateTime(new Date());
        libraryStock.setId(libraryStock.getSnowID());
        return toAjax(libraryStockService.addMore(libraryStock));
    }

    /**
     * 修改库位信息
     */
    @PreAuthorize("@ss.hasPermi('userApplication:stock:edit')")
    @Log(title = "库位信息", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation(value ="修改库位信息",notes = "修改库位信息")
    public AjaxResult edit(@RequestBody LibraryStock libraryStock) {
        libraryStock.setUpdatePersonal(getUserId());
        libraryStock.setUpdateTime(new Date());
        return toAjax(libraryStockService.updateLibraryStock(libraryStock));
    }

    /**
     * 删除库位信息
     */
    @PreAuthorize("@ss.hasPermi('userApplication:stock:remove')")
    @Log(title = "库位信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    @ApiOperation(value ="删除库位信息",notes = "删除库位信息")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(libraryStockService.deleteLibraryStockByIds(ids,getUserId()));
    }
}
