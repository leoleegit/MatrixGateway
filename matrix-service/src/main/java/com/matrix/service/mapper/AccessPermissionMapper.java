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
    AccessPermission selectById(@Param("id")int id);
    int updateById(@Param("permission")AccessPermission accessPermission);
    List<AccessPermission> selectByRootId(@Param("rootId")int rootId);
    List<AccessPermission> selectFunctionByRootId(@Param("rootId")int rootId);
    List<AccessPermission> queryList(@Param(Constants.WRAPPER) QueryWrapper<?> queryWrapper);
    List<AccessPermission> queryListByRole(@Param(Constants.WRAPPER) QueryWrapper<?> queryWrapper);
    int queryCount(@Param(Constants.WRAPPER) QueryWrapper<?> queryWrapper);
}
