package com.yangyang.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yangyang.constants.SystemConstants;
import com.yangyang.domain.ResponseResult;
import com.yangyang.domain.entity.Menu;
import com.yangyang.enums.AppHttpCodeEnum;
import com.yangyang.exception.SystemException;
import com.yangyang.mapper.MenuMapper;
import com.yangyang.service.MenuService;
import com.yangyang.utils.SecurityUtils;
import com.yangyang.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


//通过role来查menu
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Autowired
    private MenuService menuService;

    @Override
    public List<String> selectPermsByUserId(Long id) {
        if (id == 1L) {
            LambdaQueryWrapper<Menu> menuLambdaQueryWrapper = new LambdaQueryWrapper<>();
            menuLambdaQueryWrapper.in(Menu::getMenuType, SystemConstants.MENU, SystemConstants.BUTTON);
            menuLambdaQueryWrapper.eq(Menu::getStatus, SystemConstants.LINK_STATUS_NORMAL);

            List<Menu> menus = this.list(menuLambdaQueryWrapper);
            List<String> perms = menus.stream()
                    .map(Menu::getPerms)
                    .collect(Collectors.toList());

            return perms;
        }


        return this.getBaseMapper().selectPermsByUserId(id);
    }

    @Override
    public List<Menu> selectRouterMenuByUserId(Long userId) {

        List<Menu> menus = null;
        MenuMapper menuMapper = this.getBaseMapper();
        if(SecurityUtils.isAdmin()) {
            menus = menuMapper.selectAllRouterMenu();
        } else {
            menus = menuMapper.selectRouterMenuByUserId(userId);
        }

        return buildMenuTree(menus, 0L);
    }

    @Override
    public ResponseResult getMenuList(String status, String menuName) {

        LambdaQueryWrapper<Menu> menuLambdaQueryWrapper = new LambdaQueryWrapper<>();

        menuLambdaQueryWrapper.like(StringUtils.hasText(menuName), Menu::getMenuName, menuName);

        menuLambdaQueryWrapper.eq(StringUtils.hasText(status), Menu::getStatus, status);

        menuLambdaQueryWrapper.orderByDesc(Menu::getParentId, Menu::getOrderNum);


        List<Menu> menuList = this.list(menuLambdaQueryWrapper);

        return ResponseResult.okResult(menuList);


    }

    @Override
    public ResponseResult addMenu(Menu menu) {
        this.save(menu);

        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult searchMenu(Long id) {
        Menu menu = this.getById(id);

        return ResponseResult.okResult(menu);

    }

    @Override
    public ResponseResult updateMenu(HttpServletResponse response, Menu menu) {
        if(Objects.equals(menu.getParentId(), menu.getId())) {
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR.getCode(), "修改菜单'写博文'失败，上级菜单不能选择自己");
            WebUtils.renderString(response, JSON.toJSONString(result));
        }

        this.updateById(menu);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult removeMenu(HttpServletResponse response, Long menuId) {

        Menu menu = this.getById(menuId);

        if(Objects.equals(menuId, menu.getId())) {
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR.getCode(), "存在子菜单不允许删除");
            WebUtils.renderString(response, JSON.toJSONString(result));
        }

        this.removeById(menuId);

        return ResponseResult.okResult();


    }


    private List<Menu> buildMenuTree(List<Menu> menus, Long parentId) {
        return menus.stream().filter(menu -> parentId.equals(menu.getParentId()))
                .map(menu -> menu.setChildren(getChildren(menu, menus)))
                .collect(Collectors.toList());
    }

    private List<Menu> getChildren(Menu menu, List<Menu> menus) {
        return menus.stream().filter(m -> menu.getId().equals(m.getParentId()))
                .map(m -> m.setChildren(getChildren(m, menus)))
                .collect(Collectors.toList());
    }


}
