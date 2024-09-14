package com.yangyang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yangyang.domain.ResponseResult;
import com.yangyang.domain.dto.AddRoleDto;
import com.yangyang.domain.dto.ChangeStatusDto;
import com.yangyang.domain.entity.Role;

import java.util.List;

public interface RoleService extends IService<Role> {
    List<String> selectRolesByUserId(Long id);

    ResponseResult getPageRoleList(Integer pageNum, Integer pageSize, String roleName, String status);

    ResponseResult changeStatus(ChangeStatusDto changeStatusDto);

    ResponseResult treeSelect();

    ResponseResult addRole(AddRoleDto addRoleDto);

    ResponseResult getRoleByUserId(Long userId);

    ResponseResult updateRole(Role role);

    ResponseResult deleteRole(Long roleId);

    ResponseResult getRoleList(Integer pageNum, Integer pageSize, String userName, String phoneNumber, String status);

    ResponseResult listAllRole();

}
