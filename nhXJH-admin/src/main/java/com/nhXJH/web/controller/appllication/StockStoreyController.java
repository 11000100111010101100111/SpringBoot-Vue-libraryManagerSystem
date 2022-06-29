package com.nhXJH.web.controller.appllication;

import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.nhXJH.common.enums.application.StatusEnums;
import com.nhXJH.web.domain.StockStorey;
import com.nhXJH.web.domain.vo.StoreyVO;
import com.nhXJH.web.service.IStockStoreyService;
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
 * 书架层信息Controller
 * 
 * @author xjh
 * @date 2022-01-25
 */
@RestController
@RequestMapping("/userApplication/storey")
@Api(tags = {"书架层信息"})
public class StockStoreyController extends BaseController {
    @Autowired
    private IStockStoreyService stockStoreyService;

    /**
     * 查询书架层信息列表
     */
    @PreAuthorize("@ss.hasPermi('userApplication:storey:list')")
    @GetMapping("/list")
    @ApiOperation(value ="查询书架层信息列表",notes = "查询书架层信息列表")
    public TableDataInfo list(StockStorey stockStorey) {
        startPage();
        List<StoreyVO> list = stockStoreyService.selectStockStoreyVOList(stockStorey);
        int total = null==list?0:list.size();
        TableDataInfo info = getDataTable(list);
        info.setTotal(total);
        return info;
    }

    /**
     * 导出书架层信息列表
     */
    @PreAuthorize("@ss.hasPermi('userApplication:storey:export')")
    @Log(title = "书架层信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ApiOperation(value ="导出书架层信息列表",notes = "导出书架层信息列表")
    public void export(HttpServletResponse response, StockStorey stockStorey) {
        List<StockStorey> list = stockStoreyService.selectStockStoreyList(stockStorey);
        ExcelUtil<StockStorey> util = new ExcelUtil<StockStorey>(StockStorey.class);
        util.exportExcel(response, list, "书架层信息数据");
    }

    /**
     * 获取书架层信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('userApplication:storey:query')")
    @GetMapping(value = "/{id}")
    @ApiOperation(value ="获取书架层信息详细信息",notes = "获取书架层信息详细信息")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(stockStoreyService.selectStockStoreyById(id));
    }

    /**
     * 新增书架层信息
     */
    @PreAuthorize("@ss.hasPermi('userApplication:storey:add')")
    @Log(title = "书架层信息", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation(value ="新增书架层信息",notes = "新增书架层信息")
    public AjaxResult add(@RequestBody StockStorey stockStorey) {
        stockStorey.setCreateTime(new Date());
        stockStorey.setCreatePersonal(getUserId());
        stockStorey.setStatus(StatusEnums.ONE.getDesc());
        stockStorey.setId(stockStorey.getSnowID());
        return toAjax(stockStoreyService.insertStockStorey(stockStorey));
    }

    /**
     * 修改书架层信息
     */
    @PreAuthorize("@ss.hasPermi('userApplication:storey:edit')")
    @Log(title = "书架层信息", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation(value ="修改书架层信息",notes = "修改书架层信息")
    public AjaxResult edit(@RequestBody StockStorey stockStorey) {
        stockStorey.setUpdatePersonal(getUserId());
        stockStorey.setUpdateTime(new Date());
        return toAjax(stockStoreyService.updateStockStorey(stockStorey));
    }

    /**
     * 删除书架层信息
     */
    @PreAuthorize("@ss.hasPermi('userApplication:storey:remove')")
    @Log(title = "书架层信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    @ApiOperation(value ="删除书架层信息",notes = "删除书架层信息")
    public AjaxResult remove(@PathVariable Long[] ids) {
        List<StockStorey> list = stockStoreyService.deleteStockStoreyByIds(ids);
        return null == list ? AjaxResult.success("删除成功"):AjaxResult.error("部分书架层存在图书，无法删除",list);
    }
}
