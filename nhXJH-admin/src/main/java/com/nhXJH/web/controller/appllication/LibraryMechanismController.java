package com.nhXJH.web.controller.appllication;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.nhXJH.common.core.redis.RedisCache;
import com.nhXJH.common.enums.application.StatusEnums;
import com.nhXJH.common.enums.application.TableTypeEnums;
import com.nhXJH.web.domain.BaseFile;
import com.nhXJH.web.domain.LibraryMechanism;
import com.nhXJH.web.domain.param.GetFileParam;
import com.nhXJH.web.domain.param.MechanismParam;
import com.nhXJH.web.domain.param.RefulshMechaismFileParam;
import com.nhXJH.web.domain.vo.FileVo;
import com.nhXJH.web.domain.vo.GetFileVO;
import com.nhXJH.web.domain.vo.MechanismVO;
import com.nhXJH.web.service.IBaseFileService;
import com.nhXJH.web.service.ILibraryMechanismService;
import com.nhXJH.web.util.tokenizer.Tokenizer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
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
 * 图书管理机构信息Controller
 * 
 * @author xjh
 * @date 2022-01-25
 */
@RestController
@RequestMapping("/userApplication/mechanism")
@Api(tags = {"图书管理机构信息"})
public class LibraryMechanismController extends BaseController {
    @Autowired
    private ILibraryMechanismService libraryMechanismService;
    @Autowired
    private IBaseFileService baseFileService;
    @Autowired
    private RedisCache redisCache;

    @Value("${redisCache.timeOut.mechanismTag:1800}")
    Long mechanismTagTimeOut;
    /**
     * 查询图书管理机构信息列表
     */
    @PreAuthorize("@ss.hasAnyRoles('libraryAdmin,admin')") //@PreAuthorize("@ss.hasPermi('userApplication:mechanism:list')")
    @GetMapping("/list")
    @ApiOperation(value ="查询图书管理机构信息列表",notes = "查询图书管理机构信息列表")
    public TableDataInfo list(MechanismParam libraryMechanism) {
        List<LibraryMechanism> mechanisms  = libraryMechanismService.selectLibraryMechanismList(libraryMechanism);
        Integer total =  null== mechanisms ? 0:mechanisms.size();
        startPage();
        List<MechanismVO> list = libraryMechanismService.selectLibraryMechanismVOList(libraryMechanism);
        TableDataInfo info = getDataTable(list);
        info.setTotal(total);
        return info;
    }

    /**
     * 导出图书管理机构信息列表
     */
    @PreAuthorize("@ss.hasAnyRoles('libraryAdmin,admin')") //@PreAuthorize("@ss.hasPermi('userApplication:mechanism:export')")
    @Log(title = "图书管理机构信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ApiOperation(value ="导出图书管理机构信息列表",notes = "导出图书管理机构信息列表")
    public void export(HttpServletResponse response, MechanismParam libraryMechanism) {
        List<LibraryMechanism> list = libraryMechanismService.selectLibraryMechanismList(libraryMechanism);
        ExcelUtil<LibraryMechanism> util = new ExcelUtil<LibraryMechanism>(LibraryMechanism.class);
        util.exportExcel(response, list, "图书管理机构信息数据");
    }

    /**
     * 获取图书管理机构信息详细信息
     */
    @PreAuthorize("@ss.hasAnyRoles('libraryAdmin,admin')") //@PreAuthorize("@ss.hasPermi('userApplication:mechanism:query')")
    @GetMapping(value = "/{id}")
    @ApiOperation(value ="获取图书管理机构信息详细信息",notes = "获取图书管理机构信息详细信息")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(libraryMechanismService.selectLibraryMechanismById(id));
    }

    /**
     * 新增图书管理机构信息
     */
    @PreAuthorize("@ss.hasAnyRoles('libraryAdmin,admin')") //@PreAuthorize("@ss.hasPermi('userApplication:mechanism:add')")
    @Log(title = "图书管理机构信息", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation(value ="新增图书管理机构信息",notes = "新增图书管理机构信息")
    public AjaxResult add(@RequestBody MechanismParam libraryMechanism) {
        libraryMechanism.setCreatePersonal(getUserId());
        libraryMechanism.setCreateBy(getUsername());
        libraryMechanism.setCreateTime(new Date());
        libraryMechanism.setId(libraryMechanism.getSnowID());
        return toAjax(libraryMechanismService.insertLibraryMechanism(libraryMechanism));
    }

    /**
     * 修改图书管理机构信息
     */
    @PreAuthorize("@ss.hasAnyRoles('libraryAdmin,admin')") //@PreAuthorize("@ss.hasPermi('userApplication:mechanism:edit')")
    @Log(title = "图书管理机构信息", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation(value ="修改图书管理机构信息",notes = "修改图书管理机构信息")
    public AjaxResult edit(@RequestBody LibraryMechanism libraryMechanism) {
        libraryMechanism.setUpdatePersonal(getUserId());
        libraryMechanism.setUpdateTime(new Date());
        return toAjax(libraryMechanismService.updateLibraryMechanism(libraryMechanism));
    }

    /**
     * 删除图书管理机构信息
     */
    @PreAuthorize("@ss.hasAnyRoles('libraryAdmin,admin')") //@PreAuthorize("@ss.hasPermi('userApplication:mechanism:remove')")
    @Log(title = "图书管理机构信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    @ApiOperation(value ="删除图书管理机构信息",notes = "删除图书管理机构信息")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(libraryMechanismService.deleteLibraryMechanismByIds(ids));
    }

    /**
     * 修改信息状态
     */
    @PreAuthorize("@ss.hasAnyRoles('libraryAdmin,admin')") //@PreAuthorize("@ss.hasPermi('userApplication:mechanism:modify')")
    @Log(title = "修改实体信息状态", businessType = BusinessType.UPDATE)
    @PutMapping("/changeStatus")
    @ApiOperation(value ="修改实体信息状态",notes = "修改实体信息状态")
    public AjaxResult changeStatus(@RequestBody LibraryMechanism mechanism){
//        userService.checkUserAllowed();
        mechanism.setUpdateBy(getUsername());
        mechanism.setUpdatePersonal(getUserId());
        return toAjax(libraryMechanismService.updateUserStatus(mechanism));
    }


    /**
     * 查询附件实体对应信息列表
     */
    @PreAuthorize("@ss.hasAnyRoles('libraryAdmin,admin')") //@PreAuthorize("@ss.hasPermi('userApplication:file:list')")
    @Log(title = "查询附件实体对应信息列表", businessType = BusinessType.OTHER)
    @GetMapping("/getFile/{id}")
    @ApiOperation(value ="查询附件实体对应信息列表",notes = "查询附件实体对应信息列表")
    public AjaxResult getFile(@PathVariable("id") Long id) {
        BaseFile baseFile = new BaseFile();
        baseFile.setModelId(id);
        baseFile.setDbId(TableTypeEnums.MECHANISM.getCode());
        List<FileVo> list = baseFileService.getFile(baseFile);
        List<String> fileSrc = new ArrayList<>();
        list.forEach(src->{fileSrc.add(src.getUrl());});
        return AjaxResult.success(fileSrc);
    }

    /**
     * 获取附件对应信息
     */
    @Log(title = "获取附件对应信息", businessType = BusinessType.OTHER)
    @PostMapping("/getFileList")
    @ApiOperation(value ="获取附件对应信息",notes = "获取附件对应信息")
    public AjaxResult getFileList(@RequestBody GetFileParam fileParam) {
        fileParam.setDbId(TableTypeEnums.MECHANISM.getCode());
        List<GetFileVO> list = new ArrayList<>();
        list = baseFileService.getFileList(fileParam);
        return AjaxResult.success(list);
    }

    /**
     * 分析字符串获取关键词
     */
    @Log(title = "分析字符串获取关键词", businessType = BusinessType.UPDATE)
    @PostMapping("/analysisStr")
    @ApiOperation(value ="分析字符串获取关键词",notes = "分析字符串获取关键词")
    public AjaxResult analysisStr(@RequestBody LibraryMechanism mechanism) {
        if (null==mechanism.getId()){
            return AjaxResult.error("获取失败！");
        }
        String tagKey = "mechanismTag_"+mechanism.getId()+"_"+TableTypeEnums.MECHANISM.getCode();

        //从redis缓存中获取
        List<String> tags = redisCache.getCacheList(tagKey);

        //没有获取到
        if (null == tags || tags.size()<=0) {
            // 先查询信息
            mechanism.setStatus(StatusEnums.ONE.getDesc());
            LibraryMechanism mechanismItem = libraryMechanismService.selectLibraryMechanismById(mechanism.getId());
            String mark = mechanismItem.getMark();

            //生成关键字列表
            tags = Tokenizer.getTokenizerValue(mark);
            if(null == tags || tags.size()<=0){
                tags = new ArrayList<>();
                tags.add("暂无");
            }
            //添加关键字到redis
            redisCache.setCacheList(tagKey,tags);
            //设置过期时间，默认1800秒
            redisCache.expire(tagKey,this.mechanismTagTimeOut);
        }
        return AjaxResult.success(tags);
    }

    /**
     * 更新机构图片资源
     */
    @Log(title = "更新机构图片资源", businessType = BusinessType.UPDATE)
    @PostMapping("/reflushFile")
    @ApiOperation(value ="更新机构图片资源",notes = "更新机构图片资源")
    public AjaxResult reflushFile(@RequestBody List<RefulshMechaismFileParam> mechanism, @RequestParam("id")Long id) {
        BaseFile file = new BaseFile();
        file.setModelId(id);
        file.setCreatePerson(getUserId());
        file.setCreateTime(null);
        file.setUpdatePerson(-1l);
        file.setUpdateTime(null);
        return AjaxResult.success(libraryMechanismService.reflushFile(mechanism,file));
    }
}
