package com.nhXJH.web.controller.appllication;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.nhXJH.web.domain.LibraryAuth;
import com.nhXJH.web.domain.LibraryPress;
import com.nhXJH.web.domain.vo.AuthVO;
import com.nhXJH.web.domain.vo.PressVO;
import com.nhXJH.web.service.ILibraryPressService;
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
 * 出版社信息Controller
 * 
 * @author xjh
 * @date 2022-01-25
 */
@RestController
@RequestMapping("/userApplication/press")
@Api(tags = {"出版社信息"})
public class LibraryPressController extends BaseController {
    @Autowired
    private ILibraryPressService libraryPressService;

    /**
     * 查询出版社信息列表
     */
//    @PreAuthorize("@ss.hasPermi('userApplication:press:list')")
    @PreAuthorize("@ss.hasAnyRoles('libraryAdmin,admin')")
    @GetMapping("/list")
    @ApiOperation(value ="查询出版社信息列表",notes = "查询出版社信息列表")
    public TableDataInfo list(LibraryPress libraryPress) {
        startPage();
        List<PressVO> list = libraryPressService.selectLibraryPressList(libraryPress);
        TableDataInfo info = getDataTable(list);
        info.setTotal(libraryPressService.getCount(libraryPress));
        return info;
    }
    /**
     * 查询出版社信息列表
     */
//    @PreAuthorize("@ss.hasPermi('userApplication:press:list')")
    @PreAuthorize("@ss.hasAnyRoles('libraryAdmin,admin')")
    @GetMapping("/all")
    @ApiOperation(value ="查询出版社信息列表",notes = "查询出版社信息列表")
    public AjaxResult all(LibraryPress libraryPress) {
        List<PressVO> list = libraryPressService.selectLibraryPressList(libraryPress);
        return AjaxResult.success(list);
    }

    /**
     * 导出出版社信息列表
     */
//    @PreAuthorize("@ss.hasPermi('userApplication:press:export')")
    @PreAuthorize("@ss.hasAnyRoles('libraryAdmin,admin')")
    @Log(title = "出版社信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ApiOperation(value ="导出出版社信息列表",notes = "导出出版社信息列表")
    public void export(HttpServletResponse response, LibraryPress libraryPress) {
        List<PressVO> list = libraryPressService.selectLibraryPressList(libraryPress);
        List<LibraryPress> presses = new ArrayList<>();
        list.forEach(pressVO -> {
            presses.add(pressVO.getPress());
        });
        ExcelUtil<LibraryPress> util = new ExcelUtil<LibraryPress>(LibraryPress.class);
        util.exportExcel(response, presses, "出版社信息数据");
    }

    /**
     * 获取出版社信息详细信息
     */
//    @PreAuthorize("@ss.hasPermi('userApplication:press:query')")
    @PreAuthorize("@ss.hasAnyRoles('libraryAdmin,admin')")
    @GetMapping(value = "/{id}")
    @ApiOperation(value ="获取出版社信息详细信息",notes = "获取出版社信息详细信息")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(libraryPressService.selectLibraryPressById(id));
    }

    /**
     * 新增出版社信息
     */
//    @PreAuthorize("@ss.hasPermi('userApplication:press:add')")
    @PreAuthorize("@ss.hasAnyRoles('libraryAdmin,admin')")
    @Log(title = "出版社信息", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation(value ="新增出版社信息",notes = "新增出版社信息")
    public AjaxResult add(@RequestBody LibraryPress libraryPress) {
        libraryPress.setCreatePersonal(getUserId());
        libraryPress.setCreateTime(new Date());
        libraryPress.setId(libraryPress.getSnowID());
        return toAjax(libraryPressService.insertLibraryPress(libraryPress));
    }

    /**
     * 修改出版社信息
     */
    //@PreAuthorize("@ss.hasPermi('userApplication:press:edit')")
    @Log(title = "出版社信息", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation(value ="修改出版社信息",notes = "修改出版社信息")
    public AjaxResult edit(@RequestBody LibraryPress libraryPress) {
        libraryPress.setUpdatePersonal(getUserId());
        libraryPress.setUpdateTime(new Date());
        return toAjax(libraryPressService.updateLibraryPress(libraryPress));
    }

    /**
     * 删除出版社信息
     */
    @PreAuthorize("@ss.hasAnyRoles('libraryAdmin,admin')") //@PreAuthorize("@ss.hasPermi('userApplication:press:remove')")
    @Log(title = "出版社信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    @ApiOperation(value ="删除出版社信息",notes = "删除出版社信息")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(libraryPressService.deleteLibraryPressByIds(ids,getUserId()));
    }

    /**
     * 修改信息状态
     */
    @PreAuthorize("@ss.hasAnyRoles('libraryAdmin,admin')") //@PreAuthorize("@ss.hasPermi('userApplication:auth:modify')")
    @Log(title = "修改实体信息状态", businessType = BusinessType.UPDATE)
    @PutMapping("/changeStatus")
    @ApiOperation(value ="修改实体信息状态",notes = "修改实体信息状态")
    public AjaxResult changeStatus(@RequestBody LibraryPress press){
//        userService.checkUserAllowed();
        press.setUpdateBy(getUsername());
        press.setUpdatePersonal(getUserId());
        press.setUpdateTime(new Date());
        return toAjax(libraryPressService.updateUserStatus(press));
    }
}
