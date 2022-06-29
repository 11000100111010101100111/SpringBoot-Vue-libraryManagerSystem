package com.nhXJH.web.controller.appllication;

import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.nhXJH.common.utils.StringUtils;
import com.nhXJH.enums.DelEnums;
import com.nhXJH.enums.HasBrowedEnums;
import com.nhXJH.enums.StatusEnums;
import com.nhXJH.web.domain.BookStockBrowHistory;
import com.nhXJH.web.domain.vo.BookBrowHistoryVO;
import com.nhXJH.web.service.IBookStockBrowHistoryService;
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
 * 借书信息Controller
 * 
 * @author xjh
 * @date 2022-03-14
 */
@RestController
@RequestMapping("/userApplication/history")
@Api(tags = {"借书信息"})
public class BookStockBrowHistoryController extends BaseController {
    @Autowired
    private IBookStockBrowHistoryService bookStockBrowHistoryService;

    /**
     * 查询借书信息列表
     */
    @PreAuthorize("@ss.hasPermi('userApplication:history:list')")
    @GetMapping("/list")
    @ApiOperation(value ="查询借书信息列表",notes = "查询借书信息列表")
    public TableDataInfo list(BookStockBrowHistory bookStockBrowHistory) {
        startPage();
        List<BookStockBrowHistory> list = bookStockBrowHistoryService.selectBookStockBrowHistoryList(bookStockBrowHistory);
        return getDataTable(list);
    }

    /**
     * 查询借书信息列表
     */
    @PreAuthorize("@ss.hasPermi('userApplication:history:list')")
    @GetMapping("/mine/history")
    @ApiOperation(value ="查询借书信息列表",notes = "查询借书信息列表")
    public TableDataInfo getMyHistory(BookStockBrowHistory bookStockBrowHistory) {
        startPage();
        List<BookBrowHistoryVO> list = bookStockBrowHistoryService.getMyHistory(bookStockBrowHistory);
        TableDataInfo info = getDataTable(list);
        info.setTotal(StringUtils.isNull(list)?0:list.size());
        return info;
    }

    /**
     * 查询借书信息列表
     */
    @PreAuthorize("@ss.hasPermi('userApplication:history:list')")
    @GetMapping("/mine/back/history")
    @ApiOperation(value ="查询借书信息列表",notes = "查询借书信息列表")
    public AjaxResult getBackHistory(BookStockBrowHistory bookStockBrowHistory) {
        bookStockBrowHistory.setHasBack(HasBrowedEnums.BACKED.getDesc());
        bookStockBrowHistory.setUserId(getUserId());
        bookStockBrowHistory.setIsDel(DelEnums.NOT_DEL.getValue());
        bookStockBrowHistory.setStatus(StatusEnums.ONE.getDesc());
        List<BookBrowHistoryVO> list = bookStockBrowHistoryService.getMyHistory(bookStockBrowHistory);
        return AjaxResult.success(list);
    }
    /**
     * 查询借书信息列表
     */
    @PreAuthorize("@ss.hasPermi('userApplication:history:list')")
    @GetMapping("/mine/back/id")
    @ApiOperation(value ="查询借书信息列表",notes = "查询借书信息列表")
    public AjaxResult getBackHistoryById(Long id) {
        BookBrowHistoryVO list = bookStockBrowHistoryService.getMyHistoryById(id);
        return AjaxResult.success(list);
    }
    /**
     * 查询借书信息列表
     */
    @PreAuthorize("@ss.hasPermi('userApplication:history:list')")
    @GetMapping("/mine/back")
    @ApiOperation(value ="查询借书信息列表",notes = "查询借书信息列表")
    public AjaxResult back(BookStockBrowHistory bookStockBrowHistory) {
        bookStockBrowHistory.setUserId(getUserId());
        bookStockBrowHistory.setBackTime(new Date());
        bookStockBrowHistory.setUpdateTime(new Date());
        return toAjax(bookStockBrowHistoryService.back(bookStockBrowHistory));
    }

    /**
     * 导出借书信息列表
     */
    @PreAuthorize("@ss.hasPermi('userApplication:history:export')")
    @Log(title = "借书信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ApiOperation(value ="导出借书信息列表",notes = "导出借书信息列表")
    public void export(HttpServletResponse response, BookStockBrowHistory bookStockBrowHistory) {
        List<BookStockBrowHistory> list = bookStockBrowHistoryService.selectBookStockBrowHistoryList(bookStockBrowHistory);
        ExcelUtil<BookStockBrowHistory> util = new ExcelUtil<BookStockBrowHistory>(BookStockBrowHistory.class);
        util.exportExcel(response, list, "借书信息数据");
    }

    /**
     * 获取借书信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('userApplication:history:query')")
    @GetMapping(value = "/{id}")
    @ApiOperation(value ="获取借书信息详细信息",notes = "获取借书信息详细信息")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(bookStockBrowHistoryService.selectBookStockBrowHistoryById(id));
    }

    /**
     * 新增借书信息
     */
    @PreAuthorize("@ss.hasPermi('userApplication:history:add')")
    @Log(title = "借书信息", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation(value ="新增借书信息",notes = "新增借书信息")
    public AjaxResult add(@RequestBody BookStockBrowHistory bookStockBrowHistory) {
        bookStockBrowHistory.setCreatePersonal(getUserId());
        bookStockBrowHistory.setCreateTime(new Date());
        return toAjax(bookStockBrowHistoryService.insertBookStockBrowHistory(bookStockBrowHistory));
    }

    /**
     * 修改借书信息
     */
    @PreAuthorize("@ss.hasPermi('userApplication:history:edit')")
    @Log(title = "借书信息", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation(value ="修改借书信息",notes = "修改借书信息")
    public AjaxResult edit(@RequestBody BookStockBrowHistory bookStockBrowHistory) {
        bookStockBrowHistory.setUpdatePersonal(getUserId());
        bookStockBrowHistory.setUpdateTime(new Date());
        return toAjax(bookStockBrowHistoryService.updateBookStockBrowHistory(bookStockBrowHistory));
    }

    /**
     * 删除借书信息
     */
    @PreAuthorize("@ss.hasPermi('userApplication:history:remove')")
    @Log(title = "借书信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    @ApiOperation(value ="删除借书信息",notes = "删除借书信息")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(bookStockBrowHistoryService.deleteBookStockBrowHistoryByIds(ids,getUserId()));
    }
}
