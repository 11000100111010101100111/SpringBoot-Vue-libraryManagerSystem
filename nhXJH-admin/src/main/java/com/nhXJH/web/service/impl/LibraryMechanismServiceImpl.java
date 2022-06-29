package com.nhXJH.web.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

import com.nhXJH.common.core.domain.entity.SysDept;
import com.nhXJH.common.core.domain.entity.SysUser;
import com.nhXJH.common.enums.application.StatusEnums;
import com.nhXJH.common.enums.application.TableTypeEnums;
import com.nhXJH.common.exception.ServiceException;
import com.nhXJH.system.mapper.SysDeptMapper;
import com.nhXJH.system.mapper.SysUserMapper;
import com.nhXJH.web.domain.BaseFile;
import com.nhXJH.web.domain.LibraryMechanism;
import com.nhXJH.common.utils.DateUtils;
import com.nhXJH.web.domain.param.MechanismParam;
import com.nhXJH.web.domain.param.RefulshMechaismFileParam;
import com.nhXJH.web.domain.vo.AuthVO;
import com.nhXJH.web.domain.vo.FileVo;
import com.nhXJH.web.domain.vo.MechanismVO;
import com.nhXJH.web.domain.vo.RefulshMechaismFileVO;
import com.nhXJH.web.mapper.BaseFileMapper;
import com.nhXJH.web.util.file.FileUploadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nhXJH.web.mapper.LibraryMechanismMapper;
import com.nhXJH.web.service.ILibraryMechanismService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 图书管理机构信息Service业务层处理
 * 
 * @author xjh
 * @date 2022-01-25
 */
@Service
public class LibraryMechanismServiceImpl extends ServiceImpl<LibraryMechanismMapper, LibraryMechanism>
        implements ILibraryMechanismService  {
    @Autowired
    private LibraryMechanismMapper libraryMechanismMapper;
    @Autowired
    private SysUserMapper userMapper;
    @Autowired
    private SysDeptMapper deptMapper;
    @Autowired
    private BaseFileMapper fileMapper;

    /**npm
     * 查询图书管理机构信息
     * 
     * @param id 图书管理机构信息主键
     * @return 图书管理机构信息
     */
    @Override
    public MechanismVO selectLibraryMechanismById(Long id) {
        LibraryMechanism mechanism = libraryMechanismMapper.selectLibraryMechanismById(id);
        if (null == mechanism){
            return null;
        }
        MechanismVO mechanismVO = new MechanismVO();
        mechanismVO.setMechanism(mechanism);

        SysUser cUser = userMapper.selectUserById(mechanism.getCreatePersonal());
        SysUser uUser = userMapper.selectUserById(mechanism.getUpdatePersonal());
        SysDept dept = deptMapper.selectDeptById(mechanism.getDeptId());

        mechanismVO.setCreateUser(cUser);
        mechanismVO.setUpdateUser(uUser);
        mechanismVO.setDept(dept);
        return mechanismVO;
    }

    @Override
    public Integer getCount(MechanismParam libraryMechanism) {
        return libraryMechanismMapper.getCount(libraryMechanism);
    }

    /**
     * 查询图书管理机构信息列表
     *
     * @param libraryMechanism 图书管理机构信息
     * @return 图书管理机构信息
     */
    @Override
    public List<LibraryMechanism> selectLibraryMechanismList(MechanismParam libraryMechanism) {
        return libraryMechanismMapper.selectLibraryMechanismList(libraryMechanism);
    }
    /**
     * 查询图书管理机构信息列表
     *
     * @param libraryMechanism 图书管理机构信息
     * @return 图书管理机构信息
     */
    public List<MechanismVO> selectLibraryMechanismVOList(MechanismParam libraryMechanism){
        List<LibraryMechanism> mechanismList = libraryMechanismMapper.selectLibraryMechanismList(libraryMechanism);
        List<MechanismVO> voList = new ArrayList<>();

        //查无数据时
        if(null == mechanismList || mechanismList.size()<=0){
            return voList;
        }
        //获取创建人、更新人ID集合
//        Method[] methods = new Method[2];
//        Long[] userIds = new Long[1];
//        try {
//            methods[0] = LibraryMechanism.class.getMethod("getCreatePerson");
//            methods[1] = LibraryMechanism.class.getMethod("getUpdatePerson");
//            userIds = (Long[]) this.getParamArray(mechanismList,methods,LibraryMechanism.class);
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        }
//
//        //获取用户信息列表
//        List<SysUser> users = userMapper.selectUserByIds(userIds);
//        Method getMapMethod = null;
//        Map<Object,Object> userMap = new HashMap<>();
//        try {
//            getMapMethod = SysUser.class.getMethod("getUserId");
//            userMap = this.getObjectMap(users,SysUser.class,getMapMethod);
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        }
//        Map<Object, Object> finalUserMap = userMap;
//        //添加创建人、关系人信息到返回结果集
//        mechanismList.forEach(mechanism -> {
//            MechanismVO vo = new MechanismVO();
//            vo.setUpdateUser((SysUser) finalUserMap.get(mechanism.getCreatePerson()));
//            vo.setCreateUser((SysUser) finalUserMap.get(mechanism.getUpdatePerson()));
//            vo.setMechanism(mechanism);
//            voList.add( vo );
//        });


//        //获取部门id集
//        Method[] deptMethod = new Method[1];
//        Long[] deptIds = new Long[1];
//        try {
//            deptMethod[0] = LibraryMechanism.class.getMethod("getDeptId");
//            deptIds = (Long[]) this.getParamArray(mechanismList,deptMethod,LibraryMechanism.class);
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        }
//
//        //获取部门信息列表
//        List<SysDept> depts = deptMapper.selectDeptByIds(deptIds);
//        Map<Object,Object> deptMap = new HashMap<>();
//        try {
//            getMapMethod = SysUser.class.getMethod("getUserId");
//            deptMap = this.getObjectMap(depts,SysDept.class,getMapMethod);
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        }
//        Map<Object, Object> finalDeptMap = deptMap;
//        //添加部门信息到返回结果集
//        voList.forEach(vo ->  vo.setDept((SysDept) finalDeptMap.get(vo.getDeptId())) );


        Set<Long> idSet = new HashSet<>();
        mechanismList.forEach(press->{
            idSet.add(press.getCreatePersonal());
            idSet.add(press.getUpdatePersonal());
        });

        Long[] ids = new Long[idSet.size()];
        int index = 0;
        for (Long aLong : idSet) {
            ids[index++] = aLong;
        }
        //idSet.forEach(set-> ids[index++] = set );

        List<SysUser> users = userMapper.selectUserByIds(ids);
        Map<Long,SysUser> userMap = new HashMap<>();
        users.forEach(u-> userMap.put(u.getUserId(),u) );
        mechanismList.forEach(mechanism -> {
            MechanismVO vo = new MechanismVO();
            vo.setCreateUser(userMap.get(mechanism.getCreatePersonal()));
            vo.setUpdateUser(userMap.get(mechanism.getUpdatePersonal()));
            vo.setMechanism(mechanism);
            voList.add( vo );
        });

        Set<Long> deptIdSet = new HashSet<>();
        mechanismList.forEach(press->{
            deptIdSet.add(press.getDeptId());
        });

        Long[] deptIds = new Long[deptIdSet.size()];
        int deptIndex = 0;
        for (Long aLong : deptIdSet) {
            deptIds[deptIndex++] = aLong;
        }
        //deptIdSet.forEach(set-> {deptIds[deptIndex] = set;deptIndex+=1; });

        List<SysDept> depts = deptMapper.selectDeptByIds(deptIds);
        Map<Long,SysDept> deptMap = new HashMap<>();
        depts.forEach(d-> deptMap.put(d.getDeptId(),d) );
        voList.forEach(vo -> vo.setDept(deptMap.get(vo.getDeptId())));


        return  voList;
    }
    /**
     * 新增图书管理机构信息
     * 
     * @param libraryMechanism 图书管理机构信息
     * @return 结果
     */
    @Override
    @Transactional
    public int insertLibraryMechanism(MechanismParam libraryMechanism) {
        libraryMechanism.setCreateTime(DateUtils.getNowDate());

        Integer deptNum = deptMapper.getMechanismCount(libraryMechanism.getDeptName());
        if (deptNum>0){
            throw  new ServiceException("图书管理机构名称重复");
        }

        //向部门表添加管理机构
        SysDept dept = new SysDept();
        dept.setDeptName(libraryMechanism.getDeptName());
        dept.setEmail(libraryMechanism.getEmail());
        dept.setLeader(libraryMechanism.getContacts());
        dept.setPhone(libraryMechanism.getPhone());
        dept.setStatus(StatusEnums.ZERO.getDesc());
        dept.setCreateBy(libraryMechanism.getCreateBy());
        dept.setParentId(StatusEnums.ZERO.getCode().longValue());
        dept.setAncestors(StatusEnums.ZERO.getDesc());
        dept.setOrderNum(StatusEnums.ZERO.getDesc());
        deptMapper.insertDept(dept);

        dept = deptMapper.selectDeptByName(dept.getDeptName());
        //添加管理机构信息
        libraryMechanism.setDeptId(dept.getDeptId());
        libraryMechanism.setStatus(StatusEnums.ONE.getDesc());
        LibraryMechanism mechanism = libraryMechanism.getMechanism();
        return libraryMechanismMapper.insertLibraryMechanism(mechanism);
    }

    /**
     * 修改图书管理机构信息
     * 
     * @param libraryMechanism 图书管理机构信息
     * @return 结果
     */
    @Override
    public int updateLibraryMechanism(LibraryMechanism libraryMechanism) {
        libraryMechanism.setUpdateTime(DateUtils.getNowDate());
        return libraryMechanismMapper.updateLibraryMechanism(libraryMechanism);
    }

    /**
     * 批量删除图书管理机构信息
     * 
     * @param ids 需要删除的图书管理机构信息主键
     * @return 结果
     */
    @Override
    public int deleteLibraryMechanismByIds(Long[] ids) {
        return libraryMechanismMapper.deleteLibraryMechanismByIds(ids);
    }

    /**
     * 删除图书管理机构信息信息
     * 
     * @param id 图书管理机构信息主键
     * @return 结果
     */
    @Override
    public int deleteLibraryMechanismById(Long id) {
        return libraryMechanismMapper.deleteLibraryMechanismById(id);
    }
    /**
     * 修改状态
     *
     * @param mechanism 作者信息
     * @return 修改结果
     */
    public int updateUserStatus(LibraryMechanism mechanism){
        return libraryMechanismMapper.updateLibraryMechanism(mechanism);
    }

    /**
     * 更新机构图片资源
     *
     * @param param 提交的资源信息
     * @param file 操作的实体
     * @return 修改结果
     */
    public RefulshMechaismFileVO reflushFile(List<RefulshMechaismFileParam> param, BaseFile file){
        RefulshMechaismFileVO result = new RefulshMechaismFileVO();
        if (null == param||param.size()<=0){
            //执行删除操作，清空全部相关文件信息
            param = new ArrayList<>();
        }

        result.setSubmitNum(param.size());//本次提交多少条

        BaseFile queryEntity = new BaseFile();
        queryEntity.setDbId(TableTypeEnums.MECHANISM.getCode());
        queryEntity.setModelId(file.getModelId());
        queryEntity.setStatus(StatusEnums.ONE.getDesc());
        List<FileVo> baseFiles = fileMapper.getFile(queryEntity);

        result.setAgoNum(baseFiles.size());//原本附件信息多少条

        if (null == baseFiles){
            baseFiles = new ArrayList<>();
        }

        Map<Long,FileVo> mapFileVO = new HashMap<>();
        baseFiles.forEach(vo->{
            mapFileVO.put(vo.getFileId(),vo);
        });

        List<BaseFile> waitInsert = new ArrayList<>();//待新增的文件信息


        for (RefulshMechaismFileParam refulshFileParam : param) {
            if (null!=refulshFileParam.getFileId() && mapFileVO.containsKey(refulshFileParam.getFileId())){
                //存在，表示这个不是新增文件，文件不需要删除
                baseFiles.remove(mapFileVO.remove(refulshFileParam.getFileId()));
            }else {
                BaseFile f = new BaseFile();
                f.setModelId(file.getModelId());
                f.setDbId(TableTypeEnums.MECHANISM.getCode());
                f.setFileId(refulshFileParam.getFileId());
                f.setUrl(refulshFileParam.getUrl());
                f.setMark(null==refulshFileParam.getMark()?"":refulshFileParam.getMark());
                f.setStatus(StatusEnums.ONE.getDesc());
                f.setCreatePerson(file.getCreatePerson());
                f.setCreateTime(file.getCreateTime());
                f.setUpdatePerson(file.getUpdatePerson());
                f.setUpdateTime(file.getUpdateTime());
                waitInsert.add(f);
            }
        }

        if (null != baseFiles && baseFiles.size()>0) {
            Long[] waitDel = new Long[baseFiles.size()];//待删除的文件信息
            int index = 0;
            for (FileVo baseFile : baseFiles) {
                waitDel[index++] = baseFile.getFileId();
            }

            fileMapper.deleteBaseFileByFileIds(waitDel,file.getCreatePerson());
            result.setDelNum(baseFiles.size());//本次删除多少条
        }else {
            result.setDelNum(0);//本次删除多少条
        }


        if (null!=waitInsert && waitInsert.size()>0) {
            for (BaseFile baseFile : waitInsert) {
                baseFile.setUrl(FileUploadUtils.removeFilePath(baseFile.getUrl()));
            }
            fileMapper.insertBaseFiles(waitInsert);
            result.setInsertNum(waitInsert.size());//本次新增多少条
        }else {
            result.setInsertNum(0);//本次新增多少条
        }

        baseFiles = fileMapper.getFile(queryEntity);
        result.setNowNum(baseFiles.size());//现有文件多少条
        return result;
    }
    /**
     * 根据对象集合调用指定get方法获取对象的不重复属性字段集
     * @param list 对象集
     * @param methods 获取对象指定属性的get方法数组
     * @param objClass 对象class
     * */
    Object[] getParamArray(List list, Method[] methods,Class objClass){
        Set<Object> idSet = new HashSet<>();
        list.forEach(auth->{
            for (Method method : methods) {
                method.setAccessible(true);
                try {
                    idSet.add(method.invoke(objClass.newInstance()));

                } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        });
        Object[] ids = new Long[idSet.size()];
        int index = 0;
        idSet.forEach(set->{
            ids[index] = set;
        });
        return ids;
    }

    /**
     * 根据对象集获取指定属性与对象的映射集
     * @param list 对象集
     * @param tClass 对象class
     * @param addParam get方法
     * */
    Map<Object,Object> getObjectMap(List list,Class tClass,Method addParam){
        Map<Object,Object> map = new HashMap<>();
        list.forEach(item->{
            addParam.setAccessible(true);
            try {
                map.put(addParam.invoke(tClass.newInstance()),item);

            } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
                e.printStackTrace();
            }
        });
        return map;
    }
}
