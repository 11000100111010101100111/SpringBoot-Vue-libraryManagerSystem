package com.nhXJH.web.controller.appllication;

import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.nhXJH.enums.DelEnums;
import com.nhXJH.enums.StatusEnums;
import com.nhXJH.web.domain.BasePurchaseRequisitionSub;
import com.nhXJH.web.domain.vo.InStockVO;
import com.nhXJH.web.service.IBasePurchaseRequisitionSubService;
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
 * 采购申请子Controller
 * 
 * @author xjh
 * @date 2022-02-28
 */
@RestController
@RequestMapping("/userApplication/purchaseSub")
@Api(tags = {"采购申请子"})
public class BasePurchaseRequisitionSubController extends BaseController {
    @Autowired
    private IBasePurchaseRequisitionSubService basePurchaseRequisitionSubService;

    /**
     * 查询采购申请子列表
     */
    @PreAuthorize("@ss.hasAnyRoles('libraryAdmin,admin')") //@PreAuthorize("@ss.hasPermi('userApplication:purchaseSub:list')")
    @GetMapping("/list")
    @ApiOperation(value ="查询采购申请子列表",notes = "查询采购申请子列表")
    public TableDataInfo list(BasePurchaseRequisitionSub basePurchaseRequisitionSub) {
        startPage();
        List<BasePurchaseRequisitionSub> list = basePurchaseRequisitionSubService.selectBasePurchaseRequisitionSubList(basePurchaseRequisitionSub);
        return getDataTable(list);
    }
    /**
     * 查询采购申请子列表
     */
    @PreAuthorize("@ss.hasAnyRoles('libraryAdmin,admin')") //@PreAuthorize("@ss.hasPermi('userApplication:purchaseSub:list')")
    @GetMapping("/subs")
    @ApiOperation(value ="查询采购申请子列表",notes = "查询采购申请子列表")
    public AjaxResult subs(BasePurchaseRequisitionSub basePurchaseRequisitionSub) {
        basePurchaseRequisitionSub.setCreatePersonal(getUserId());
        basePurchaseRequisitionSub.setStatus(StatusEnums.ONE.getDesc());
        basePurchaseRequisitionSub.setIsDel(DelEnums.NOT_DEL.getValue());
        List<BasePurchaseRequisitionSub> list = basePurchaseRequisitionSubService.selectSubList(basePurchaseRequisitionSub);
        return AjaxResult.success(list);
    }

    /**
     * 根据采购子表进行图书入库
     */
    @PreAuthorize("@ss.hasAnyRoles('libraryAdmin,admin')") //@PreAuthorize("@ss.hasPermi('userApplication:purchaseSub:update')")
    @GetMapping("/inStock")
    @ApiOperation(value ="根据采购子表进行图书入库",notes = "根据采购子表进行图书入库")
    public AjaxResult inStock(BasePurchaseRequisitionSub basePurchaseRequisitionSub) {
        return AjaxResult.success(basePurchaseRequisitionSubService.inStock(basePurchaseRequisitionSub));
    }

    /**
     * 导出采购申请子列表
     */
    @PreAuthorize("@ss.hasAnyRoles('libraryAdmin,admin')") //@PreAuthorize("@ss.hasPermi('userApplication:purchaseSub:export')")
    @Log(title = "采购申请子", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ApiOperation(value ="导出采购申请子列表",notes = "导出采购申请子列表")
    public void export(HttpServletResponse response, BasePurchaseRequisitionSub basePurchaseRequisitionSub) {
        List<BasePurchaseRequisitionSub> list = basePurchaseRequisitionSubService.selectBasePurchaseRequisitionSubList(basePurchaseRequisitionSub);
        ExcelUtil<BasePurchaseRequisitionSub> util = new ExcelUtil<BasePurchaseRequisitionSub>(BasePurchaseRequisitionSub.class);
        util.exportExcel(response, list, "采购申请子数据");
    }

    /**
     * 获取采购申请子详细信息
     */
    @PreAuthorize("@ss.hasAnyRoles('libraryAdmin,admin')") //@PreAuthorize("@ss.hasPermi('userApplication:purchaseSub:query')")
    @GetMapping(value = "/{id}")
    @ApiOperation(value ="获取采购申请子详细信息",notes = "获取采购申请子详细信息")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(basePurchaseRequisitionSubService.selectBasePurchaseRequisitionSubById(id));
    }

    /**
     * 新增采购申请子
     */
    @PreAuthorize("@ss.hasAnyRoles('libraryAdmin,admin')") //@PreAuthorize("@ss.hasPermi('userApplication:purchaseSub:add')")
    @Log(title = "采购申请子", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation(value ="新增采购申请子",notes = "新增采购申请子")
    public AjaxResult add(@RequestBody BasePurchaseRequisitionSub basePurchaseRequisitionSub) {
        basePurchaseRequisitionSub.setCreateTime(new Date());
        basePurchaseRequisitionSub.setCreatePersonal(getUserId());
        basePurchaseRequisitionSub.setId(basePurchaseRequisitionSub.getSnowID());
        return toAjax(basePurchaseRequisitionSubService.insertBasePurchaseRequisitionSub(basePurchaseRequisitionSub));
    }

    /**
     * 修改采购申请子
     */
    @PreAuthorize("@ss.hasAnyRoles('libraryAdmin,admin')") //@PreAuthorize("@ss.hasPermi('userApplication:purchaseSub:edit')")
    @Log(title = "采购申请子", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation(value ="修改采购申请子",notes = "修改采购申请子")
    public AjaxResult edit(@RequestBody BasePurchaseRequisitionSub basePurchaseRequisitionSub) {
        basePurchaseRequisitionSub.setUpdateTime(new Date());
        basePurchaseRequisitionSub.setUpdatePersonal(getUserId());
        return toAjax(basePurchaseRequisitionSubService.updateBasePurchaseRequisitionSub(basePurchaseRequisitionSub));
    }

    /**
     * 删除采购申请子
     */
    @PreAuthorize("@ss.hasAnyRoles('libraryAdmin,admin')") //@PreAuthorize("@ss.hasPermi('userApplication:purchaseSub:remove')")
    @Log(title = "采购申请子", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    @ApiOperation(value ="删除采购申请子",notes = "删除采购申请子")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(basePurchaseRequisitionSubService.deleteBasePurchaseRequisitionSubByIds(ids,getUserId()));
    }
}
