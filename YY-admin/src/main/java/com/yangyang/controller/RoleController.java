package com.yangyang.controller;


import com.yangyang.domain.ResponseResult;
import com.yangyang.domain.dto.AddRoleDto;
import com.yangyang.domain.dto.ChangeStatusDto;
import com.yangyang.domain.entity.Role;
import com.yangyang.service.RoleService;
import com.yangyang.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class RoleController {

    @Autowired
    private RoleService roleService;


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



}
