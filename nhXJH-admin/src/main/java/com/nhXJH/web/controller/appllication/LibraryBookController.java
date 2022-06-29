package com.nhXJH.web.controller.appllication;

import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.nhXJH.common.core.domain.entity.SysUser;
import com.nhXJH.common.core.domain.model.LoginUser;
import com.nhXJH.system.service.ISysUserService;
import com.nhXJH.web.domain.BaseFile;
import com.nhXJH.web.domain.LibraryBook;
import com.nhXJH.web.domain.dto.LibraryBookDto;
import com.nhXJH.web.domain.dto.SearchBookDto;
import com.nhXJH.web.domain.param.BookFileParam;
import com.nhXJH.web.domain.vo.BookVO;
import com.nhXJH.web.service.ILibraryBookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.aspectj.weaver.loadtime.Aj;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.nhXJH.common.annotation.Log;
import com.nhXJH.common.core.controller.BaseController;
import com.nhXJH.common.core.domain.AjaxResult;
import com.nhXJH.common.enums.BusinessType;
import com.nhXJH.common.utils.poi.ExcelUtil;
import com.nhXJH.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 图书实体信息Controller
 * 
 * @author xjh
 * @date 2022-02-07
 */
@RestController
@RequestMapping("/userApplication/book")
@Api(tags = {"图书实体信息"})
public class LibraryBookController extends BaseController {
    @Autowired
    private ILibraryBookService libraryBookService;
    @Autowired
    private ISysUserService userService;

    /**
     * 查询图书实体信息列表
     */
    @PreAuthorize("@ss.hasPermi('userApplication:book:list')")
    @GetMapping("/list")
    @ApiOperation(value ="查询图书实体信息列表",notes = "查询图书实体信息列表")
    public TableDataInfo list(LibraryBook libraryBook) {
        startPage();
        List<LibraryBookDto> list = libraryBookService.selectLibraryBookDtoList(libraryBook);//selectLibraryBookList
        return getDataTable(list);
    }

    /**
     * 图书搜索
     */
    @PostMapping("/getBook")
    @ApiOperation(value ="查询图书实体信息列表",notes = "查询图书实体信息列表")
    public TableDataInfo getBook( @RequestBody SearchBookDto dto) {
        return libraryBookService.getBook(dto);
    }

    /**
     * 导出图书实体信息列表
     */
    @PreAuthorize("@ss.hasPermi('userApplication:book:export')")
    @Log(title = "图书实体信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ApiOperation(value ="导出图书实体信息列表",notes = "导出图书实体信息列表")
    public void export(HttpServletResponse response, LibraryBook libraryBook) {
        List<LibraryBook> list = libraryBookService.selectLibraryBookList(libraryBook);
        ExcelUtil<LibraryBook> util = new ExcelUtil<LibraryBook>(LibraryBook.class);
        util.exportExcel(response, list, "图书实体信息数据");
    }

    /**
     * 获取图书实体信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('userApplication:book:query')")
    @GetMapping(value = "/{id}")
    @ApiOperation(value ="获取图书实体信息详细信息",notes = "获取图书实体信息详细信息")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(libraryBookService.selectLibraryBookById(id));
    }

    /**
     * 获取图书实体信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('userApplication:book:query')")
    @GetMapping(value = "/detail/{id}")
    @ApiOperation(value ="获取图书实体信息详细信息",notes = "获取图书实体信息详细信息")
    public AjaxResult detail(@PathVariable("id") Long id) {
        return AjaxResult.success(libraryBookService.selectLibraryBookDetailById(id));
    }

    /**
     * 新增图书实体信息
     */
    @PreAuthorize("@ss.hasPermi('userApplication:book:add')")
    @Log(title = "图书实体信息", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation(value ="新增图书实体信息",notes = "新增图书实体信息")
    public AjaxResult add(@RequestBody LibraryBook libraryBook) {
        libraryBook.setCreatePersonal(getUserId());
        libraryBook.setCreateTime(new Date());
        libraryBook.setId(libraryBook.getSnowID());
        return toAjax(libraryBookService.insertLibraryBook(libraryBook));
    }

    /**
     * 修改图书实体信息
     */
    @PreAuthorize("@ss.hasPermi('userApplication:book:edit')")
    @Log(title = "图书实体信息", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation(value ="修改图书实体信息",notes = "修改图书实体信息")
    public AjaxResult edit(@RequestBody LibraryBook libraryBook) {
        libraryBook.setUpdatePersonal(getUserId());
        libraryBook.setUpdateTime(new Date());
        return toAjax(libraryBookService.updateLibraryBook(libraryBook));
    }

    /**
     * 删除图书实体信息
     */
    @PreAuthorize("@ss.hasPermi('userApplication:book:remove')")
    @Log(title = "图书实体信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    @ApiOperation(value ="删除图书实体信息",notes = "删除图书实体信息")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(libraryBookService.deleteLibraryBookByIds(ids,getUserId()));
    }

    /**
     * 修改图书实体信息状态
     */
    @PreAuthorize("@ss.hasPermi('userApplication:book:modify')")
    @Log(title = "修改图书实体信息状态", businessType = BusinessType.UPDATE)
    @PutMapping("/changeStatus")
    @ApiOperation(value ="修改图书实体信息状态",notes = "修改图书实体信息状态")
    public AjaxResult changeStatus(@RequestBody LibraryBook book){
//        userService.checkUserAllowed(getLoginUser().getUser());
        book.setUpdateBy(getUsername());
        book.setUpdateTime(new Date());
        return toAjax(libraryBookService.updateUserStatus(book));
    }


    /**
     * 修改图书实体信息状态
     */
    @PreAuthorize("@ss.hasPermi('userApplication:book:modify')")
    @Log(title = "修改图书实体信息状态", businessType = BusinessType.UPDATE)
    @PostMapping("/upload")
    @ApiOperation(value ="修改图书实体信息状态",notes = "修改图书实体信息状态")
    public AjaxResult uploadFile(@RequestBody BookFileParam fileParam){
        fileParam.setUpdatePersonal(getUserId());
        return AjaxResult.success(libraryBookService.uploadFile(fileParam));
    }

}
