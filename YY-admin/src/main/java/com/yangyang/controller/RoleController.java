package com.yangyang.controller;


import com.yangyang.domain.ResponseResult;
import com.yangyang.domain.dto.AddRoleDto;
import com.yangyang.domain.dto.ChangeStatusDto;
import com.yangyang.domain.entity.Role;
import com.yangyang.service.RoleService;
import com.yangyang.service.impl.RoleServiceImpl;
import com.yangyang.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;

@RestController
@RequestMapping
public class RoleController {

    @Autowired
    private RoleService roleService;
    @Autowired
    private RoleServiceImpl roleServiceImpl;


    @GetMapping("system/role/list")
    public ResponseResult getPageRoleList(Integer pageNum, Integer pageSize, String roleName, String status) {

        return roleService.getPageRoleList(pageNum, pageSize, roleName, status);

    }


    @PutMapping("system/role/changeStatus")
    public ResponseResult changeStatus(@RequestBody ChangeStatusDto changeStatusDto) {
        return roleService.changeStatus(changeStatusDto);
    }


    @GetMapping("/system/menu/treeSelect")
    public ResponseResult treeSelect() {
        return roleService.treeSelect();
    }

    @PostMapping("/system/role")
    public ResponseResult addRole(@RequestBody AddRoleDto addRoleDto) {
        return roleService.addRole(addRoleDto);

    }


    @GetMapping("/system/role/{id}")
    public ResponseResult getRoleByUserId(@PathVariable("id") Long userId) {
        return roleService.getRoleByUserId(userId);
    }

    // TODO 5.21.2.2 加载对应角色菜单列表树接口





    // 更新角色信息
    @PutMapping("/system/role")
    public ResponseResult updateRole(@RequestBody Role role) {
        return roleService.updateRole(role);
    }


    @DeleteMapping("/system/role/{id}")
    public ResponseResult deleteRole(@PathVariable("id") Long roleId) {
        return roleService.deleteRole(roleId);

    }


    @GetMapping("/system/role/listAllRole")
    public ResponseResult getAllRole() {
        return roleService.listAllRole();
    }


}
