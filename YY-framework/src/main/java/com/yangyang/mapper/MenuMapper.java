package com.yangyang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yangyang.domain.entity.Menu;

import java.util.List;

public interface MenuMapper extends BaseMapper<Menu> {
    List<String> selectPermsByUserId(Long userId);

    List<Menu> selectAllRouterMenu();

    List<Menu> selectRouterMenuByUserId(Long userId);
}
