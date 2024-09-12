package com.yangyang.domain.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddRoleDto {

    //角色名称
    private String roleName;
    //角色权限字符串
    private String roleKey;
    //显示顺序
    private Integer roleSort;

    //角色状态（0正常 1停用）
    private String status;
    //备注
    private String remark;

    //关联菜单id数组，不是表中的字段  用来接收参数使用
    private Long[] menuIds;
}
