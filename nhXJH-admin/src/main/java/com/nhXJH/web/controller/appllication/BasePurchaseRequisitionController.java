package com.nhXJH.web.controller.appllication;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.sql.rowset.serial.SerialException;
import javax.swing.text.html.HTML;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.nhXJH.common.exception.ServiceException;
import com.nhXJH.common.utils.StringUtils;
import com.nhXJH.enums.DelEnums;
import com.nhXJH.enums.PurchaseNodeStatusEnums;
import com.nhXJH.enums.StatusEnums;
import com.nhXJH.web.domain.BasePurchaseRequisition;
import com.nhXJH.web.domain.BasePurchaseRequisitionSub;
import com.nhXJH.web.domain.PurchaseRequisition;
import com.nhXJH.web.domain.param.PurchaseParam;
import com.nhXJH.web.domain.param.TempParam;
import com.nhXJH.web.domain.vo.BasePurchaseRequisitionVO;
import com.nhXJH.web.domain.vo.MyMatterVO;
import com.nhXJH.web.service.IBasePurchaseRequisitionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
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
 * 采购申请信息Controller
 * 
 * @author xjh
 * @date 2022-02-28
 */
@RestController
@RequestMapping("/userApplication/purchase")
@Api(tags = {"采购申请信息"})
public class BasePurchaseRequisitionController extends BaseController {
    @Autowired
    private IBasePurchaseRequisitionService basePurchaseRequisitionService;

    /**
     * 查询采购申请信息列表
     */
    @PreAuthorize("@ss.hasAnyRoles('libraryAdmin,admin')") //@PreAuthorize("@ss.hasPermi('userApplication:purchase:list')")
    @GetMapping("/list")
    @ApiOperation(value ="查询采购申请信息列表",notes = "查询采购申请信息列表")
    public TableDataInfo list(BasePurchaseRequisition basePurchaseRequisition) {
        startPage();
        List<BasePurchaseRequisition> list = basePurchaseRequisitionService.selectBasePurchaseRequisitionList(basePurchaseRequisition);
        basePurchaseRequisitionService.updateTimeout(list);
        return getDataTable(list);
    }


    /**
     * 查询采购申请信息列表
     */
    @PreAuthorize("@ss.hasAnyRoles('libraryAdmin,admin')") //@PreAuthorize("@ss.hasPermi('userApplication:purchase:list')")
    @GetMapping("/mine")
    @ApiOperation(value ="查询采购申请信息列表",notes = "查询采购申请信息列表")
    public TableDataInfo mine(BasePurchaseRequisition purchaseRequisition) {
        purchaseRequisition.setCreatePersonal(getUserId());
        List<MyMatterVO> list = basePurchaseRequisitionService.getMyMatter(purchaseRequisition);
        int count = StringUtils.isNull(list)?0:list.size();
        startPage();
        list = basePurchaseRequisitionService.getMyMatter(purchaseRequisition);
        TableDataInfo info = getDataTable(list);
        info.setTotal(count);
        return info;
    }
    /**
     * 查询采购申请信息列表
     */
    @PreAuthorize("@ss.hasAnyRoles('libraryAdmin,admin')") //@PreAuthorize("@ss.hasPermi('userApplication:purchase:list')")
    @GetMapping("/vo")
    @ApiOperation(value ="查询采购申请信息列表",notes = "查询采购申请信息列表")
    public TableDataInfo vo(BasePurchaseRequisition basePurchaseRequisition) {
        startPage();
        basePurchaseRequisition.setCreatePersonal(getUserId());
        List<BasePurchaseRequisitionVO> list = basePurchaseRequisitionService.selectBasePurchaseRequisitionVOList(basePurchaseRequisition);
        Integer total = null==list?0:list.size();
        TableDataInfo info = getDataTable(list);
        info.setTotal(total);
        return info;
    }

    /**
     * 查询采购申请信息列表
     */
    @PreAuthorize("@ss.hasAnyRoles('libraryAdmin,admin')") //@PreAuthorize("@ss.hasPermi('userApplication:purchase:list')")
    @GetMapping("/approved/list")
    @ApiOperation(value ="查询采购申请信息列表",notes = "查询采购申请信息列表")
    public TableDataInfo approved(BasePurchaseRequisition basePurchaseRequisition) {
        startPage();
        basePurchaseRequisition.setCreatePersonal(getUserId());
        List<BasePurchaseRequisition> list = basePurchaseRequisitionService.selectApproved(basePurchaseRequisition);
        return getDataTable(list);
    }

    /**
     * 导出采购申请信息列表
     */
    @PreAuthorize("@ss.hasAnyRoles('libraryAdmin,admin')") //@PreAuthorize("@ss.hasPermi('userApplication:purchase:export')")
    @Log(title = "采购申请信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ApiOperation(value ="导出采购申请信息列表",notes = "导出采购申请信息列表")
    public void export(HttpServletResponse response, BasePurchaseRequisition basePurchaseRequisition) {
        List<BasePurchaseRequisition> list = basePurchaseRequisitionService.selectBasePurchaseRequisitionList(basePurchaseRequisition);
        ExcelUtil<BasePurchaseRequisition> util = new ExcelUtil<BasePurchaseRequisition>(BasePurchaseRequisition.class);
        util.exportExcel(response, list, "采购申请信息数据");
    }

    /**
     * 获取采购申请信息详细信息
     */
    @PreAuthorize("@ss.hasAnyRoles('libraryAdmin,admin')") //@PreAuthorize("@ss.hasPermi('userApplication:purchase:query')")
    @GetMapping(value = "/{id}")
    @ApiOperation(value ="获取采购申请信息详细信息",notes = "获取采购申请信息详细信息")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(basePurchaseRequisitionService.selectBasePurchaseRequisitionById(id));
    }

    /**
     * 新增采购申请信息
     */
    @PreAuthorize("@ss.hasAnyRoles('libraryAdmin,admin')") //@PreAuthorize("@ss.hasPermi('userApplication:purchase:add')")
    @Log(title = "采购申请信息", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation(value ="新增采购申请信息",notes = "新增采购申请信息")
    public AjaxResult add(@RequestBody String param) {
         PurchaseParam purchaseParam = JSON.parseObject(param,PurchaseParam.class);
        if (null == purchaseParam || null == purchaseParam.getPurchase()){
            throw new ServiceException("提交的申请信息为空");
        }

        purchaseParam.getPurchase().setCreateTime(new Date());
        purchaseParam.getPurchase().setCreatePersonal(getUserId());
        purchaseParam.getPurchase().setApprovePersonal(getUserId());
        purchaseParam.getPurchase().setId(purchaseParam.getPurchase().getSnowID());
        purchaseParam.getPurchase().setStatus(StatusEnums.ONE.getDesc());

        if (null != purchaseParam.getSubPurchase() && purchaseParam.getSubPurchase().size()>0){
            Long subGroupId = purchaseParam.getPurchase().getId();
            Date now = new Date();
            for (BasePurchaseRequisitionSub purchaseSub : purchaseParam.getSubPurchase()) {
                purchaseSub.setBatchId(subGroupId);//插入首批子表记录的批次ID与主表id一致，更新时批次ID更新
                purchaseSub.setCreatePersonal(getUserId());
                purchaseSub.setCreateTime(now);
                purchaseSub.setId(purchaseSub.getSnowID());
                purchaseSub.setGroupId(subGroupId);
            }
        }

        return toAjax(basePurchaseRequisitionService.insertBasePurchaseRequisition(purchaseParam));
    }

    /**
     * 修改采购申请信息
     */
    @PreAuthorize("@ss.hasAnyRoles('libraryAdmin,admin')") //@PreAuthorize("@ss.hasPermi('userApplication:purchase:edit')")
    @Log(title = "采购申请信息", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation(value ="修改采购申请信息",notes = "修改采购申请信息")
    public AjaxResult edit(@RequestBody String param) {
        PurchaseParam purchaseParam = JSON.parseObject(param,PurchaseParam.class);
        if (null == purchaseParam || null == purchaseParam.getPurchase()){
            throw new ServiceException("提交的申请信息为空");
        }

        BasePurchaseRequisition purchase = purchaseParam.getPurchase();

        if (null == purchase || null == purchase.getId()){
            throw new ServiceException("Data error");
        }
        purchase.setUpdateTime(new Date());
        purchase.setUpdatePersonal(getUserId());

        // verify 验证是否操作人是本人，当前记录是否已提交（能否被修改）
        BasePurchaseRequisition query = basePurchaseRequisitionService.selectBasePurchaseRequisitionById(purchase.getId());
        if(null == query){
            throw new ServiceException("提交信息已失效，尝试刷新后操作");
        }
        if (!query.getCreatePersonal().equals(getUserId())){
            throw new ServiceException("权限不足，无法更改其他人员提交的申请");
        }
        if (!PurchaseNodeStatusEnums.NOT_SUBMIT.getDesc().equals(query.getPurchaseStatus())){
            throw new ServiceException("已提交的申请，无法再次执行操作");
        }

        if(null == purchase.getStatus()) {
            purchase.setStatus(StatusEnums.ONE.getDesc());
        }

        return toAjax(basePurchaseRequisitionService.updateBasePurchaseRequisition(purchaseParam));
    }

    /**
     * 删除采购申请信息
     */
    @PreAuthorize("@ss.hasAnyRoles('libraryAdmin,admin')") //@PreAuthorize("@ss.hasPermi('userApplication:purchase:remove')")
    @Log(title = "采购申请信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    @ApiOperation(value ="删除采购申请信息",notes = "删除采购申请信息")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(basePurchaseRequisitionService.deleteBasePurchaseRequisitionByIds(ids,getUserId()));
    }

    @PreAuthorize("@ss.hasAnyRoles('libraryAdmin,admin')") //@PreAuthorize("@ss.hasPermi('userApplication:purchase:remove')")
    @Log(title = "采购申请信息", businessType = BusinessType.DELETE)
    @PostMapping("/test")
    @ApiOperation(value ="删除采购申请信息",notes = "删除采购申请信息")
    public AjaxResult test(@RequestBody HTML html) {
        return AjaxResult.success("",html.toString());
    }
}
