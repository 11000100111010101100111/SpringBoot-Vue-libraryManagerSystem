package com.nhXJH.web.controller.appllication;

import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nhXJH.web.domain.BookStockLog;
import com.nhXJH.web.service.IBookStockLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
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
 * 图书库存出入信息Controller
 * 
 * @author xjh
 * @date 2022-01-25
 */
@RestController
@RequestMapping("/userApplication/log")
@Api(tags = {"图书库存出入信息"})
public class BookStockLogController extends BaseController {
    @Autowired
    private IBookStockLogService bookStockLogService;

    /**
     * 查询图书库存出入信息列表
     */
    @PreAuthorize("@ss.hasPermi('userApplication:log:list')")
    @GetMapping("/list")
    @ApiOperation(value ="查询图书库存出入信息列表",notes = "查询图书库存出入信息列表")
    public TableDataInfo list(BookStockLog bookStockLog) {
        startPage();
        List<BookStockLog> list = bookStockLogService.selectBookStockLogList(bookStockLog);
        return getDataTable(list);
    }
    /**
     * 查询图书库存出入记录
     */
    @PreAuthorize("@ss.hasPermi('userApplication:log:list')")
    @GetMapping("/getLog/{stockId}")
    @ApiOperation(value ="查询图书库存出入记录",notes = "查询图书库存出入记录")
    public AjaxResult getLogs(@PathVariable("stockId") Long stockId) {
        return AjaxResult.success(bookStockLogService.selectBookStockLogVOById(stockId));
    }
    /**
     * 导出图书库存出入信息列表
     */
    @PreAuthorize("@ss.hasPermi('userApplication:log:export')")
    @Log(title = "图书库存出入信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ApiOperation(value ="导出图书库存出入信息列表",notes = "导出图书库存出入信息列表")
    public void export(HttpServletResponse response, BookStockLog bookStockLog) {
        List<BookStockLog> list = bookStockLogService.selectBookStockLogList(bookStockLog);
        ExcelUtil<BookStockLog> util = new ExcelUtil<BookStockLog>(BookStockLog.class);
        util.exportExcel(response, list, "图书库存出入信息数据");
    }

    /**
     * 获取图书库存出入信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('userApplication:log:query')")
    @GetMapping(value = "/{id}")
    @ApiOperation(value ="获取图书库存出入信息详细信息",notes = "获取图书库存出入信息详细信息")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(bookStockLogService.selectBookStockLogById(id));
    }

    /**
     * 新增图书库存出入信息
     */
    @PreAuthorize("@ss.hasPermi('userApplication:log:add')")
    @Log(title = "图书库存出入信息", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation(value ="新增图书库存出入信息",notes = "新增图书库存出入信息")
    public AjaxResult add(@RequestBody BookStockLog bookStockLog) {
        bookStockLog.setCreatePersonal(getUserId());
        bookStockLog.setCreateTime(new Date());
        bookStockLog.setId(bookStockLog.getSnowID());
        return toAjax(bookStockLogService.insertBookStockLog(bookStockLog));
    }

    /**
     * 修改图书库存出入信息
     */
    @PreAuthorize("@ss.hasPermi('userApplication:log:edit')")
    @Log(title = "图书库存出入信息", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation(value ="修改图书库存出入信息",notes = "修改图书库存出入信息")
    public AjaxResult edit(@RequestBody BookStockLog bookStockLog) {
        bookStockLog.setUpdatePersonal(getUserId());
        bookStockLog.setUpdateTime(new Date());
        return toAjax(bookStockLogService.updateBookStockLog(bookStockLog));
    }

    /**
     * 删除图书库存出入信息
     */
    @PreAuthorize("@ss.hasPermi('userApplication:log:remove')")
    @Log(title = "图书库存出入信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    @ApiOperation(value ="删除图书库存出入信息",notes = "删除图书库存出入信息")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(bookStockLogService.deleteBookStockLogByIds(ids));
    }

}
