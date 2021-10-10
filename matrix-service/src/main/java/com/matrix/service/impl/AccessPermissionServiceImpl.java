package com.matrix.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.matrix.service.entity.AccessPermission;
import com.matrix.service.mapper.AccessPermissionMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author [matrix generator]
 * @since 2021-09-09
 */
@Service
public class AccessPermissionServiceImpl extends BaseServiceImpl<AccessPermissionMapper, AccessPermission>  {
    public int insert(AccessPermission accessPermission){
        return baseMapper.insert(accessPermission);
    }

    public int deleteById(int id){
        return baseMapper.deleteById(id);
    }

    public int update(AccessPermission accessPermission){
        return baseMapper.updateById(accessPermission);
    }

    public List<AccessPermission> list(int root){
        return baseMapper.selectByRootId(root);
    }

    public List<AccessPermission> queryList(QueryWrapper<?> queryWrapper){
        return baseMapper.queryList(queryWrapper);
    }

    public List<AccessPermission> queryListByRole(QueryWrapper<?> queryWrapper){
        return baseMapper.queryListByRole(queryWrapper);
    }

    public int queryCount(QueryWrapper<?> queryWrapper){
        return baseMapper.queryCount(queryWrapper);
    }

    public AccessPermission info(int id){
        return baseMapper.selectById(id);
    }

    public List<AccessPermission> functionList(int root){
        return baseMapper.selectFunctionByRootId(root);
    }
}
