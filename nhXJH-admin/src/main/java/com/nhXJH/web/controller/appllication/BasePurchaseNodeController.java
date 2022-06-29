package com.nhXJH.web.controller.appllication;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.nhXJH.web.domain.BasePurchaseNode;
import com.nhXJH.web.service.IBasePurchaseNodeService;
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
 * 审批流程结点信息Controller
 * 
 * @author xjh
 * @date 2022-03-03
 */
@RestController
@RequestMapping("/userApplication/purchaseNode")
@Api(tags = {"审批流程结点信息"})
public class BasePurchaseNodeController extends BaseController {
    @Autowired
    private IBasePurchaseNodeService basePurchaseNodeService;

    /**
     * 查询审批流程结点信息列表
     */
    @PreAuthorize("@ss.hasAnyRoles('libraryAdmin,admin')") //@PreAuthorize("@ss.hasPermi('userApplication:purchaseNode:list')")
    @GetMapping("/list")
    @ApiOperation(value ="查询审批流程结点信息列表",notes = "查询审批流程结点信息列表")
    public TableDataInfo list(BasePurchaseNode basePurchaseNode) {
        startPage();
        List<BasePurchaseNode> list = basePurchaseNodeService.selectBasePurchaseNodeList(basePurchaseNode);
        return getDataTable(list);
    }

    /**
     * 导出审批流程结点信息列表
     */
    @PreAuthorize("@ss.hasAnyRoles('libraryAdmin,admin')") //@PreAuthorize("@ss.hasPermi('userApplication:purchaseNode:export')")
    @Log(title = "审批流程结点信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ApiOperation(value ="导出审批流程结点信息列表",notes = "导出审批流程结点信息列表")
    public void export(HttpServletResponse response, BasePurchaseNode basePurchaseNode) {
        List<BasePurchaseNode> list = basePurchaseNodeService.selectBasePurchaseNodeList(basePurchaseNode);
        ExcelUtil<BasePurchaseNode> util = new ExcelUtil<BasePurchaseNode>(BasePurchaseNode.class);
        util.exportExcel(response, list, "审批流程结点信息数据");
    }

    /**
     * 获取审批流程结点信息详细信息
     */
    @PreAuthorize("@ss.hasAnyRoles('libraryAdmin,admin')") //@PreAuthorize("@ss.hasPermi('userApplication:purchaseNode:query')")
    @GetMapping(value = "/{id}")
    @ApiOperation(value ="获取审批流程结点信息详细信息",notes = "获取审批流程结点信息详细信息")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(basePurchaseNodeService.selectBasePurchaseNodeById(id));
    }

    /**
     * 新增审批流程结点信息
     */
    @PreAuthorize("@ss.hasAnyRoles('libraryAdmin,admin')") //@PreAuthorize("@ss.hasPermi('userApplication:purchaseNode:add')")
    @Log(title = "审批流程结点信息", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation(value ="新增审批流程结点信息",notes = "新增审批流程结点信息")
    public AjaxResult add(@RequestBody BasePurchaseNode basePurchaseNode) {
        return toAjax(basePurchaseNodeService.insertBasePurchaseNode(basePurchaseNode));
    }

    /**
     * 修改审批流程结点信息
     */
    @PreAuthorize("@ss.hasAnyRoles('libraryAdmin,admin')") //@PreAuthorize("@ss.hasPermi('userApplication:purchaseNode:edit')")
    @Log(title = "审批流程结点信息", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation(value ="修改审批流程结点信息",notes = "修改审批流程结点信息")
    public AjaxResult edit(@RequestBody BasePurchaseNode basePurchaseNode) {
        return toAjax(basePurchaseNodeService.updateBasePurchaseNode(basePurchaseNode));
    }

    /**
     * 删除审批流程结点信息
     */
    @PreAuthorize("@ss.hasAnyRoles('libraryAdmin,admin')") //@PreAuthorize("@ss.hasPermi('userApplication:purchaseNode:remove')")
    @Log(title = "审批流程结点信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    @ApiOperation(value ="删除审批流程结点信息",notes = "删除审批流程结点信息")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(basePurchaseNodeService.deleteBasePurchaseNodeByIds(ids,getUserId()));
    }
}
