package com.matrix.service.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author [matrix generator]
 * @since 2021-09-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_role_user")
public class RoleUser implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type= IdType.AUTO)
    private Integer id;

    @TableField(value = "role_id")
    private Integer roleId;

    @TableField(value = "user_id")
    private Integer userId;

    @TableField(value = "created_at")
    private Date createdAt;
}
