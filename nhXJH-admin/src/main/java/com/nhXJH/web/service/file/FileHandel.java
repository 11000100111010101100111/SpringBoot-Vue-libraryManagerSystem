package com.nhXJH.web.service.file;

import com.nhXJH.common.exception.ServiceException;
import com.nhXJH.common.utils.uuid.UUID;
import com.nhXJH.enums.FileTypeEnums;
import com.nhXJH.web.domain.AttachFile;
import com.nhXJH.web.domain.param.FileKey;
import org.springframework.web.multipart.MultipartFile;

import javax.print.attribute.standard.MediaSize;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: xjh
 * Date: 2022/1/17
 * Time: 14:36
 **/
public interface FileHandel {
    /**单文件上传**/
    public default AttachFile upload(MultipartFile file, FileKey fileToken)
            throws Exception {
        return this.upload(file,null,fileToken,null);
    }
    /**多文件上传 **/
    public default List<AttachFile> upload(MultipartFile[] files, FileKey fileToken) throws Exception {
        return this.upload(files,fileToken,null);
    }
    /**多文件上传 定义文件夹**/
    public default List<AttachFile> upload(MultipartFile[] files, FileKey fileToken, FileTypeEnums folder) throws Exception {
        if(null == files || files.length<1){
            throw new ServiceException("提交无效文件信息");
        }
        List<AttachFile> fPoList = new ArrayList<>();
        Long groupID = UUID.randomUUID().getLeastSignificantBits();
        for (MultipartFile file : files) {
            fPoList.add(this.upload(file,groupID,fileToken,folder));
        }
        return fPoList;
    }
    /**
     *处理文件上传。实现类可重写
     *
     * **/
     AttachFile upload(MultipartFile file, Long groupId, FileKey fileToken, FileTypeEnums folder) throws Exception ;
}
