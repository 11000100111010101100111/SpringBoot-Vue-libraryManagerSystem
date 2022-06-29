package com.nhXJH.web.service.file.impl;

import com.nhXJH.common.utils.file.FileUploadUtils;
import com.nhXJH.common.utils.uuid.UUID;
import com.nhXJH.enums.FileTypeEnums;
import com.nhXJH.web.core.config.file.MinioConfig;
//import com.nhXJH.system.domain.AttachFile;
import com.nhXJH.web.domain.AttachFile;
import com.nhXJH.web.domain.param.FileKey;
import com.nhXJH.web.service.file.FileHandel;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.*;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: xjh
 * Date: 2022/1/17
 * Time: 14:36
 **/
@Service("miniIOServerImpl")
public class MiniIOServerImpl implements FileHandel {
    @Autowired
    private MinioConfig minioConfig;

    @Autowired
    private MinioClient client;
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
            throws IOException,
            ServerException,
            InsufficientDataException,
            InternalException,
            InvalidResponseException,
            InvalidKeyException,
            NoSuchAlgorithmException,
            XmlParserException, ErrorResponseException {
        //TODO 校验附件上传权限 fileToken


        String fileName = FileUploadUtils.extractFilename(file);
        PutObjectArgs args = PutObjectArgs.builder()
                .bucket(minioConfig.getBucketName())
                .object(fileName)
                .stream(file.getInputStream(), file.getSize(), -1)
                .contentType(file.getContentType())
                .build();
        client.putObject(args);

        String url = minioConfig.getUrl() + "/" + minioConfig.getBucketName() + "/" + fileName;
        Date createTime = new Date();
        String newFileName = FilenameUtils.getExtension(file.getOriginalFilename());

        AttachFile fPo = new AttachFile();
        fPo.setName(fileName);
        fPo.setNewName(newFileName);
        fPo.setUrl(url);
        fPo.setCreateTime(createTime);
        fPo.setGroudId( null == groupId ? UUID.randomUUID().getLeastSignificantBits() : groupId );

        return fPo;
    }
}
