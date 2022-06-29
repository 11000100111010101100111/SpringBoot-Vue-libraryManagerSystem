package com.nhXJH.web.service.file.impl;

import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.nhXJH.common.utils.uuid.UUID;
import com.nhXJH.enums.FileTypeEnums;
import com.nhXJH.web.domain.AttachFile;
import com.nhXJH.web.domain.param.FileKey;
import com.nhXJH.web.service.file.FileHandel;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * FastDFS 文件存储
 *
 * @author xjh
 */
@Primary
@Service("fastDfsSysFileServiceImpl")
//@RefreshScope
public class FastDfsSysFileServiceImpl implements FileHandel {
    /**
     * 域名或本机访问地址
     */
    @Value("${fdfs.domain}")//:http:\192.168.20.240
    public String domain;

    @Autowired
    private FastFileStorageClient storageClient;

    public MultipartFile downloadFile(String fileId) {
        MultipartFile file = null;

//        SysAttachFile sysAttachFile = sysAttachFileMapper.selectById(fileId);
//        if (null == sysAttachFile.getPath()){
//            throw new ServiceException("查无此文件");
//        }
//
//        file = storageClient.downloadFile("file",sysAttachFile.getPath(),null);
//
//        if(null == file){
//            throw new ServiceException("文件路径有误");
//        }
        return file;
    }
    /**
     * 单文件上传
     *
     * **/
//    public AttachFile upload(MultipartFile file, FileKey fileToken)
//            throws Exception {
//        return this.upload(file,null,fileToken);
//    }

    /**
     * 多文件上传
     *
     * **/
//    public List<AttachFile> upload(MultipartFile[] files, FileKey fileToken) throws Exception {
//        List<AttachFile> fPoList = new ArrayList<>();
//        Long groupID = UUID.randomUUID().getLeastSignificantBits();
//        for (MultipartFile file : files) {
//            fPoList.add(this.upload(file,groupID,fileToken));
//        }
//        return fPoList;
//    }
    public AttachFile upload(MultipartFile file, Long groupId, FileKey fileToken, FileTypeEnums folder)
            throws IOException {
        //TODO 校验附件上传权限 fileToken

        String fileName = file.getName();
        Date createTime = new Date();
        String newFileName = FilenameUtils.getExtension(file.getOriginalFilename());
        //String dom = domain;
        StorePath storePath = storageClient.uploadFile(file.getInputStream(), file.getSize(), newFileName , null);

        String domainPath = domain + "/" + storePath.getFullPath();
        AttachFile fPo = new AttachFile();
        fPo.setName(fileName);
        fPo.setNewName(newFileName);
        fPo.setUrl(domainPath);
        fPo.setCreateTime(createTime);
        fPo.setGroudId( null == groupId ? UUID.randomUUID().getLeastSignificantBits() : groupId );

        return fPo;
    }
}
