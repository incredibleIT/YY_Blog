package com.yangyang.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yangyang.constants.SystemConstants;
import com.yangyang.domain.ResponseResult;
import com.yangyang.domain.dto.AddRoleDto;
import com.yangyang.domain.dto.ChangeStatusDto;
import com.yangyang.domain.entity.Menu;
import com.yangyang.domain.entity.Role;
import com.yangyang.domain.entity.User;
import com.yangyang.domain.entity.UserRole;
import com.yangyang.domain.vo.PageVo;
import com.yangyang.mapper.RoleMapper;
import com.yangyang.service.MenuService;
import com.yangyang.service.RoleService;
import com.yangyang.service.UserService;
import com.yangyang.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private MenuService menuService;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private RoleService roleService;




    @Override
    public List<String> selectRolesByUserId(Long id) {
        if(id == 1L) {
            ArrayList<String> roleKeys = new ArrayList<>();
            roleKeys.add("admin");

            return roleKeys;
        }

        return getBaseMapper().selectRolesByUserId(id);
    }

    @Override
    public ResponseResult getPageRoleList(Integer pageNum, Integer pageSize, String roleName, String status) {
        Page<Role> rolePage = new Page<>(pageNum, pageSize);

        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.like(StringUtils.hasText(roleName), Role::getRoleName, roleName);
        queryWrapper.eq(StringUtils.hasText(status), Role::getStatus, status);

        queryWrapper.orderByAsc(Role::getRoleSort);

        page(rolePage, queryWrapper);


        return ResponseResult.okResult(new PageVo(rolePage.getRecords(), rolePage.getTotal()));
    }

    @Override
    public ResponseResult changeStatus(ChangeStatusDto roleDto) {
        Role role = BeanCopyUtils.copyBean(roleDto, Role.class);
        this.updateById(role);

        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult treeSelect() {
        //全部的
        List<Menu> list = menuService.list();

        return ResponseResult.okResult(buildTree(list));



    }

    @Override
    public ResponseResult addRole(AddRoleDto addRoleDto) {
        Role role = BeanCopyUtils.copyBean(addRoleDto, Role.class);

        this.save(role);

        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getRoleByUserId(Long userId) {

        LambdaQueryWrapper<UserRole> roleLambdaQueryWrapper = new LambdaQueryWrapper<>();
        roleLambdaQueryWrapper.eq(UserRole::getUserId, userId);

        UserRole userRole = userRoleService.getOne(roleLambdaQueryWrapper);

        LambdaQueryWrapper<Role> roleLambdaQueryWrapper1 = new LambdaQueryWrapper<>();
        roleLambdaQueryWrapper1.eq(Role::getId, userRole.getRoleId());

        List<Role> list = roleService.list(roleLambdaQueryWrapper1);


        return ResponseResult.okResult(list);
    }

    @Override
    public ResponseResult updateRole(Role role) {
        updateById(role);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteRole(Long roleId) {
        removeById(roleId);

        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getRoleList(Integer pageNum, Integer pageSize, String roleName, String phoneNumber, String status) {

        LambdaQueryWrapper<Role> roleLambdaQueryWrapper = new LambdaQueryWrapper<>();

        roleLambdaQueryWrapper.like(StringUtils.hasText(roleName), Role::getRoleName, roleName);

        roleLambdaQueryWrapper.eq(Role::getStatus, status);

        roleLambdaQueryWrapper.orderByAsc(Role::getRoleSort);

        Page<Role> rolePage = new Page<>(pageNum, pageSize);

        this.page(rolePage, roleLambdaQueryWrapper);

        return ResponseResult.okResult(new PageVo(rolePage.getRecords(), rolePage.getTotal()));
    }

    @Override
    public ResponseResult listAllRole() {
        LambdaQueryWrapper<Role> roleLambdaQueryWrapper = new LambdaQueryWrapper<>();

        roleLambdaQueryWrapper.eq(Role::getStatus, SystemConstants.NORMAL);

        return ResponseResult.okResult(this.list(roleLambdaQueryWrapper));
    }

    private List<Menu> buildTree(List<Menu> list) {

        LambdaQueryWrapper<Menu> menuLambdaQueryWrapper = new LambdaQueryWrapper<>();
        menuLambdaQueryWrapper.eq(Menu::getParentId, 0L);
        // 全部父节点
        List<Menu> menus = menuService.list(menuLambdaQueryWrapper);


        return menus.stream()
                .map(menu -> menu.setChildren(getChildren(menu, list)))
                .collect(Collectors.toList());

    }

    private List<Menu> getChildren(Menu menu, List<Menu> list) {

        return list.stream().filter(m -> m.getParentId().equals(menu.getId()))
                .map(m -> m.setChildren(getChildren(m, list)))
                .collect(Collectors.toList());



    }
}
