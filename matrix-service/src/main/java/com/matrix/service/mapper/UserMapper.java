package com.matrix.service.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.matrix.service.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.matrix.service.entity.UserWithRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author [matrix generator]
 * @since 2021-09-04
 */
public interface UserMapper extends BaseMapper<User> {
    User selectByUsername(@Param("username")String username);
    User selectByTelephone(@Param("telephone")String telephone);
    User selectByEmail(@Param("email")String email);
    List<UserWithRole> queryList(@Param(Constants.WRAPPER) QueryWrapper<User> queryWrapper);
    int queryCount(@Param(Constants.WRAPPER) QueryWrapper<User> queryWrapper);
}
