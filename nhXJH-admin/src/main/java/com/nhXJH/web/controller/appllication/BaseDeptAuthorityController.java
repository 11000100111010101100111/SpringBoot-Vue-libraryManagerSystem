package com.nhXJH.web.controller.appllication;

import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.nhXJH.common.core.domain.entity.SysDept;
import com.nhXJH.system.service.ISysDeptService;
import com.nhXJH.web.domain.BaseDeptAuthority;
import com.nhXJH.web.domain.param.DeptAuthorityParam;
import com.nhXJH.web.domain.vo.DeptAuthorityVO;
import com.nhXJH.web.service.IBaseDeptAuthorityService;
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
 * 用户可访问部门信息Controller
 * 
 * @author xjh
 * @date 2022-02-27
 */
@RestController
@RequestMapping("/userApplication/authority")
@Api(tags = {"用户可访问部门信息"})
public class BaseDeptAuthorityController extends BaseController {
    @Autowired
    private IBaseDeptAuthorityService baseDeptAuthorityService;
    @Autowired
    private ISysDeptService deptService;
    /**
     * 查询用户可访问部门信息列表
     */
    @PreAuthorize("@ss.hasAnyRoles('libraryAdmin,admin')") //@PreAuthorize("@ss.hasPermi('userApplication:authority:list')")
    @GetMapping("/list")
    @ApiOperation(value ="查询用户可访问部门信息列表",notes = "查询用户可访问部门信息列表")
    public TableDataInfo list(BaseDeptAuthority baseDeptAuthority) {
        List<BaseDeptAuthority> l = baseDeptAuthorityService.selectBaseDeptAuthorityList(baseDeptAuthority);
        List<DeptAuthorityVO> item = baseDeptAuthorityService.selectBaseDeptAuthorityVOList(l);
//        int pageNum = baseDeptAuthority.getPageNum();
//        int pageSize = baseDeptAuthority.getPageSize();
//        int bound = pageSize*pageNum-1;
//
//        int start = (pageNum-1)*pageSize;
//        int end = Math.min(bound, item.size());
//        TableDataInfo info = getDataTable(item.subList(start, end) );
        TableDataInfo info = getDataTable(item );
        return info;
    }
    /**
     * 查询用户可访问部门信息列表
     */
    @PreAuthorize("@ss.hasAnyRoles('libraryAdmin,admin')") //@PreAuthorize("@ss.hasPermi('userApplication:authority:list')")
    @GetMapping("/dept/{userId}")
    @ApiOperation(value ="查询用户可访问部门信息列表",notes = "查询用户可访问部门信息列表")
    public AjaxResult list(@PathVariable("userId") Long userId) {
        List<SysDept> dept = baseDeptAuthorityService.getDepts(userId);
        return AjaxResult.success(deptService.buildDeptTreeSelect(dept));
    }
    /**
     * 导出用户可访问部门信息列表
     */
    @PreAuthorize("@ss.hasAnyRoles('libraryAdmin,admin')") //@PreAuthorize("@ss.hasPermi('userApplication:authority:export')")
    @Log(title = "用户可访问部门信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ApiOperation(value ="导出用户可访问部门信息列表",notes = "导出用户可访问部门信息列表")
    public void export(HttpServletResponse response, BaseDeptAuthority baseDeptAuthority) {
        List<BaseDeptAuthority> list = baseDeptAuthorityService.selectBaseDeptAuthorityList(baseDeptAuthority);
        ExcelUtil<BaseDeptAuthority> util = new ExcelUtil<BaseDeptAuthority>(BaseDeptAuthority.class);
        util.exportExcel(response, list, "用户可访问部门信息数据");
    }

    /**
     * 获取用户可访问部门信息详细信息
     */
    @PreAuthorize("@ss.hasAnyRoles('libraryAdmin,admin')") //@PreAuthorize("@ss.hasPermi('userApplication:authority:query')")
    @GetMapping(value = "/{id}")
    @ApiOperation(value ="获取用户可访问部门信息详细信息",notes = "获取用户可访问部门信息详细信息")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(baseDeptAuthorityService.selectBaseDeptAuthorityById(id));
    }

    /**
     * 新增用户可访问部门信息
     */
    @PreAuthorize("@ss.hasAnyRoles('libraryAdmin,admin')") //@PreAuthorize("@ss.hasPermi('userApplication:authority:add')")
    @Log(title = "用户可访问部门信息", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation(value ="新增用户可访问部门信息",notes = "新增用户可访问部门信息")
    public AjaxResult add(@RequestBody BaseDeptAuthority baseDeptAuthority) {
        baseDeptAuthority.setCreatePersonal(getUserId());
        baseDeptAuthority.setCreateTime(new Date());
        baseDeptAuthority.setId(baseDeptAuthority.getSnowID());
        return toAjax(baseDeptAuthorityService.insertBaseDeptAuthority(baseDeptAuthority));
    }

    /**
     * 新增用户可访问部门信息
     */
    @PreAuthorize("@ss.hasAnyRoles('libraryAdmin,admin')") //@PreAuthorize("@ss.hasPermi('userApplication:authority:add')")
    @Log(title = "用户可访问部门信息", businessType = BusinessType.INSERT)
    @DeleteMapping("/adds/{ids}")
    @ApiOperation(value ="新增用户可访问部门信息",notes = "新增用户可访问部门信息")
    public AjaxResult adds(@PathVariable("ids") Long[] ids) {
        return AjaxResult.success(baseDeptAuthorityService.insertBaseDeptAuthoritys(ids,getUserId()));
    }

    /**
     * 新增用户可访问部门信息
     */
    @PreAuthorize("@ss.hasAnyRoles('libraryAdmin,admin')") //@PreAuthorize("@ss.hasPermi('userApplication:authority:add')")
    @Log(title = "用户可访问部门信息", businessType = BusinessType.INSERT)
    @PostMapping("/addAuthority")
    @ApiOperation(value ="新增用户可访问部门信息",notes = "新增用户可访问部门信息")
    public AjaxResult addDeptAuthority(@RequestBody DeptAuthorityParam param) {
        param.setOperatorUser(getUserId());
        return AjaxResult.success(baseDeptAuthorityService.insertBaseDeptAuthoritys(param));
    }

    /**
     * 修改用户可访问部门信息
     */
    @PreAuthorize("@ss.hasAnyRoles('libraryAdmin,admin')") //@PreAuthorize("@ss.hasPermi('userApplication:authority:edit')")
    @Log(title = "用户可访问部门信息", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation(value ="修改用户可访问部门信息",notes = "修改用户可访问部门信息")
    public AjaxResult edit(@RequestBody BaseDeptAuthority baseDeptAuthority) {
        baseDeptAuthority.setUpdatePersonal(getUserId());
        baseDeptAuthority.setUpdateTime(new Date());
        return toAjax(baseDeptAuthorityService.updateBaseDeptAuthority(baseDeptAuthority));
    }

    /**
     * 删除用户可访问部门信息
     */
    @PreAuthorize("@ss.hasAnyRoles('libraryAdmin,admin')") //@PreAuthorize("@ss.hasPermi('userApplication:authority:remove')")
    @Log(title = "用户可访问部门信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/del/{ids}")
    @ApiOperation(value ="删除用户可访问部门信息",notes = "删除用户可访问部门信息")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(baseDeptAuthorityService.deleteBaseDeptAuthorityByIds(ids,getUserId()));
    }
}
