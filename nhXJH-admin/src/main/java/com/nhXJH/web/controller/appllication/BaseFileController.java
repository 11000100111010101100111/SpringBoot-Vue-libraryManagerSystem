package com.nhXJH.web.controller.appllication;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONArray;
import com.nhXJH.web.domain.BaseFile;
import com.nhXJH.web.domain.param.BaseFileParam;
import com.nhXJH.web.domain.param.GetFileParam;
import com.nhXJH.web.domain.vo.FileVo;
import com.nhXJH.web.domain.vo.GetFileVO;
import com.nhXJH.web.service.IBaseFileService;
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
 * 附件实体对应信息Controller
 * 
 * @author xjh
 * @date 2022-01-25
 */
@RestController
@RequestMapping("/userApplication/file")
@Api(tags = {"附件实体对应信息"})
public class BaseFileController extends BaseController {
    @Autowired
    private IBaseFileService baseFileService;

    @Value("${nhXJH.profile}")
    private String pathSuff;
    /**
     * 查询附件实体对应信息列表
     */
    @PreAuthorize("@ss.hasPermi('userApplication:file:list')")
    @GetMapping("/list")
    @ApiOperation(value ="查询附件实体对应信息列表",notes = "查询附件实体对应信息列表")
    public TableDataInfo list(BaseFile baseFile) {
        startPage();
        List<BaseFile> list = baseFileService.selectBaseFileList(baseFile);
        return getDataTable(list);
    }

    /**
     * 查询附件实体对应信息列表
     */
    @PreAuthorize("@ss.hasPermi('userApplication:file:list')")
    @GetMapping("/getFile")
    @ApiOperation(value ="查询附件实体对应信息列表",notes = "查询附件实体对应信息列表")
    public AjaxResult getFile(BaseFile baseFile) {
        List<FileVo> list = baseFileService.getFile(baseFile);
        return AjaxResult.success(list);
    }
    /**
     * 导出附件实体对应信息列表
     */
    @PreAuthorize("@ss.hasPermi('userApplication:file:export')")
    @Log(title = "附件实体对应信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ApiOperation(value ="导出附件实体对应信息列表",notes = "导出附件实体对应信息列表")
    public void export(HttpServletResponse response, BaseFile baseFile) {
        List<BaseFile> list = baseFileService.selectBaseFileList(baseFile);
        ExcelUtil<BaseFile> util = new ExcelUtil<BaseFile>(BaseFile.class);
        util.exportExcel(response, list, "附件实体对应信息数据");
    }

    /**
     * 获取附件实体对应信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('userApplication:file:query')")
    @GetMapping(value = "/{fileId}")
    @ApiOperation(value ="获取附件实体对应信息详细信息",notes = "获取附件实体对应信息详细信息")
    public AjaxResult getInfo(@PathVariable("fileId") Long fileId) {
        return AjaxResult.success(baseFileService.selectBaseFileByFileId(fileId));
    }

    /**
     * 新增附件实体对应信息
     */
    @PreAuthorize("@ss.hasPermi('userApplication:file:add')")
    @Log(title = "附件实体对应信息", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation(value ="新增附件实体对应信息",notes = "新增附件实体对应信息")
    public AjaxResult add(@RequestBody BaseFile baseFile) {
        List<BaseFile> files = new ArrayList<>();
        baseFile.setCreateTime(new Date());
        baseFile.setCreatePerson(getUserId());
        files.add(baseFile);
        return toAjax(baseFileService.insertBaseFile(files));
    }
    /**
     * 新增附件实体对应信息
     */
    @PreAuthorize("@ss.hasPermi('userApplication:file:add')")
    @Log(title = "附件实体对应信息", businessType = BusinessType.INSERT)
    @PostMapping("/insertList")
    @ApiOperation(value ="新增附件实体对应信息",notes = "新增附件实体对应信息")
    public AjaxResult insertList(@RequestParam("fileList") String fileList) {
        List<BaseFile> list = JSONArray.parseArray(fileList,BaseFile.class);
        return toAjax(baseFileService.insertBaseFile(list));
    }
    /**
     * 修改附件实体对应信息
     */
    @PreAuthorize("@ss.hasPermi('userApplication:file:edit')")
    @Log(title = "附件实体对应信息", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation(value ="修改附件实体对应信息",notes = "修改附件实体对应信息")
    public AjaxResult edit(@RequestBody BaseFile baseFile) {
        baseFile.setUpdatePerson(getUserId());
        baseFile.setUpdateTime(new Date());
        return toAjax(baseFileService.updateBaseFile(baseFile));
    }

    /**
     * 删除附件实体对应信息
     */
    @PreAuthorize("@ss.hasPermi('userApplication:file:remove')")
    @Log(title = "附件实体对应信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{fileIds}")
    @ApiOperation(value ="删除附件实体对应信息",notes = "删除附件实体对应信息")
    public AjaxResult remove(@PathVariable Long[] fileIds) {
        return toAjax(baseFileService.deleteBaseFileByFileIds(fileIds,getUserId()));
    }
    /**
     * 删除附件实体对应信息
     */
    @PreAuthorize("@ss.hasPermi('userApplication:file:remove')")
    @Log(title = "附件实体对应信息", businessType = BusinessType.DELETE)
    @PostMapping("/del/{fileId}")
    @ApiOperation(value ="删除附件实体对应信息",notes = "删除附件实体对应信息")
    public AjaxResult del(@PathVariable Long fileId) {
        return toAjax(baseFileService.deleteBaseFileByFileId(fileId,getUserId()));
    }
    /**
     * 分析字符串获取关键词
     */
    @Log(title = "分析字符串获取关键词", businessType = BusinessType.UPDATE)
    @PostMapping("/analysisStr")
    @ApiOperation(value ="删除附件实体对应信息",notes = "删除附件实体对应信息")
    public AjaxResult analysisStr(@RequestBody String str) {
        List<String> list = new ArrayList<>();
        list = Tokenizer.getTokenizerValue(str);
        return AjaxResult.success(list);
    }
    /**
     * 获取附件对应信息
     */
    @Log(title = "获取附件对应信息", businessType = BusinessType.OTHER)
    @PostMapping("/getFileList")
    @ApiOperation(value ="获取附件对应信息",notes = "获取附件对应信息")
    public AjaxResult getFileList(@RequestBody GetFileParam fileParam) {
        List<GetFileVO> list = new ArrayList<>();
        list = baseFileService.getFileList(fileParam);
        return AjaxResult.success(list);
    }
    /**
     * 文件下载
     */
    @Log(title = "文件下载", businessType = BusinessType.OTHER)
    @PostMapping("/downloadFile")
    @ApiOperation(value ="文件下载",notes = "文件下载")
    public void getFileList(HttpServletResponse response, FileVo vo) throws Exception{
        String url = vo.getUrl();
        if (url.startsWith("/")){
            url = url.substring(1);
        }
        String[] arr = url.split("/");
        // 下载本地文件
        String fileName = arr[arr.length-1]; // 文件的默认保存名
        // 读到流中
        StringBuilder uri = new StringBuilder();
        for (int i = 2; i < arr.length; i++) {
            uri.append('/').append(arr[i]);
        }
        InputStream inStream = new FileInputStream(pathSuff+uri.toString());// 文件的存放路径
        // 设置输出的格式
        response.reset();
        response.setContentType("bin");
        response.addHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
        // 循环取出流中的数据
        byte[] b = new byte[100];
        int len;
        try {
            while ((len = inStream.read(b)) > 0)
                response.getOutputStream().write(b, 0, len);
            inStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 获取文件存取路径前缀
     */
    @Log(title = "获取文件存取路径前缀", businessType = BusinessType.OTHER)
    @GetMapping("/getPath")
    @ApiOperation(value ="获取文件存取路径前缀",notes = "获取文件存取路径前缀")
    public AjaxResult getPath() {
        return AjaxResult.success("文件存取路径前缀",pathSuff);
    }
}
