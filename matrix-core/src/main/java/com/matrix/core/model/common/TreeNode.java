package com.matrix.core.model.common;


import java.util.ArrayList;
import java.util.List;

public class TreeNode {
    protected Integer id;
    protected Integer parentId;
    protected boolean disabled;

    public List<TreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<TreeNode> children) {
        this.children = children;
    }

    List<TreeNode> children = null;

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public void add(TreeNode node){
        if(children==null)
            children = new ArrayList<>();
        children.add(node);
    }

    public boolean remove(TreeNode node){return children!=null?children.remove(node):false;}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }
}
