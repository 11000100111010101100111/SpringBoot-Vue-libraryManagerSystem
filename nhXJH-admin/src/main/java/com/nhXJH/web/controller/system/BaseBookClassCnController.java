package com.nhXJH.web.controller.system;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nhXJH.common.core.domain.entity.SysUser;
import com.nhXJH.enums.StatusEnums;
import com.nhXJH.system.domain.po.BaseBookClassCn;
import com.nhXJH.system.domain.vo.BookClassVO;
import com.nhXJH.system.service.IBaseBookClassCnService;
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
 * 中国标准图书分类规则Controller
 * 
 * @author xjh
 * @date 2022-01-30
 */
@RestController
@RequestMapping("/main/bookClass")
@Api(tags = {"中国标准图书分类规则"})
public class BaseBookClassCnController extends BaseController {
    @Autowired
    private IBaseBookClassCnService baseBookClassCnService;

    /**
     * 查询中国标准图书分类规则列表
     */
@PreAuthorize("@ss.hasAnyRoles('libraryAdmin,admin')") //@PreAuthorize("@ss.hasPermi('userApplication:("@ss.hasPermi('main:bookClass:list')")
    @GetMapping("/list")
    @ApiOperation(value ="查询中国标准图书分类规则列表",notes = "查询中国标准图书分类规则列表")
    public TableDataInfo list(BaseBookClassCn baseBookClassCn) {
        Integer total = baseBookClassCnService.selectBaseBookClassCnList(baseBookClassCn).size();

        startPage();
        List<BaseBookClassCn> list = baseBookClassCnService.selectBaseBookClassCnList(baseBookClassCn);
        List<BookClassVO> bookClassList = new ArrayList<>();
        for (BaseBookClassCn bookClassCn : list) {
            bookClassList.add(new BookClassVO(bookClassCn));
        }

        TableDataInfo info = getDataTable(bookClassList);
        info.setTotal(total);
        return info;
    }

    /**
     * 导出中国标准图书分类规则列表
     */
@PreAuthorize("@ss.hasAnyRoles('libraryAdmin,admin')") //@PreAuthorize("@ss.hasPermi('userApplication:("@ss.hasPermi('main:bookClass:export')")
    @Log(title = "中国标准图书分类规则", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ApiOperation(value ="导出中国标准图书分类规则列表",notes = "导出中国标准图书分类规则列表")
    public void export(HttpServletResponse response, BaseBookClassCn baseBookClassCn) {
        List<BaseBookClassCn> list = baseBookClassCnService.selectBaseBookClassCnList(baseBookClassCn);
        ExcelUtil<BaseBookClassCn> util = new ExcelUtil<BaseBookClassCn>(BaseBookClassCn.class);
        util.exportExcel(response, list, "中国标准图书分类规则数据");
    }

    /**
     * 获取中国标准图书分类规则详细信息
     */
@PreAuthorize("@ss.hasAnyRoles('libraryAdmin,admin')") //@PreAuthorize("@ss.hasPermi('userApplication:("@ss.hasPermi('main:bookClass:query')")
    @GetMapping(value = "/{id}")
    @ApiOperation(value ="获取中国标准图书分类规则详细信息",notes = "获取中国标准图书分类规则详细信息")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(baseBookClassCnService.selectBaseBookClassCnById(id));
    }

    /**
     * 新增中国标准图书分类规则
     */
@PreAuthorize("@ss.hasAnyRoles('libraryAdmin,admin')") //@PreAuthorize("@ss.hasPermi('userApplication:("@ss.hasPermi('main:bookClass:add')")
    @Log(title = "中国标准图书分类规则", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation(value ="新增中国标准图书分类规则",notes = "新增中国标准图书分类规则")
    public AjaxResult add(@RequestBody BaseBookClassCn baseBookClassCn) {
        baseBookClassCn.setCreatePersonal(getUserId());
        baseBookClassCn.setCreateTime(new Date());
        baseBookClassCn.setStatus(StatusEnums.ONE.getDesc());
        baseBookClassCn.setId(baseBookClassCn.getSnowID());
        return toAjax(baseBookClassCnService.insertBaseBookClassCn(baseBookClassCn));
    }

    /**
     * 修改中国标准图书分类规则
     */
@PreAuthorize("@ss.hasAnyRoles('libraryAdmin,admin')") //@PreAuthorize("@ss.hasPermi('userApplication:("@ss.hasPermi('main:bookClass:edit')")
    @Log(title = "中国标准图书分类规则", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation(value ="修改中国标准图书分类规则",notes = "修改中国标准图书分类规则")
    public AjaxResult edit(@RequestBody BaseBookClassCn baseBookClassCn) {
        baseBookClassCn.setUpdatePersonal(getUserId());
        baseBookClassCn.setUpdateTime(new Date());
        return toAjax(baseBookClassCnService.updateBaseBookClassCn(baseBookClassCn));
    }

    /**
     * 删除中国标准图书分类规则
     */
@PreAuthorize("@ss.hasAnyRoles('libraryAdmin,admin')") //@PreAuthorize("@ss.hasPermi('userApplication:("@ss.hasPermi('main:bookClass:remove')")
    @Log(title = "中国标准图书分类规则", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    @ApiOperation(value ="删除中国标准图书分类规则",notes = "删除中国标准图书分类规则")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(baseBookClassCnService.deleteBaseBookClassCnByIds(ids));
    }

    /**
     * 状态修改
     */
    @ApiOperation("状态修改")
@PreAuthorize("@ss.hasAnyRoles('libraryAdmin,admin')") //@PreAuthorize("@ss.hasPermi('userApplication:("@ss.hasPermi('system:bookClass:edit')")
    @Log(title = "用户管理", businessType = BusinessType.UPDATE)
    @PutMapping("/changeStatus")
    public AjaxResult changeStatus(@RequestBody BaseBookClassCn baseBookClassCn) {
        baseBookClassCn.setUpdatePersonal(getUserId());
        baseBookClassCn.setUpdateTime(new Date());
        return toAjax(baseBookClassCnService.updateStatus(baseBookClassCn));
    }

    /**
     * 获取图书类别树结构
     */
    @ApiOperation("图书类别树结构")
    @PreAuthorize("@ss.hasAnyRoles('libraryAdmin,admin')") //@PreAuthorize("@ss.hasPermi('userApplication:("@ss.hasPermi('system:bookClass:tree')")
    @Log(title = "图书类别树结构", businessType = BusinessType.UPDATE)
    @GetMapping("/tree")
    public AjaxResult tree(){
        return AjaxResult.success(baseBookClassCnService.tree());
    }
    /**
     * 获取图书类别树结构
     */
    @ApiOperation("图书类别树结构")
    @PreAuthorize("@ss.hasAnyRoles('libraryAdmin,admin')") //@PreAuthorize("@ss.hasPermi('userApplication:("@ss.hasPermi('system:bookClass:tree')")
    @Log(title = "图书类别树结构", businessType = BusinessType.UPDATE)
    @GetMapping("/book/tree")
    public AjaxResult treeBook(){
        return AjaxResult.success(baseBookClassCnService.treeBook());
    }
    /**
     * 获取图书类别树结构
     */
    @ApiOperation("图书类别树结构")
    @PreAuthorize("@ss.hasAnyRoles('libraryAdmin,admin')") //@PreAuthorize("@ss.hasPermi('userApplication:("@ss.hasPermi('system:bookClass:tree')")
    @Log(title = "图书类别树结构", businessType = BusinessType.UPDATE)
    @GetMapping("/resource/tree")
    public AjaxResult treeResource(){
        return AjaxResult.success(baseBookClassCnService.treeResource());
    }
}
