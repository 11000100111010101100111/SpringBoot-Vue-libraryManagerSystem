package com.nhXJH.web.controller.appllication;

import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.nhXJH.web.domain.PurchaseRequisition;
import com.nhXJH.web.service.IPurchaseRequisitionService;
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
 * 采购申请信息Controller
 * 
 * @author xjh
 * @date 2022-01-25
 */
@RestController
@RequestMapping("/userApplication/requisition")
@Api(tags = {"采购申请信息"})
public class PurchaseRequisitionController extends BaseController {
    @Autowired
    private IPurchaseRequisitionService purchaseRequisitionService;

    /**
     * 查询采购申请信息列表
     */
    @PreAuthorize("@ss.hasAnyRoles('libraryAdmin,admin')") //@PreAuthorize("@ss.hasPermi('userApplication:requisition:list')")
    @GetMapping("/list")
    @ApiOperation(value ="查询采购申请信息列表",notes = "查询采购申请信息列表")
    public TableDataInfo list(PurchaseRequisition purchaseRequisition) {
        startPage();
        List<PurchaseRequisition> list = purchaseRequisitionService.selectPurchaseRequisitionList(purchaseRequisition);
        return getDataTable(list);
    }


    /**
     * 导出采购申请信息列表
     */
    @PreAuthorize("@ss.hasAnyRoles('libraryAdmin,admin')") //@PreAuthorize("@ss.hasPermi('userApplication:requisition:export')")
    @Log(title = "采购申请信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ApiOperation(value ="导出采购申请信息列表",notes = "导出采购申请信息列表")
    public void export(HttpServletResponse response, PurchaseRequisition purchaseRequisition) {
        List<PurchaseRequisition> list = purchaseRequisitionService.selectPurchaseRequisitionList(purchaseRequisition);
        ExcelUtil<PurchaseRequisition> util = new ExcelUtil<PurchaseRequisition>(PurchaseRequisition.class);
        util.exportExcel(response, list, "采购申请信息数据");
    }

    /**
     * 获取采购申请信息详细信息
     */
    @PreAuthorize("@ss.hasAnyRoles('libraryAdmin,admin')") //@PreAuthorize("@ss.hasPermi('userApplication:requisition:query')")
    @GetMapping(value = "/{id}")
    @ApiOperation(value ="获取采购申请信息详细信息",notes = "获取采购申请信息详细信息")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(purchaseRequisitionService.selectPurchaseRequisitionById(id));
    }

    /**
     * 新增采购申请信息
     */
    @PreAuthorize("@ss.hasAnyRoles('libraryAdmin,admin')") //@PreAuthorize("@ss.hasPermi('userApplication:requisition:add')")
    @Log(title = "采购申请信息", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation(value ="新增采购申请信息",notes = "新增采购申请信息")
    public AjaxResult add(@RequestBody PurchaseRequisition purchaseRequisition) {
        purchaseRequisition.setCreateTime(new Date());
        purchaseRequisition.setCreatePersonal(getUserId());
        purchaseRequisition.setId(purchaseRequisition.getSnowID());
        return toAjax(purchaseRequisitionService.insertPurchaseRequisition(purchaseRequisition));
    }

    /**
     * 修改采购申请信息
     */
    @PreAuthorize("@ss.hasAnyRoles('libraryAdmin,admin')") //@PreAuthorize("@ss.hasPermi('userApplication:requisition:edit')")
    @Log(title = "采购申请信息", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation(value ="修改采购申请信息",notes = "修改采购申请信息")
    public AjaxResult edit(@RequestBody PurchaseRequisition purchaseRequisition) {
        purchaseRequisition.setUpdateTime(new Date());
        purchaseRequisition.setUpdatePersonal(getUserId());
        return toAjax(purchaseRequisitionService.updatePurchaseRequisition(purchaseRequisition));
    }

    /**
     * 删除采购申请信息
     */
    @PreAuthorize("@ss.hasAnyRoles('libraryAdmin,admin')") //@PreAuthorize("@ss.hasPermi('userApplication:requisition:remove')")
    @Log(title = "采购申请信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    @ApiOperation(value ="删除采购申请信息",notes = "删除采购申请信息")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(purchaseRequisitionService.deletePurchaseRequisitionByIds(ids));
    }
}
