package com.nhXJH.web.service.impl;

import java.util.*;

import com.nhXJH.common.core.domain.entity.SysUser;
import com.nhXJH.common.enums.application.StatusEnums;
import com.nhXJH.system.mapper.SysUserMapper;
import com.nhXJH.web.domain.LibraryAuth;
import com.nhXJH.web.domain.LibraryPress;
import com.nhXJH.common.utils.DateUtils;
import com.nhXJH.web.domain.vo.AuthVO;
import com.nhXJH.web.domain.vo.PressVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nhXJH.web.mapper.LibraryPressMapper;
//import com.nhXJH.web.domain.LibraryPress;
import com.nhXJH.web.service.ILibraryPressService;

/**
 * 出版社信息Service业务层处理
 * 
 * @author xjh
 * @date 2022-01-25
 */
@Service
public class LibraryPressServiceImpl extends ServiceImpl<LibraryPressMapper, LibraryPress>
        implements ILibraryPressService  {
    @Autowired
    private LibraryPressMapper libraryPressMapper;
    @Autowired
    private SysUserMapper userMapper;

    /**
     * 查询出版社信息
     * 
     * @param id 出版社信息主键
     * @return 出版社信息
     */
    @Override
    public PressVO selectLibraryPressById(Long id) {
        LibraryPress press = libraryPressMapper.selectLibraryPressById(id);
        SysUser createUser = userMapper.selectUserById(press.getCreatePersonal());
        SysUser updateUser = userMapper.selectUserById(press.getCreatePersonal());
        PressVO pressVO = new PressVO(press,createUser,updateUser);
        return pressVO;
    }

    @Override
    public Integer getCount(LibraryPress libraryPress) {
        return libraryPressMapper.getCount(libraryPress);
    }

    /**
     * 查询出版社信息列表
     * 
     * @param libraryPress 出版社信息
     * @return 出版社信息
     */
    @Override
    public List<PressVO> selectLibraryPressList(LibraryPress libraryPress) {
        List<LibraryPress> pressList = libraryPressMapper.selectLibraryPressList(libraryPress);
        Set<Long> idSet = new HashSet<>();
        pressList.forEach(press->{
            idSet.add(press.getCreatePersonal());
            idSet.add(press.getUpdatePersonal());
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
        List<PressVO> result = new ArrayList<>();
        pressList.forEach(press -> {
            result.add(
                    new PressVO(
                            press,
                            userMap.get(press.getCreatePersonal()),
                            userMap.get(press.getUpdatePersonal())
                    )
            );
        });

        return result;
    }

    /**
     * 新增出版社信息
     * 
     * @param libraryPress 出版社信息
     * @return 结果
     */
    @Override
    public int insertLibraryPress(LibraryPress libraryPress) {
        libraryPress.setCreateTime(DateUtils.getNowDate());
        libraryPress.setStatus(StatusEnums.ONE.getDesc());
        return libraryPressMapper.insertLibraryPress(libraryPress);
    }

    /**
     * 修改出版社信息
     * 
     * @param libraryPress 出版社信息
     * @return 结果
     */
    @Override
    public int updateLibraryPress(LibraryPress libraryPress) {
        libraryPress.setUpdateTime(DateUtils.getNowDate());
        return libraryPressMapper.updateLibraryPress(libraryPress);
    }

    /**
     * 批量删除出版社信息
     * 
     * @param ids 需要删除的出版社信息主键
     * @return 结果
     */
    @Override
    public int deleteLibraryPressByIds(Long[] ids,Long operator) {
        return libraryPressMapper.deleteLibraryPressByIds(ids,operator);
    }

    /**
     * 删除出版社信息信息
     * 
     * @param id 出版社信息主键
     * @return 结果
     */
    @Override
    public int deleteLibraryPressById(Long id,Long operator) {
        return libraryPressMapper.deleteLibraryPressById(id,operator);
    }

    /**
     * 状态修改
     */
    public int updateUserStatus(LibraryPress press){
        return libraryPressMapper.updateLibraryPress(press);
    }
}
