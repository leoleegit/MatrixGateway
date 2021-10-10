package com.matrix.service.impl;

import com.matrix.service.entity.AccessPermission;
import com.matrix.service.entity.Role;
import com.matrix.service.entity.User;
import com.matrix.service.mapper.RoleMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> {
    public int insert(Role role){
        return baseMapper.insert(role);
    }

    public int deleteById(int id){
        return baseMapper.deleteById(id);
    }

    public List<Role> selectListByCreatedBy(String createdBy){
        return baseMapper.selectListByCreatedBy(createdBy);
    }

    public Role info(int id){
        return baseMapper.selectById(id);
    }

    public int update(Role role){
        return baseMapper.updateById(role);
    }
}
