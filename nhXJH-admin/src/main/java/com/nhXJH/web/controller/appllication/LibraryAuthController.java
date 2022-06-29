package com.nhXJH.web.controller.appllication;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONArray;
import com.nhXJH.common.enums.application.StatusEnums;
import com.nhXJH.common.enums.application.TableTypeEnums;
import com.nhXJH.web.domain.AttachFile;
import com.nhXJH.web.domain.BaseFile;
import com.nhXJH.web.domain.LibraryAuth;
import com.nhXJH.web.domain.LibraryBook;
import com.nhXJH.web.domain.entity.SysFile;
import com.nhXJH.web.domain.param.AuthParam;
import com.nhXJH.web.domain.vo.AuthVO;
import com.nhXJH.web.domain.vo.FileVo;
import com.nhXJH.web.service.IBaseFileService;
import com.nhXJH.web.service.ILibraryAuthService;
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
 * 作信息者Controller
 * 
 * @author xjh
 * @date 2022-01-25
 */
@RestController
@RequestMapping("/userApplication/auth")
@Api(tags = {"作信息者"})
public class LibraryAuthController extends BaseController {
    @Autowired
    private ILibraryAuthService libraryAuthService;

    @Autowired
    private IBaseFileService baseFileService;

    /**
     * 查询作信息者列表
     */
    @PreAuthorize("@ss.hasAnyRoles('libraryAdmin,admin')") //@PreAuthorize("@ss.hasPermi('userApplication:auth:list')")
    @GetMapping("/list")
    @ApiOperation(value ="查询作信息者列表",notes = "查询作信息者列表")
    public TableDataInfo list(LibraryAuth libraryAuth) {
        startPage();
        List<AuthVO> list = libraryAuthService.selectLibraryAuthList(libraryAuth);
        TableDataInfo info =  getDataTable(list);
        info.setTotal(libraryAuthService.getCount(libraryAuth));
        return info;
    }

    /**
     * 查询作信息者列表
     */
    @PreAuthorize("@ss.hasAnyRoles('libraryAdmin,admin')") //@PreAuthorize("@ss.hasPermi('userApplication:auth:list')")
    @GetMapping("/all")
    @ApiOperation(value ="查询作信息者列表",notes = "查询作信息者列表")
    public AjaxResult all(LibraryAuth libraryAuth) {
        List<AuthVO> list = libraryAuthService.selectLibraryAuthList(libraryAuth);
        return AjaxResult.success(list);
    }

    /**
     * 导出作信息者列表
     */
    @PreAuthorize("@ss.hasAnyRoles('libraryAdmin,admin')") //@PreAuthorize("@ss.hasPermi('userApplication:auth:export')")
    @Log(title = "作信息者", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ApiOperation(value ="导出作信息者列表",notes = "导出作信息者列表")
    public void export(HttpServletResponse response, LibraryAuth libraryAuth) {
        List<AuthVO> list = libraryAuthService.selectLibraryAuthList(libraryAuth);
        List<LibraryAuth> auths = new ArrayList<>();
        list.forEach(authVO -> {
            auths.add(authVO.getAuth());
        });
        ExcelUtil<LibraryAuth> util = new ExcelUtil<LibraryAuth>(LibraryAuth.class);
        util.exportExcel(response, auths, "作信息者数据");
    }

    /**
     * 获取作信息者详细信息
     */
    @PreAuthorize("@ss.hasAnyRoles('libraryAdmin,admin')") //@PreAuthorize("@ss.hasPermi('userApplication:auth:query')")
    @GetMapping(value = "/{id}")
    @ApiOperation(value ="获取作信息者详细信息",notes = "获取作信息者详细信息")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(libraryAuthService.selectLibraryAuthById(id));
    }

    /**
     * 新增作信息者
     */
    @PreAuthorize("@ss.hasAnyRoles('libraryAdmin,admin')") //@PreAuthorize("@ss.hasPermi('userApplication:auth:add')")
    @Log(title = "作信息者", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation(value ="新增作信息者",notes = "新增作信息者")
    public AjaxResult add(@RequestBody AuthParam libraryAuth) {
        libraryAuth.setCreatePersonal(getUserId());
        libraryAuth.setStatus(StatusEnums.ONE.getDesc());
        libraryAuth.setId(libraryAuth.getSnowID());
        return AjaxResult.success(libraryAuthService.insertLibraryAuth(libraryAuth));
    }

    /**
     * 修改作信息者
     */
    @PreAuthorize("@ss.hasAnyRoles('libraryAdmin,admin')") //@PreAuthorize("@ss.hasPermi('userApplication:auth:edit')")
    @Log(title = "作信息者", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation(value ="修改作信息者",notes = "修改作信息者")
    public AjaxResult edit(@RequestBody AuthParam libraryAuth) {
        libraryAuth.setUpdatePersonal(getUserId());
        libraryAuth.setUpdateTime(new Date());
        return AjaxResult.success(libraryAuthService.updateLibraryAuth(libraryAuth));
    }

    /**
     * 删除作信息者
     */
    @PreAuthorize("@ss.hasAnyRoles('libraryAdmin,admin')") //@PreAuthorize("@ss.hasPermi('userApplication:auth:remove')")
    @Log(title = "作信息者", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    @ApiOperation(value ="删除作信息者",notes = "删除作信息者")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(libraryAuthService.deleteLibraryAuthByIds(ids,getUserId()));
    }

    /**
     * 修改信息状态
     */
    @PreAuthorize("@ss.hasAnyRoles('libraryAdmin,admin')") //@PreAuthorize("@ss.hasPermi('userApplication:auth:modify')")
    @Log(title = "修改实体信息状态", businessType = BusinessType.UPDATE)
    @PutMapping("/changeStatus")
    @ApiOperation(value ="修改实体信息状态",notes = "修改实体信息状态")
    public AjaxResult changeStatus(@RequestBody LibraryAuth auth){
//        userService.checkUserAllowed();
        auth.setUpdateBy(getUsername());
        auth.setUpdatePersonal(getUserId());
        return toAjax(libraryAuthService.updateUserStatus(auth));
    }

//    /**
//     * 获取当前作者对应图片信息
//     */
//    @PreAuthorize("@ss.hasAnyRoles('libraryAdmin,admin')") //@PreAuthorize("@ss.hasPermi('userApplication:auth:modify')")
//    @Log(title = "获取当前作者对应图片信息", businessType = BusinessType.OTHER)
//    @PutMapping("/changeStatus")
//    @ApiOperation(value ="获取当前作者对应图片信息",notes = "获取当前作者对应图片信息")
//    public AjaxResult getPicList(@RequestBody LibraryAuth auth){
////        userService.checkUserAllowed();
//        auth.setUpdateBy(getUsername());
//        //@TODO 获取当前作者对应图片信息
//        return toAjax(libraryAuthService.getPicList(auth));
//    }
//
//    /**
//     * 设置当前作者对应图片信息
//     */
//    @PreAuthorize("@ss.hasAnyRoles('libraryAdmin,admin')") //@PreAuthorize("@ss.hasPermi('userApplication:auth:modify')")
//    @Log(title = "设置当前作者对应图片信息", businessType = BusinessType.UPDATE)
//    @PutMapping("/changeStatus")
//    @ApiOperation(value ="设置当前作者对应图片信息",notes = "设置当前作者对应图片信息")
//    public AjaxResult setPicList(@RequestBody LibraryAuth auth){
////        userService.checkUserAllowed();
//
//        //@TODO 设置当前作者对应图片信息
//        auth.setUpdateBy(getUsername());
//        return toAjax(libraryAuthService.setPicList(auth));
//    }

    /**
     * 查询附件实体对应信息列表
     */
    @PreAuthorize("@ss.hasAnyRoles('libraryAdmin,admin')") //@PreAuthorize("@ss.hasPermi('userApplication:file:list')")
    @Log(title = "查询附件实体对应信息列表", businessType = BusinessType.OTHER)
    @GetMapping("/getFile/{id}")
    @ApiOperation(value ="查询附件实体对应信息列表",notes = "查询附件实体对应信息列表")
    public AjaxResult getFile(@PathVariable("id") Long id) {
        BaseFile baseFile = new BaseFile();
        baseFile.setModelId(id);
        baseFile.setDbId(TableTypeEnums.AUTH.getCode());
        List<FileVo> list = baseFileService.getFile(baseFile);
        List<String> fileSrc = new ArrayList<>();
        list.forEach(src->{fileSrc.add(src.getUrl());});
        return AjaxResult.success(fileSrc);
    }
}
