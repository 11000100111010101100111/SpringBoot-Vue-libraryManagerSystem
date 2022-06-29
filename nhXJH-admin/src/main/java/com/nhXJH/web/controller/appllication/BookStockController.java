package com.nhXJH.web.controller.appllication;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.servlet.http.HttpServletResponse;

import com.nhXJH.common.core.redis.RedisCache;
import com.nhXJH.common.utils.StringUtils;
import com.nhXJH.web.domain.BookStock;
import com.nhXJH.web.domain.LibraryBook;
import com.nhXJH.web.domain.entity.BrowBookCode;
import com.nhXJH.web.domain.vo.BookStockVO;
import com.nhXJH.web.service.IBookStockService;
import com.nhXJH.web.util.CodeUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.RedisServer;
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
 * 图书库存信息Controller
 * 
 * @author xjh
 * @date 2022-01-25
 */
@RestController
@RequestMapping("/userApplication/bookStock")
@Api(tags = {"图书库存信息"})
public class BookStockController extends BaseController {
    @Autowired
    private IBookStockService bookStockService;
    @Autowired
    private RedisCache redis;
    @Value("${brow.book.timeout:5}")
    private Integer browBookCodeTimeout;
    /**
     * 查询图书库存信息列表
     */
    @PreAuthorize("@ss.hasAnyRoles('libraryAdmin,admin')") //@PreAuthorize("@ss.hasPermi('userApplication:stock:list')")
    @GetMapping("/list")
    @ApiOperation(value ="查询图书库存信息列表",notes = "查询图书库存信息列表")
    public TableDataInfo list(BookStock bookStock) {
        startPage();
        List<BookStock> list = bookStockService.selectBookStockList(bookStock);
        return getDataTable(list);
    }
    /**
     * 获取图书借阅码
     */
    @GetMapping("/getCode")
    @ApiOperation(value ="查询图书库存信息列表",notes = "查询图书库存信息列表")
    public AjaxResult getCode(LibraryBook book) {
        BrowBookCode browBookCode = new BrowBookCode();
        browBookCode.setUserId(getUserId());
        browBookCode = bookStockService.selectBookStockList(book,browBookCode);
        String key = browBookCode.getCode()+"_"+getUserId();

        if(StringUtils.isNull(redis.getCacheObject(key))) {
            redis.setCacheObject(
                    key,
                    browBookCode,
                    this.browBookCodeTimeout,
                    TimeUnit.MINUTES);
        }else{
            browBookCode = redis.getCacheObject(key);
        }
        return null==browBookCode.getCode()?
                AjaxResult.error("手慢了~~，就在刚刚,图书已经被别人借光了!")
                :AjaxResult.success("借阅码获取成功,有效期["+this.browBookCodeTimeout+"]分钟",browBookCode.getCode());
    }
    /**
     * 根据借阅码借阅图书
     */
    @GetMapping("/browBook/{code}")
    @ApiOperation(value ="根据借阅码借阅图书",notes = "根据借阅码借阅图书")
    public AjaxResult browBook(@PathVariable("code")String code) {
        if (StringUtils.isEmpty(code) || !CodeUtil.isCode(code)){
            return AjaxResult.error("图书码借阅码输入有误！");
        }
        String key = code+"_"+getUserId();
        BrowBookCode browBookCode = (BrowBookCode)redis.getCacheObject(key);
        if ( StringUtils.isNull(browBookCode) ){
            return AjaxResult.error("图书码借阅码无效！");
        }
        if (!browBookCode.getUserId().equals(getUserId())){
            return AjaxResult.error("不能使用别人的图书码借阅码！");
        }
        BookStockVO vo = bookStockService.selectBookStock(code);
        if (StringUtils.isNull(vo)){
            return AjaxResult.error("来晚了~~，这本书已经下架了！目前无法查看！");
        }
        return AjaxResult.success(vo);
    }
    /**
     * 根据借阅码借阅图书
     */
    @GetMapping("/brow/{code}")
    @ApiOperation(value ="查询图书库存信息列表",notes = "查询图书库存信息列表")
    public AjaxResult brow(@PathVariable("code")String code) {
        if (StringUtils.isEmpty(code) || !CodeUtil.isCode(code)){
            return AjaxResult.error("图书码借阅码输入有误！");
        }
        String key = code+"_"+getUserId();
        BrowBookCode browBookCode = (BrowBookCode)redis.getCacheObject(key);
        if ( StringUtils.isNull(browBookCode) ){
            return AjaxResult.error("图书码借阅码无效！");
        }
        if (!browBookCode.getUserId().equals(getUserId())){
            return AjaxResult.error("不能使用别人的图书码借阅码！");
        }

        AjaxResult result = bookStockService.getBookStock(code,getUserId());
        if (Boolean.TRUE.equals(result.get(AjaxResult.DATA_TAG))){
            redis.deleteObject(key);
        }
        return result;
    }
    /**
     * 导出图书库存信息列表
     */
    @PreAuthorize("@ss.hasAnyRoles('libraryAdmin,admin')") //@PreAuthorize("@ss.hasPermi('userApplication:stock:export')")
    @Log(title = "图书库存信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ApiOperation(value ="导出图书库存信息列表",notes = "导出图书库存信息列表")
    public void export(HttpServletResponse response, BookStock bookStock) {
        List<BookStock> list = bookStockService.selectBookStockList(bookStock);
        ExcelUtil<BookStock> util = new ExcelUtil<BookStock>(BookStock.class);
        util.exportExcel(response, list, "图书库存信息数据");
    }

    /**
     * 获取图书库存信息详细信息
     */
    @PreAuthorize("@ss.hasAnyRoles('libraryAdmin,admin')") //@PreAuthorize("@ss.hasPermi('userApplication:stock:query')")
    @GetMapping(value = "/{id}")
    @ApiOperation(value ="获取图书库存信息详细信息",notes = "获取图书库存信息详细信息")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(bookStockService.selectBookStockById(id));
    }

    /**
     * 新增图书库存信息
     */
    @PreAuthorize("@ss.hasAnyRoles('libraryAdmin,admin')") //@PreAuthorize("@ss.hasPermi('userApplication:stock:add')")
    @Log(title = "图书库存信息", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation(value ="新增图书库存信息",notes = "新增图书库存信息")
    public AjaxResult add(@RequestBody BookStock bookStock) {
        bookStock.setCreatePersonal(getUserId());
        bookStock.setCreateTime(new Date());
        bookStock.setId(bookStock.getSnowID());
        return toAjax(bookStockService.insertBookStock(bookStock));
    }

    /**
     * 修改图书库存信息
     */
    @PreAuthorize("@ss.hasAnyRoles('libraryAdmin,admin')") //@PreAuthorize("@ss.hasPermi('userApplication:stock:edit')")
    @Log(title = "图书库存信息", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation(value ="修改图书库存信息",notes = "修改图书库存信息")
    public AjaxResult edit(@RequestBody BookStock bookStock) {
        bookStock.setUpdatePersonal(getUserId());
        bookStock.setUpdateTime(new Date());
        return toAjax(bookStockService.updateBookStock(bookStock));
    }

    /**
     * 删除图书库存信息
     */
    @PreAuthorize("@ss.hasAnyRoles('libraryAdmin,admin')") //@PreAuthorize("@ss.hasPermi('userApplication:stock:remove')")
    @Log(title = "图书库存信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    @ApiOperation(value ="删除图书库存信息",notes = "删除图书库存信息")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(bookStockService.deleteBookStockByIds(ids));
    }
}
