package com.nhXJH.web.controller.appllication;

import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.nhXJH.common.utils.poi.ExcelUtil;
import com.nhXJH.web.domain.ApprovalQueue;
import com.nhXJH.web.service.IApprovalQueueService;
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
import com.nhXJH.common.core.page.TableDataInfo;

/**
 * 申请队列信息Controller
 * 
 * @author xjh
 * @date 2022-01-25
 */
@RestController
@RequestMapping("/userApplication/queue")
@Api(tags = {"申请队列信息"})
public class ApprovalQueueController extends BaseController {
    @Autowired
    private IApprovalQueueService approvalQueueService;

    /**
     * 查询申请队列信息列表
     */
//    @PreAuthorize("@ss.hasPermi('userApplication:queue:list')")
    @PreAuthorize("@ss.hasAnyRoles('libraryAdmin,admin')")
    @GetMapping("/list")
    @ApiOperation(value ="查询申请队列信息列表",notes = "查询申请队列信息列表")
    public TableDataInfo list(ApprovalQueue approvalQueue) {
        startPage();
        List<ApprovalQueue> list = approvalQueueService.selectApprovalQueueList(approvalQueue);
        return getDataTable(list);
    }

    /**
     * 导出申请队列信息列表
     */
//    @PreAuthorize("@ss.hasPermi('userApplication:queue:export')")
    @PreAuthorize("@ss.hasAnyRoles('libraryAdmin,admin')")
    @Log(title = "申请队列信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ApiOperation(value ="导出申请队列信息列表",notes = "导出申请队列信息列表")
    public void export(HttpServletResponse response, ApprovalQueue approvalQueue) {
        List<ApprovalQueue> list = approvalQueueService.selectApprovalQueueList(approvalQueue);
        ExcelUtil<ApprovalQueue> util = new ExcelUtil<ApprovalQueue>(ApprovalQueue.class);
        util.exportExcel(response, list, "申请队列信息数据");
    }

    /**
     * 获取申请队列信息详细信息
     */
//    @PreAuthorize("@ss.hasPermi('userApplication:queue:query')")
    @PreAuthorize("@ss.hasAnyRoles('libraryAdmin,admin')")
    @GetMapping(value = "/{id}")
    @ApiOperation(value ="获取申请队列信息详细信息",notes = "获取申请队列信息详细信息")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(approvalQueueService.selectApprovalQueueById(id));
    }

    /**
     * 新增申请队列信息
     */
//    @PreAuthorize("@ss.hasPermi('userApplication:queue:add')")
    @PreAuthorize("@ss.hasAnyRoles('libraryAdmin,admin')")
    @Log(title = "申请队列信息", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation(value ="新增申请队列信息",notes = "新增申请队列信息")
    public AjaxResult add(@RequestBody ApprovalQueue approvalQueue) {
        approvalQueue.setCreatePersonal(getUserId());
        approvalQueue.setId(approvalQueue.getSnowID());
        return toAjax(approvalQueueService.insertApprovalQueue(approvalQueue));
    }

    /**
     * 修改申请队列信息
     */
//    @PreAuthorize("@ss.hasPermi('userApplication:queue:edit')")
    @PreAuthorize("@ss.hasAnyRoles('libraryAdmin,admin')")
    @Log(title = "申请队列信息", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation(value ="修改申请队列信息",notes = "修改申请队列信息")
    public AjaxResult edit(@RequestBody ApprovalQueue approvalQueue) {
        approvalQueue.setUpdatePersonal(getUserId());
        approvalQueue.setUpdateTime(new Date());
        return toAjax(approvalQueueService.updateApprovalQueue(approvalQueue));
    }

    /**
     * 修改申请队列信息
     */
//    @PreAuthorize("@ss.hasAnyRoles('libraryAdmin,admin')")
    @PreAuthorize("@ss.hasAnyRoles('libraryAdmin,admin')")
    @Log(title = "申请队列信息", businessType = BusinessType.UPDATE)
    @PostMapping("/approve")
    @ApiOperation(value ="修改申请队列信息",notes = "修改申请队列信息")
    public AjaxResult approve(@RequestBody ApprovalQueue approvalQueue) {
        approvalQueue.setUpdatePersonal(getUserId());
        approvalQueue.setUpdateTime(new Date());
        return toAjax(approvalQueueService.approve(approvalQueue));
    }

    /**
     * 删除申请队列信息
     */
//    @PreAuthorize("@ss.hasPermi('userApplication:queue:remove')")
    @PreAuthorize("@ss.hasAnyRoles('libraryAdmin,admin')")
    @Log(title = "申请队列信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    @ApiOperation(value ="删除申请队列信息",notes = "删除申请队列信息")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(approvalQueueService.deleteApprovalQueueByIds(ids,getUserId()));
    }
    /**
     * 删除申请队列信息
     */
//    @PreAuthorize("@ss.hasPermi('userApplication:queue:remove')")
    @Log(title = "申请队列信息", businessType = BusinessType.DELETE)
	@GetMapping("/readAll/{ids}")
    @ApiOperation(value ="删除申请队列信息",notes = "删除申请队列信息")
    public AjaxResult readAll(@PathVariable("ids") String ids) {
        return toAjax(approvalQueueService.readAll(ids,getUserId()));
    }
}
