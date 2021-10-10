package com.matrix.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.matrix.service.entity.RolePermission;
import com.matrix.service.mapper.RolePermissionMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author [matrix generator]
 * @since 2021-09-18
 */
@Service
public class RolePermissionServiceImpl extends BaseServiceImpl<RolePermissionMapper, RolePermission>{
    public int insert(RolePermission rolePermission){
        return baseMapper.insert(rolePermission);
    }

    public int deleteByQuery(QueryWrapper<?> queryWrapper){
        return baseMapper.deleteByQuery(queryWrapper);
    }

    public List<RolePermission> selectByRoleId(int roleId){
        return baseMapper.selectByRoleId(roleId);
    }
}
