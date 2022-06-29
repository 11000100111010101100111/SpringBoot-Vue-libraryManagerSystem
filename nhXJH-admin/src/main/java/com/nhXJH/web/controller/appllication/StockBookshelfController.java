package com.nhXJH.web.controller.appllication;

import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.nhXJH.common.enums.application.StatusEnums;
import com.nhXJH.web.domain.LibraryBook;
import com.nhXJH.web.domain.StockBookshelf;
import com.nhXJH.web.domain.vo.BookShelfVO;
import com.nhXJH.web.service.IStockBookshelfService;
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
 * 书架信息Controller
 * 
 * @author xjh
 * @date 2022-01-25
 */
@RestController
@RequestMapping("/userApplication/bookshelf")
@Api(tags = {"书架信息"})
public class StockBookshelfController extends BaseController {
    @Autowired
    private IStockBookshelfService stockBookshelfService;

    /**
     * 查询书架信息列表
     */
    @PreAuthorize("@ss.hasAnyRoles('libraryAdmin,admin')") //@PreAuthorize("@ss.hasPermi('userApplication:bookshelf:list')")
    @GetMapping("/list")
    @ApiOperation(value ="查询书架信息列表",notes = "查询书架信息列表")
    public TableDataInfo list(StockBookshelf stockBookshelf) {
        Long count = stockBookshelfService.getCount(stockBookshelf);
        count = count>0?count:0l;
        startPage();
//        List<StockBookshelf> list = stockBookshelfService.selectStockBookshelfList(stockBookshelf);
        List<BookShelfVO> list = stockBookshelfService.selectStockBookshelfVOList(stockBookshelf);
        TableDataInfo info = getDataTable(list);
        info.setTotal(Math.toIntExact(count));
        return info;
    }

    /**
     * 导出书架信息列表
     */
    @PreAuthorize("@ss.hasAnyRoles('libraryAdmin,admin')") //@PreAuthorize("@ss.hasPermi('userApplication:bookshelf:export')")
    @Log(title = "书架信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ApiOperation(value ="导出书架信息列表",notes = "导出书架信息列表")
    public void export(HttpServletResponse response, StockBookshelf stockBookshelf) {
        List<StockBookshelf> list = stockBookshelfService.selectStockBookshelfList(stockBookshelf);
        ExcelUtil<StockBookshelf> util = new ExcelUtil<StockBookshelf>(StockBookshelf.class);
        util.exportExcel(response, list, "书架信息数据");
    }

    /**
     * 获取书架信息详细信息
     */
    @PreAuthorize("@ss.hasAnyRoles('libraryAdmin,admin')") //@PreAuthorize("@ss.hasPermi('userApplication:bookshelf:query')")
    @GetMapping(value = "/{id}")
    @ApiOperation(value ="获取书架信息详细信息",notes = "获取书架信息详细信息")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(stockBookshelfService.selectStockBookshelfVOById(id));
    }

    /**
     * 新增书架信息
     */
    @PreAuthorize("@ss.hasAnyRoles('libraryAdmin,admin')") //@PreAuthorize("@ss.hasPermi('userApplication:bookshelf:add')")
    @Log(title = "书架信息", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation(value ="新增书架信息",notes = "新增书架信息")
    public AjaxResult add(@RequestBody StockBookshelf stockBookshelf) {
        stockBookshelf.setCreatePersonal(getUserId());
        stockBookshelf.setCreateTime(new Date());
        stockBookshelf.setStatus(StatusEnums.ONE.getDesc());
        stockBookshelf.setId(stockBookshelf.getSnowID());
        return toAjax(stockBookshelfService.insertStockBookshelf(stockBookshelf));
    }

    /**
     * 修改书架信息
     */
    @PreAuthorize("@ss.hasAnyRoles('libraryAdmin,admin')") //@PreAuthorize("@ss.hasPermi('userApplication:bookshelf:edit')")
    @Log(title = "书架信息", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation(value ="修改书架信息",notes = "修改书架信息")
    public AjaxResult edit(@RequestBody StockBookshelf stockBookshelf) {
        stockBookshelf.setUpdatePersonal(getUserId());
        stockBookshelf.setUpdateTime(new Date());
        return toAjax(stockBookshelfService.updateStockBookshelf(stockBookshelf));
    }

    /**
     * 删除书架信息
     */
    @PreAuthorize("@ss.hasAnyRoles('libraryAdmin,admin')") //@PreAuthorize("@ss.hasPermi('userApplication:bookshelf:remove')")
    @Log(title = "书架信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    @ApiOperation(value ="删除书架信息",notes = "删除书架信息")
    public AjaxResult remove(@PathVariable Long[] ids) {
        List<StockBookshelf> list = stockBookshelfService.deleteStockBookshelfByIds(ids);
        return null == list?AjaxResult.success("删除成功"):AjaxResult.error("部分书架已有图书入库信息，删除失败",list);
    }

    /**
     * 修改书架信息状态
     */
    @PreAuthorize("@ss.hasAnyRoles('libraryAdmin,admin')") //@PreAuthorize("@ss.hasPermi('userApplication:book:modify')")
    @Log(title = "修改书架信息状态", businessType = BusinessType.UPDATE)
    @PutMapping("/changeStatus")
    @ApiOperation(value ="修改书架信息状态",notes = "修改书架信息状态")
    public AjaxResult changeStatus(@RequestBody StockBookshelf bookshelf){
//        userService.checkUserAllowed();
        bookshelf.setUpdateBy(getUsername());
        bookshelf.setUpdatePersonal(getUserId());
        bookshelf.setUpdateTime(new Date());
        return toAjax(stockBookshelfService.updateUserStatus(bookshelf));
    }
}
