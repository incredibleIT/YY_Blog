package com.yangyang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yangyang.domain.entity.Role;

import java.util.List;

public interface RoleMapper extends BaseMapper<Role> {

    List<String> selectRolesByUserId(Long userId);
}
