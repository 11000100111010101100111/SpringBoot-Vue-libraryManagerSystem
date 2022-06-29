package com.nhXJH.web.controller.appllication;

import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.nhXJH.web.domain.FileToken;
import com.nhXJH.web.service.IFileTokenService;
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
 * 附件访问秘钥信息Controller
 * 
 * @author xjh
 * @date 2022-01-25
 */
@RestController
@RequestMapping("/userApplication/token")
@Api(tags = {"附件访问秘钥信息"})
public class FileTokenController extends BaseController {
    @Autowired
    private IFileTokenService fileTokenService;

    /**
     * 查询附件访问秘钥信息列表
     */
    @PreAuthorize("@ss.hasAnyRoles('libraryAdmin,admin')") //@PreAuthorize("@ss.hasPermi('userApplication:token:list')")
    @GetMapping("/list")
    @ApiOperation(value ="查询附件访问秘钥信息列表",notes = "查询附件访问秘钥信息列表")
    public TableDataInfo list(FileToken fileToken) {
        startPage();
        List<FileToken> list = fileTokenService.selectFileTokenList(fileToken);
        return getDataTable(list);
    }

    /**
     * 导出附件访问秘钥信息列表
     */
    @PreAuthorize("@ss.hasAnyRoles('libraryAdmin,admin')") //@PreAuthorize("@ss.hasPermi('userApplication:token:export')")
    @Log(title = "附件访问秘钥信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ApiOperation(value ="导出附件访问秘钥信息列表",notes = "导出附件访问秘钥信息列表")
    public void export(HttpServletResponse response, FileToken fileToken) {
        List<FileToken> list = fileTokenService.selectFileTokenList(fileToken);
        ExcelUtil<FileToken> util = new ExcelUtil<FileToken>(FileToken.class);
        util.exportExcel(response, list, "附件访问秘钥信息数据");
    }

    /**
     * 获取附件访问秘钥信息详细信息
     */
    @PreAuthorize("@ss.hasAnyRoles('libraryAdmin,admin')") //@PreAuthorize("@ss.hasPermi('userApplication:token:query')")
    @GetMapping(value = "/{id}")
    @ApiOperation(value ="获取附件访问秘钥信息详细信息",notes = "获取附件访问秘钥信息详细信息")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(fileTokenService.selectFileTokenById(id));
    }

    /**
     * 新增附件访问秘钥信息
     */
    @PreAuthorize("@ss.hasAnyRoles('libraryAdmin,admin')") //@PreAuthorize("@ss.hasPermi('userApplication:token:add')")
    @Log(title = "附件访问秘钥信息", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation(value ="新增附件访问秘钥信息",notes = "新增附件访问秘钥信息")
    public AjaxResult add(@RequestBody FileToken fileToken) {
        fileToken.setCreatePeraon(getUserId());
        fileToken.setCreateTime(new Date());
        fileToken.setId(fileToken.getSnowID());
        return toAjax(fileTokenService.insertFileToken(fileToken));
    }

    /**
     * 修改附件访问秘钥信息
     */
    @PreAuthorize("@ss.hasAnyRoles('libraryAdmin,admin')") //@PreAuthorize("@ss.hasPermi('userApplication:token:edit')")
    @Log(title = "附件访问秘钥信息", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation(value ="修改附件访问秘钥信息",notes = "修改附件访问秘钥信息")
    public AjaxResult edit(@RequestBody FileToken fileToken) {
        fileToken.setUpdatePersonal(getUserId());
        fileToken.setUpdateTime(new Date());
        return toAjax(fileTokenService.updateFileToken(fileToken));
    }

    /**
     * 删除附件访问秘钥信息
     */
    @PreAuthorize("@ss.hasAnyRoles('libraryAdmin,admin')") //@PreAuthorize("@ss.hasPermi('userApplication:token:remove')")
    @Log(title = "附件访问秘钥信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    @ApiOperation(value ="删除附件访问秘钥信息",notes = "删除附件访问秘钥信息")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(fileTokenService.deleteFileTokenByIds(ids));
    }
}
