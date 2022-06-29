package com.nhXJH.web.controller.appllication;

import java.io.File;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.nhXJH.web.domain.PurchaseTemplate;
import com.nhXJH.web.domain.param.purchase.TemplateParam;
import com.nhXJH.web.service.IPurchaseTemplateService;
import com.nhXJH.web.util.TemplateUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
import org.springframework.web.multipart.MultipartFile;

/**
 * 审批流程模板Controller
 * 
 * @author xjh
 * @date 2022-01-25
 */
@RestController
@RequestMapping("/userApplication/template")
@Api(tags = {"审批流程模板"})
public class PurchaseTemplateController extends BaseController {
    @Autowired
    private IPurchaseTemplateService purchaseTemplateService;

    /**
     * 查询审批流程模板列表
     */
@PreAuthorize("@ss.hasAnyRoles('libraryAdmin,admin')") //@PreAuthorize("@ss.hasPermi('userApplication:("@ss.hasPermi('userApplication:template:list')")
    @GetMapping("/list")
    @ApiOperation(value ="查询审批流程模板列表",notes = "查询审批流程模板列表")
    public TableDataInfo list(PurchaseTemplate purchaseTemplate) {
        startPage();
        List<PurchaseTemplate> list = purchaseTemplateService.selectPurchaseTemplateList(purchaseTemplate);
        return getDataTable(list);
    }

    /**
     * 导出审批流程模板列表
     */
@PreAuthorize("@ss.hasAnyRoles('libraryAdmin,admin')") //@PreAuthorize("@ss.hasPermi('userApplication:("@ss.hasPermi('userApplication:template:export')")
    @Log(title = "审批流程模板", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ApiOperation(value ="导出审批流程模板列表",notes = "导出审批流程模板列表")
    public void export(HttpServletResponse response, PurchaseTemplate purchaseTemplate) {
        List<PurchaseTemplate> list = purchaseTemplateService.selectPurchaseTemplateList(purchaseTemplate);
        ExcelUtil<PurchaseTemplate> util = new ExcelUtil<PurchaseTemplate>(PurchaseTemplate.class);
        util.exportExcel(response, list, "审批流程模板数据");
    }

    /**
     * 获取审批流程模板详细信息
     */
    @PreAuthorize("@ss.hasPermi('userApplication:template:query')")
    @GetMapping(value = "/{id}")
    @ApiOperation(value ="获取审批流程模板详细信息",notes = "获取审批流程模板详细信息")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(purchaseTemplateService.selectPurchaseTemplateById(id));
    }

    /**
     * 新增审批流程模板
     */
    @PreAuthorize("@ss.hasPermi('userApplication:template:add')")
    @Log(title = "审批流程模板", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation(value ="新增审批流程模板",notes = "新增审批流程模板")
    public AjaxResult add(@RequestBody PurchaseTemplate purchaseTemplate) {
        purchaseTemplate.setCreateTime(new Date());
        purchaseTemplate.setCreatePersonal(getUserId());
        purchaseTemplate.setId(purchaseTemplate.getSnowID());
        return toAjax(purchaseTemplateService.insertPurchaseTemplate(purchaseTemplate));
    }

    /**
     * 修改审批流程模板
     */
    @PreAuthorize("@ss.hasPermi('userApplication:template:edit')")
    @Log(title = "审批流程模板", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation(value ="修改审批流程模板",notes = "修改审批流程模板")
    public AjaxResult edit(@RequestBody PurchaseTemplate purchaseTemplate) {
        purchaseTemplate.setUpdateTime(new Date());
        purchaseTemplate.setUpdatePersonal(getUserId());
        return toAjax(purchaseTemplateService.updatePurchaseTemplate(purchaseTemplate));
    }

    /**
     * 删除审批流程模板
     */
    @PreAuthorize("@ss.hasPermi('userApplication:template:remove')")
    @Log(title = "审批流程模板", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    @ApiOperation(value ="删除审批流程模板",notes = "删除审批流程模板")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(purchaseTemplateService.deletePurchaseTemplateByIds(ids));
    }


    /**
     * 解析上传的流程模板
     */
    @PreAuthorize("@ss.hasPermi('userApplication:template:import')")
    @Log(title = "审批流程模板", businessType = BusinessType.DELETE)
    @PostMapping("/import")
    @ApiOperation(value ="解析上传的流程模板",notes = "解析上传的流程模板")
    public AjaxResult anylaze(@ApiParam(value = "文件",name = "file") MultipartFile file) {
        File f = TemplateUtils.getFile(file);
        TemplateParam param = TemplateUtils.JsonFileParseTemplate(f);
        return null != param?AjaxResult.success(param):AjaxResult.error("模板格式不正确！");
    }
}
