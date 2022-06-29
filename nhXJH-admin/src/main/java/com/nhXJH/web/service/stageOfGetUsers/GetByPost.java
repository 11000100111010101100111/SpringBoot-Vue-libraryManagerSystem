package com.nhXJH.web.service.stageOfGetUsers;

import com.nhXJH.common.core.domain.entity.SysUser;
import com.nhXJH.common.exception.ServiceException;
import com.nhXJH.system.domain.SysUserPost;
import com.nhXJH.system.mapper.SysUserMapper;
import com.nhXJH.system.mapper.SysUserPostMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: xjh
 * Date: 2022/3/4
 * Time: 10:40
 **/
@Service("byPost")
public class GetByPost implements IStageOfUser {
    @Autowired
    private SysUserMapper userMapper;
    @Autowired
    private SysUserPostMapper postMapper;
    @Override
    public List<SysUser> stage(QueryParam param) {
        if (null == param || null == param.getPostId()){
            throw new ServiceException("没有要查询的岗位条件");
        }
        SysUserPost query = new SysUserPost();
        query.setPostId(param.getPostId());
        List<SysUserPost> posts = postMapper.getList(query);

        if(null == posts){
            return new ArrayList<>();
        }

        Long[] ids = new Long[posts.size()];
        for (int i = 0; i < posts.size(); i++) {
            ids[i] = posts.get(i).getUserId();
        }
        return userMapper.selectUserByIds(ids);
    }
}
