package com.nhXJH.web.service.file.impl;

import com.nhXJH.common.utils.file.FileUploadUtils;
import com.nhXJH.common.utils.uuid.UUID;
import com.nhXJH.enums.FileTypeEnums;
import com.nhXJH.web.domain.AttachFile;
import com.nhXJH.web.domain.param.FileKey;
import com.nhXJH.web.service.file.FileHandel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.Date;
/**
 * Created by IntelliJ IDEA.
 * User: xjh
 * Date: 2022/1/21
 * Time: 15:00
 **/
@Service("localFileServiceImpl")
public class LocalFileServiceImpl implements FileHandel {
    @Value("${local.file.path}")
    String localpath;
    public AttachFile upload(MultipartFile file, Long groupId, FileKey fileToken, FileTypeEnums folder) throws Exception {
        String itemPath = this.localpath + (null==folder?"":"/"+folder.getDesc());

        String path = FileUploadUtils.upload(itemPath,file);

        String fileName = file.getOriginalFilename();
        String newFileName = FileUploadUtils.extractFilename(file);
        //String fileType = FileUploadUtils.getExtension(file);
        Date now = new Date();

        AttachFile fPo = new AttachFile();
        fPo.setName(fileName);
        fPo.setNewName(newFileName);
        fPo.setUrl(path);
        fPo.setType(null==folder?FileTypeEnums.PICTURE.getValue().toString():folder.getValue().toString());
        fPo.setCreateTime(now);
        fPo.setGroudId( null == groupId ? UUID.randomUUID().getLeastSignificantBits() : groupId );
        return fPo;
    }
}
