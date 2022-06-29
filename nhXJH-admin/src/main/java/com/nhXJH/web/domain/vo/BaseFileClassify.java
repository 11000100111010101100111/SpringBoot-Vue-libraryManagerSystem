package com.nhXJH.web.domain.vo;

import com.nhXJH.enums.FileTypeEnums;
import com.nhXJH.web.domain.BaseFile;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: xjh
 * Date: 2022/2/25
 * Time: 13:49
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseFileClassify {
    @ApiModelProperty("url开头")
    private String urlHead;
    @ApiModelProperty("相关图片附件信息")
    private List<BaseFile> picFile;
    @ApiModelProperty("相关文档附件信息")
    private List<BaseFile> docFile;
    @ApiModelProperty("相关其他附件信息")
    private List<BaseFile> pdfFile;
    @ApiModelProperty("相关视频附件信息")
    private List<BaseFile> videoFile;
    @ApiModelProperty("相关其他附件信息")
    private List<BaseFile> otherFile;

    public  BaseFileClassify(List<BaseFile> fileList){
        this.picFile = new ArrayList<>();
        this.docFile = new ArrayList<>();
        this.pdfFile = new ArrayList<>();
        this.videoFile = new ArrayList<>();
        this.otherFile = new ArrayList<>();
        if (null != fileList) {
            for (BaseFile file : fileList) {
                if (file.getType().equals(FileTypeEnums.PICTURE.getValue())) {
                    this.picFile.add(file);
                } else if (file.getType().equals(FileTypeEnums.DOC.getValue())) {
                    this.docFile.add(file);
                } else if (file.getType().equals(FileTypeEnums.VIDEO.getValue())) {
                    this.videoFile.add(file);
                } else if (file.getType().equals(FileTypeEnums.PDF.getValue())) {
                    this.pdfFile.add(file);
                } else {
                    this.otherFile.add(file);
                }
            }
        }
    }
}
