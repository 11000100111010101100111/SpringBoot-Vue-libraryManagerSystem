package com.nhXJH.web.controller.system;

import java.util.List;
import java.util.Set;

import net.bytebuddy.implementation.bind.annotation.Origin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.nhXJH.common.constant.Constants;
import com.nhXJH.common.core.domain.AjaxResult;
import com.nhXJH.common.core.domain.entity.SysMenu;
import com.nhXJH.common.core.domain.entity.SysUser;
import com.nhXJH.common.core.domain.model.LoginBody;
import com.nhXJH.common.utils.SecurityUtils;
import com.nhXJH.framework.web.service.SysLoginService;
import com.nhXJH.framework.web.service.SysPermissionService;
import com.nhXJH.system.service.ISysMenuService;

/**
 * 登录验证
 * 
 * @author nhXJH
 */
@RestController
public class SysLoginController {
    @Autowired
    private SysLoginService loginService;

    @Autowired
    private ISysMenuService menuService;

    @Autowired
    private SysPermissionService permissionService;

    /**
     * 登录方法
     * 
     * @param loginBody 登录信息
     * @return 结果
     */
    @PostMapping("/login")
    @CrossOrigin
    public AjaxResult login(@RequestBody LoginBody loginBody) {
        AjaxResult ajax = AjaxResult.success();
        // 生成令牌
        String token = loginService.login(loginBody.getUsername(), loginBody.getPassword(), loginBody.getCode(),
                loginBody.getUuid());
        ajax.put(Constants.TOKEN, token);
        return ajax;
    }

    /**
     * 获取用户信息
     * 
     * @return 用户信息
     */
    @GetMapping("/getInfo")
    @CrossOrigin
    public AjaxResult getInfo() {
        SysUser user = SecurityUtils.getLoginUser().getUser();
        // 角色集合
        Set<String> roles = permissionService.getRolePermission(user);
        // 权限集合
        Set<String> permissions = permissionService.getMenuPermission(user);
        AjaxResult ajax = AjaxResult.success();
        ajax.put("user", user);
        ajax.put("roles", roles);
        ajax.put("permissions", permissions);
        return ajax;
    }

    /**
     * 获取路由信息
     * 
     * @return 路由信息
     */
    @GetMapping("/getRouters")
    @CrossOrigin
    public AjaxResult getRouters() {
        Long userId = SecurityUtils.getUserId();
        List<SysMenu> menus = menuService.selectMenuTreeByUserId(userId);
        return AjaxResult.success(menuService.buildMenus(menus));
    }
}
