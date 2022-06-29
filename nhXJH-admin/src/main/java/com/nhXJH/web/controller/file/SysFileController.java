package com.nhXJH.web.controller.file;

import com.nhXJH.common.core.controller.BaseController;
import com.nhXJH.common.core.domain.AjaxResult;
import com.nhXJH.enums.EnumUtils;
import com.nhXJH.enums.FileTypeEnums;
import com.nhXJH.web.domain.AttachFile;
import com.nhXJH.web.service.file.AttachFileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.nhXJH.web.domain.param.FileKey;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * (SysFile)表控制层
 *
 * @author makejava
 * @since 2022-01-17 14:23:33
 */
@RestController
@RequestMapping("/sysFile")
@Api(tags = "文件模块")
public class SysFileController  extends BaseController {
    /**
     * 服务对象
     */
    @Resource
    private AttachFileService sysFileService;

    @Value("${file.account}")
    String account;
    @Value("${file.key}")
    String key;
    /**
     * 分页查询
     *
     * @param sysFile 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @GetMapping
    public AjaxResult queryByPage(AttachFile sysFile, PageRequest pageRequest) {
        return AjaxResult.success(this.sysFileService.queryByPage(sysFile, pageRequest));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public AjaxResult queryById(@PathVariable("id") Long id) {
        return AjaxResult.success(this.sysFileService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param sysFile 实体
     * @return 新增结果
     */
    @PostMapping
    public AjaxResult add(AttachFile sysFile) {
        sysFile.setCreateTime(new Date());
        sysFile.setId(sysFile.getSnowID());
        sysFile.setCreatePerson(getUserId());
        return AjaxResult.success(this.sysFileService.insert(sysFile));
    }

    /**
     * 编辑数据
     *
     * @param sysFile 实体
     * @return 编辑结果
     */
    @PutMapping
    public AjaxResult edit(AttachFile sysFile) {
        sysFile.setUpdatePersonal(getUserId());
        sysFile.setUpdateTime(new Date());
        return AjaxResult.success(this.sysFileService.update(sysFile));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping
    public AjaxResult deleteById(Long id) {
        return AjaxResult.success(this.sysFileService.deleteById(id));
    }

    /**
     * 多文件上传，保存实体信息
     *
     * @param files 文件实体
     * @return SysFile实例对象集
     */
    @PostMapping("/upload")
    @PreAuthorize("@ss.hasRole('user')")
    @CrossOrigin
    @ApiOperation(value = "文件上传",notes = "多文件上传，保存实体信息")
    public AjaxResult upload(
            @RequestParam(value = "files",required=false)
            @ApiParam(value = "文件列表",name = "files") MultipartFile[] files
        , @ApiParam(value = "访问秘钥和账号实体",name = "fileToken") FileKey fileToken){
        return AjaxResult.success(sysFileService.upload(files,fileToken));
    }

    /**
     * 多文件上传，保存实体信息
     *
     * @param file 文件实体
     * @return SysFile实例对象集
     */
    @PostMapping("/uploadType")
    @PreAuthorize("@ss.hasRole('user')")
    @CrossOrigin
    @ApiOperation(value = "文件上传",notes = "多文件上传，保存实体信息")
    public AjaxResult uploadType(
            @RequestParam(value = "file",required=false)
            @ApiParam(value = "文件列表",name = "file") MultipartFile file
          , @ApiParam(value = "访问秘钥和账号实体",name = "fileToken") FileKey fileToken,
            @RequestParam("fileType")@ApiParam(value = "文件类型",name = "fileType") String fileType){
        FileTypeEnums fileTypeEnum = EnumUtils.getEnumByDesc(FileTypeEnums.class.getEnumConstants(),fileType);
        return AjaxResult.success(sysFileService.upload(new  MultipartFile[]{file},fileToken,fileTypeEnum));
    }
    /**
     * 确认文件
     *
     * @param ids 文件实体
     * @return 是否更新成功
     */
    @PostMapping("/ack")
    @PreAuthorize("@ss.hasRole('user')")
    @ApiOperation(value = "文件确认",notes = "集合形式上传文件的id")
    public AjaxResult ack(@RequestParam(value = "fileIDs",required=false) @ApiParam(value = "文件is",name = "fileIDs") List<Long> ids){
        return AjaxResult.success(sysFileService.ack(ids));
    }
}

