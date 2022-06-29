package com.nhXJH.web.controller.appllication;

import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.nhXJH.web.domain.PurchaseLink;
import com.nhXJH.web.domain.vo.PurchaseLinkNodeVO;
import com.nhXJH.web.service.IPurchaseLinkService;
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
 * 采购申请结点处理Controller
 * 
 * @author xjh
 * @date 2022-01-25
 */
@RestController
@RequestMapping("/userApplication/link")
@Api(tags = {"采购申请结点处理"})
public class PurchaseLinkController extends BaseController {
    @Autowired
    private IPurchaseLinkService purchaseLinkService;

    /**
     * 查询采购申请结点处理列表
     */
    @PreAuthorize("@ss.hasAnyRoles('libraryAdmin,admin')") //@PreAuthorize("@ss.hasPermi('userApplication:link:list')")
    @GetMapping("/list")
    @ApiOperation(value ="查询采购申请结点处理列表",notes = "查询采购申请结点处理列表")
    public TableDataInfo list(PurchaseLink purchaseLink) {
        startPage();
        List<PurchaseLink> list = purchaseLinkService.selectPurchaseLinkList(purchaseLink);
        return getDataTable(list);
    }

    /**
     * 查询采购申请结点处理列表
     */
    @PreAuthorize("@ss.hasAnyRoles('libraryAdmin,admin')") //@PreAuthorize("@ss.hasPermi('userApplication:link:list')")
    @GetMapping("/link")
    @ApiOperation(value ="查询采购申请结点处理列表",notes = "查询采购申请结点处理列表")
    public AjaxResult link(PurchaseLink purchaseLink) {
        List<PurchaseLinkNodeVO> list = purchaseLinkService.selectPurchaseLink(purchaseLink);
        return AjaxResult.success(list);
    }
    /**
     * 导出采购申请结点处理列表
     */
    @PreAuthorize("@ss.hasAnyRoles('libraryAdmin,admin')") //@PreAuthorize("@ss.hasPermi('userApplication:link:export')")
    @Log(title = "采购申请结点处理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ApiOperation(value ="导出采购申请结点处理列表",notes = "导出采购申请结点处理列表")
    public void export(HttpServletResponse response, PurchaseLink purchaseLink) {
        List<PurchaseLink> list = purchaseLinkService.selectPurchaseLinkList(purchaseLink);
        ExcelUtil<PurchaseLink> util = new ExcelUtil<PurchaseLink>(PurchaseLink.class);
        util.exportExcel(response, list, "采购申请结点处理数据");
    }

    /**
     * 获取采购申请结点处理详细信息
     */
    @PreAuthorize("@ss.hasAnyRoles('libraryAdmin,admin')") //@PreAuthorize("@ss.hasPermi('userApplication:link:query')")
    @GetMapping(value = "/{id}")
    @ApiOperation(value ="获取采购申请结点处理详细信息",notes = "获取采购申请结点处理详细信息")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(purchaseLinkService.selectPurchaseLinkById(id));
    }

    /**
     * 新增采购申请结点处理
     */
    @PreAuthorize("@ss.hasAnyRoles('libraryAdmin,admin')") //@PreAuthorize("@ss.hasPermi('userApplication:link:add')")
    @Log(title = "采购申请结点处理", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation(value ="新增采购申请结点处理",notes = "新增采购申请结点处理")
    public AjaxResult add(@RequestBody PurchaseLink purchaseLink) {
        purchaseLink.setCreateTime(new Date());
        purchaseLink.setCreatePersonal(getUserId());
        purchaseLink.setId(purchaseLink.getSnowID());
        return toAjax(purchaseLinkService.insertPurchaseLink(purchaseLink));
    }

    /**
     * 修改采购申请结点处理
     */
    @PreAuthorize("@ss.hasAnyRoles('libraryAdmin,admin')") //@PreAuthorize("@ss.hasPermi('userApplication:link:edit')")
    @Log(title = "采购申请结点处理", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation(value ="修改采购申请结点处理",notes = "修改采购申请结点处理")
    public AjaxResult edit(@RequestBody PurchaseLink purchaseLink) {
        purchaseLink.setUpdateTime(new Date());
        purchaseLink.setUpdatePersonal(getUserId());
        return toAjax(purchaseLinkService.updatePurchaseLink(purchaseLink));
    }

    /**
     * 删除采购申请结点处理
     */
    @PreAuthorize("@ss.hasAnyRoles('libraryAdmin,admin')") //@PreAuthorize("@ss.hasPermi('userApplication:link:remove')")
    @Log(title = "采购申请结点处理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    @ApiOperation(value ="删除采购申请结点处理",notes = "删除采购申请结点处理")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(purchaseLinkService.deletePurchaseLinkByIds(ids,getUserId()));
    }
}
