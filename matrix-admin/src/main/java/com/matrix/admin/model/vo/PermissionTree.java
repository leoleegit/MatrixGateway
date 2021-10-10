package com.matrix.admin.model.vo;

import com.matrix.core.constants.CS;
import com.matrix.core.model.common.TreeNode;
import com.matrix.service.entity.AccessPermission;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class PermissionTree extends TreeNode {
    private String name;

    private Integer type;

    private String code;

    private String desc;

    private Integer rootId;

    private String method;

    private String path;

    private Date createdAt;

    private Date updatedAt;

    public PermissionTree() {
    }

    public PermissionTree(AccessPermission accessPermission) {
        BeanUtils.copyProperties(accessPermission,this);
        setParentId(accessPermission.getRootId());
    }

    public static List<PermissionTree> getInstance(List<AccessPermission> accessPermissionList){
        List<PermissionTree> treeList = new ArrayList<>();
        if(accessPermissionList!=null && accessPermissionList.size()>0){
            for(AccessPermission accessPermission : accessPermissionList){
                if(accessPermission.getRootId()==null || accessPermission.getRootId() == CS.PERMISSION_TREE.SYS){
                    PermissionTree permissionTree = new PermissionTree(accessPermission);
                    treeList.add(findChildren(permissionTree,accessPermissionList));
                }
            }
        }
        return treeList;
    }

    private static PermissionTree findChildren(PermissionTree parent, List<AccessPermission> accessPermissionList){
        if(accessPermissionList!=null && accessPermissionList.size()>0){
            for(AccessPermission accessPermission : accessPermissionList){
                if(accessPermission.getRootId()!=null && parent.getId() == accessPermission.getRootId()){
                    PermissionTree permissionTree = new PermissionTree(accessPermission);
                    parent.add(findChildren(permissionTree,accessPermissionList));
                }
            }
        }
        return parent;
    }

    public static boolean hasPermission(TreeNode permissionTree, List<AccessPermission> accessPermissions){
        if(accessPermissions!=null){
            return accessPermissions.stream().filter(accessPermission -> accessPermission.getId() == permissionTree.getId()).count()>0;
        }
        return false;
    }

    public static boolean hasChild(TreeNode permissionTree, List<AccessPermission> accessPermissions){
        if(accessPermissions!=null){
            if(permissionTree.getChildren()!=null){
                for(TreeNode child : permissionTree.getChildren()){
                    boolean hasPermission = hasPermission(child,accessPermissions);
                    if(hasPermission)return true;
                    return hasChild(child,accessPermissions);
                }
            }
        }
        return false;
    }

    public static List<PermissionTree> filter(List<PermissionTree> list, List<AccessPermission> accessPermissions){
        list = list.stream().filter(permissionTree -> {
            if(permissionTree.getChildren()!=null){
                permissionTree.setChildren(filterChild(permissionTree.getChildren(),accessPermissions));
            }
            return filter(permissionTree,accessPermissions);
        }).collect(Collectors.toList());
        return list;
    }

    private static List<TreeNode> filterChild(List<TreeNode> list, List<AccessPermission> accessPermissions){
        list = list.stream().filter(permissionTree -> {
            if(permissionTree.getChildren()!=null){
                permissionTree.setChildren(filterChild(permissionTree.getChildren(),accessPermissions));
            }
            return filter(permissionTree,accessPermissions);
        }).collect(Collectors.toList());
        return list;
    }

    private static boolean filter(TreeNode permissionTree, List<AccessPermission> accessPermissions){
        if(hasPermission(permissionTree,accessPermissions)){
            return true;
        }
        if(hasChild(permissionTree,accessPermissions)){
            permissionTree.setDisabled(true);
            return true;
        }
        return false;
    }

}
