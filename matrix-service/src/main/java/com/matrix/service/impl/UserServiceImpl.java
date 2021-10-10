package com.matrix.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.matrix.core.constants.CS;
import com.matrix.service.entity.User;
import com.matrix.service.entity.UserWithRole;
import com.matrix.service.mapper.UserMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 * @author [matrix generator]
 * @since 2021-09-03
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<UserMapper, User> {
    public User selectByLogin(String username, int authType){
        switch (authType){
            case CS.AuthType.USER_NAME:
                return baseMapper.selectByUsername(username);
            case CS.AuthType.PHONE:
                return baseMapper.selectByTelephone(username);
            case CS.AuthType.EMAIL:
                return baseMapper.selectByEmail(username);
            default:
                return null;
        }
    }

    public List<UserWithRole> queryList(QueryWrapper<User> queryWrapper){
        return baseMapper.queryList(queryWrapper);
    }

    public int queryCount(QueryWrapper<User> queryWrapper){
        return baseMapper.queryCount(queryWrapper);
    }

    public int insert(User user){
        return baseMapper.insert(user);
    }
}
