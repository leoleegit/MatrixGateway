package com.matrix.service.impl;

import com.matrix.service.entity.RoleUser;
import com.matrix.service.mapper.RoleUserMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author [matrix generator]
 * @since 2021-09-18
 */
@Service
public class RoleUserServiceImpl extends ServiceImpl<RoleUserMapper, RoleUser>{
    public int insert(RoleUser roleUser){
        return baseMapper.insert(roleUser);
    }
}
