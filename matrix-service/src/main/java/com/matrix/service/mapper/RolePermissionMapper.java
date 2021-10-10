package com.matrix.service.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.matrix.service.entity.RolePermission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author [matrix generator]
 * @since 2021-09-18
 */
public interface RolePermissionMapper extends BaseMapper<RolePermission> {
    List<RolePermission> selectByRoleId(@Param("roleId")int roleId);
    int deleteByQuery(@Param(Constants.WRAPPER) QueryWrapper<?> queryWrapper);
}
