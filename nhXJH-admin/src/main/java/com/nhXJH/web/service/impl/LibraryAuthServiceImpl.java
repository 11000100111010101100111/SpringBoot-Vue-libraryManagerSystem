package com.nhXJH.web.service.impl;

import java.util.*;

import com.nhXJH.common.core.domain.entity.SysUser;
import com.nhXJH.common.enums.application.StatusEnums;
import com.nhXJH.common.enums.application.TableTypeEnums;
import com.nhXJH.enums.FileTypeEnums;
import com.nhXJH.system.mapper.SysUserMapper;
import com.nhXJH.web.domain.AttachFile;
import com.nhXJH.web.domain.BaseFile;
import com.nhXJH.web.domain.LibraryAuth;
import com.nhXJH.common.utils.DateUtils;
import com.nhXJH.web.domain.param.AuthParam;
import com.nhXJH.web.domain.vo.AuthVO;
import com.nhXJH.web.mapper.BaseFileMapper;
import com.nhXJH.web.service.IBaseFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nhXJH.web.mapper.LibraryAuthMapper;
import com.nhXJH.web.service.ILibraryAuthService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 作信息者Service业务层处理
 * 
 * @author xjh
 * @date 2022-01-25
 */
@Service
public class LibraryAuthServiceImpl extends ServiceImpl<LibraryAuthMapper, LibraryAuth>
        implements ILibraryAuthService  {
    @Autowired
    private LibraryAuthMapper libraryAuthMapper;
    @Autowired
    private IBaseFileService baseFileMapper;
    @Autowired
    private BaseFileMapper mapper;
    @Autowired
    private SysUserMapper userMapper;
    /**
     * 查询作信息者
     * 
     * @param id 作信息者主键
     * @return 作信息者
     */
//    @Override
//    public LibraryAuth selectLibraryAuthById(Long id) {
//        return libraryAuthMapper.selectLibraryAuthById(id);
//    }
    @Override
    public AuthVO selectLibraryAuthById(Long id) {
        LibraryAuth auth = libraryAuthMapper.selectLibraryAuthById(id);

        SysUser createUser = userMapper.selectUserById(auth.getCreatePersonal());
        SysUser updateUser = userMapper.selectUserById(auth.getCreatePersonal());
        AuthVO authVO = new AuthVO(auth,createUser,updateUser);
        return authVO;
    }

    @Override
    public Integer getCount(LibraryAuth libraryAuth) {
        return libraryAuthMapper.getCount(libraryAuth);
    }

    /**
     * 查询作信息者列表
     * 
     * @param libraryAuth 作信息者
     * @return 作信息者
     */
    @Override
    public List<AuthVO> selectLibraryAuthList(LibraryAuth libraryAuth) {
        List<LibraryAuth> authList = libraryAuthMapper.selectLibraryAuthList(libraryAuth);
        //查无数据时
        if(null == authList || authList.size()<=0){
            return new ArrayList<AuthVO>();
        }

        Set<Long> idSet = new HashSet<>();
        authList.forEach(auth->{
            idSet.add(auth.getCreatePersonal());
            idSet.add(auth.getUpdatePersonal());
        });

        Long[] ids = new Long[idSet.size()];
        int index = 0;
        idSet.forEach(set->{
            ids[index] = set;
        });

        List<SysUser> users = userMapper.selectUserByIds(ids);
        Map<Long,SysUser> userMap = new HashMap<>();
        users.forEach(u->{
            userMap.put(u.getUserId(),u);
        });
        List<AuthVO> result = new ArrayList<>();
        authList.forEach(auth -> {
            result.add(
                    new AuthVO(
                            auth,
                            userMap.get(auth.getCreatePersonal()),
                            userMap.get(auth.getUpdatePersonal())
                    )
            );
        });

        return result;
    }

    /**
     * 新增作信息者
     * 
     * @param libraryAuth 作信息者
     * @return 结果
     */
    @Override
    @Transactional
    public List<BaseFile> insertLibraryAuth(AuthParam libraryAuth) {
        libraryAuth.setCreateTime(DateUtils.getNowDate());
        LibraryAuth auth = libraryAuth.getAuth();
        int result = libraryAuthMapper.insertLibraryAuth(auth);
        List<AttachFile> files = libraryAuth.getPicList();

        List<BaseFile> baseFiles = new ArrayList<>();
        if ( files != null && files.size()>0 ) {
            //新增图片信息

            for (AttachFile file : files) {
                BaseFile baseFile = new BaseFile();
                baseFile.setFileId(file.getId());
                baseFile.setDbId(TableTypeEnums.AUTH.getCode());
                baseFile.setModelId(auth.getId());
                baseFile.setCreatePerson(file.getCreatePerson());
                baseFile.setMark("作家:"+libraryAuth.getName());
                baseFile.setUrl(file.getUrl());
                baseFile.setCreateTime(file.getCreateTime());
                baseFiles.add(baseFile);
            }
            baseFileMapper.insertBaseFile(baseFiles);
        }

        return baseFiles;
    }

    /**
     * 修改作信息者
     * 
     * @param libraryAuth 作信息者
     * @return 结果
     */
    @Override
    @Transactional
    public  List<BaseFile> updateLibraryAuth(AuthParam libraryAuth) {
        libraryAuth.setUpdateTime(DateUtils.getNowDate());
        int result = libraryAuthMapper.updateLibraryAuth(libraryAuth.getAuth());
        List<AttachFile> files = libraryAuth.getPicList();

        BaseFile query = new BaseFile();
        query.setModelId(libraryAuth.getId());
        query.setDbId(TableTypeEnums.AUTH.getCode());
        query.setStatus(StatusEnums.ONE.getDesc());
        List<BaseFile> savedFile = baseFileMapper.selectBaseFileList(query);
        Long filesIds[] = new Long[ (savedFile==null) ? 0:savedFile.size()];
        for (int index = 0 ;index<filesIds.length;index++) {
            filesIds[index] = savedFile.get(index).getFileId();
        }

        //清空原有图片信息
        if (filesIds.length>0) {
            baseFileMapper.deleteBaseFileByFileIds(filesIds,libraryAuth.getUpdatePersonal());
        }

        List<BaseFile> baseFiles = new ArrayList<>();
        //添加新图片
        if (files != null && files.size()>0 ){
            //新增图片信息

            for (AttachFile file : files) {
                BaseFile baseFile = new BaseFile();
                baseFile.setFileId(file.getSnowID());
                baseFile.setDbId(TableTypeEnums.AUTH.getCode());
                baseFile.setModelId(libraryAuth.getId());
                baseFile.setCreatePerson(file.getCreatePerson());
                baseFile.setType(FileTypeEnums.PICTURE.getValue());//图片文件
                baseFile.setMark(file.getMark()==null?"":file.getMark());
                baseFile.setUrl(file.getUrl());
                baseFile.setCreateTime(file.getCreateTime());
                baseFile.setStatus(StatusEnums.ONE.getDesc());
                baseFiles.add(baseFile);
            }
            baseFileMapper.insertBaseFile(baseFiles);
        }
       return baseFiles;
    }

    @Transactional
    public int addPic(List<AttachFile> files,LibraryAuth auth) {
        if (files != null && files.size() > 0) {
            //新增图片信息
            List<BaseFile> baseFiles = new ArrayList<>();
            for (AttachFile file : files) {
                BaseFile baseFile = new BaseFile();
                baseFile.setFileId(file.getId());
                baseFile.setDbId(TableTypeEnums.AUTH.getCode());
                baseFile.setModelId(auth.getId());
                baseFile.setCreatePerson(file.getCreatePerson());
                baseFile.setMark(file.getMark());
                baseFile.setUrl(file.getUrl());
                baseFile.setCreateTime(file.getCreateTime());
                baseFiles.add(baseFile);
            }
           return baseFileMapper.insertBaseFile(baseFiles);
        }
        return 0;
    }
    /**
     * 批量删除作信息者
     * 
     * @param ids 需要删除的作信息者主键
     * @return 结果
     */
    @Override
    public int deleteLibraryAuthByIds(Long[] ids,Long operator) {
        return libraryAuthMapper.deleteLibraryAuthByIds(ids,operator);
    }

    /**
     * 删除作信息者信息
     * 
     * @param id 作信息者主键
     * @return 结果
     */
    @Override
    public int deleteLibraryAuthById(Long id,Long operator) {
        return libraryAuthMapper.deleteLibraryAuthById(id,operator);
    }

    /**
     * 修改状态
     *
     * @param auth 作者信息
     * @return 修改结果
     */
    @Override
    public int updateUserStatus(LibraryAuth auth){
        return libraryAuthMapper.updateLibraryAuth(auth);
    }

}
