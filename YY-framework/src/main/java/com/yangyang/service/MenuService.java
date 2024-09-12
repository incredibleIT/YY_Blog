package com.yangyang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yangyang.domain.ResponseResult;
import com.yangyang.domain.entity.Menu;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface MenuService extends IService<Menu> {
    List<String> selectPermsByUserId(Long id);

    List<Menu> selectRouterMenuByUserId(Long userId);

    ResponseResult getMenuList(String status, String menuName);

    ResponseResult addMenu(Menu menu);

    ResponseResult searchMenu(Long id);

    ResponseResult updateMenu(HttpServletResponse response, Menu menu);

    ResponseResult removeMenu(HttpServletResponse response, Long menuId);
}
