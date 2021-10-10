package com.matrix.service.mapper;

import com.matrix.service.entity.Role;
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
public interface RoleMapper extends BaseMapper<Role> {
    int updateById(@Param("role") Role role);
    Role selectById(@Param("id")int id);
    List<Role> selectListByCreatedBy(@Param("createdBy")String createdBy);
}
