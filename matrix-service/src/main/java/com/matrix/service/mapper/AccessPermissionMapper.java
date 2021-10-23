package com.matrix.service.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.matrix.service.entity.AccessPermission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author [matrix generator]
 * @since 2021-09-09
 */
public interface AccessPermissionMapper extends BaseMapper<AccessPermission> {
    int updateById(@Param("permission")AccessPermission accessPermission);
    List<AccessPermission> queryListByRole(@Param(Constants.WRAPPER) QueryWrapper<?> queryWrapper,@Param("roleId")long roleId);
    int queryListByRoleCount(@Param(Constants.WRAPPER) QueryWrapper<?> queryWrapper,@Param("roleId")long roleId);
    List<AccessPermission> queryListByUser(@Param(Constants.WRAPPER) QueryWrapper<?> queryWrapper,@Param("userId")long userId);
    int queryListByUserCount(@Param(Constants.WRAPPER) QueryWrapper<?> queryWrapper,@Param("userId")long userId);
}
