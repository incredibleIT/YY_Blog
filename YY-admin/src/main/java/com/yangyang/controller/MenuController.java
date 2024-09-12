package com.yangyang.controller;


import com.yangyang.domain.ResponseResult;
import com.yangyang.domain.entity.Menu;
import com.yangyang.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping
public class MenuController {

    @Autowired
    private MenuService menuService;



    @GetMapping("system/menu/list")
    public ResponseResult getMenuList(String status, String menuName) {

        return menuService.getMenuList(status, menuName);

    }


    @PostMapping("/content/menu")
    public ResponseResult addMenu(@RequestBody Menu menu) {
        return menuService.addMenu(menu);

    }

    @GetMapping("system/menu/{id}")
    public ResponseResult searchMenu(@PathVariable("id") Long id) {

        return menuService.searchMenu(id);

    }

    @PutMapping("system/menu")
    public ResponseResult updateMenu(@RequestBody Menu menu, HttpServletResponse response) {

        return menuService.updateMenu(response, menu);

    }

    @DeleteMapping("content/menu/{menuId}")
    public ResponseResult removeMenu(@PathVariable("menuId") Long menuId, HttpServletResponse response) {
        return menuService.removeMenu(response, menuId);
    }







}
