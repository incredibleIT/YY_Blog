package com.yangyang.domain.vo;


import com.yangyang.domain.entity.Menu;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RouterVo {

    private List<Menu> menus;
}
