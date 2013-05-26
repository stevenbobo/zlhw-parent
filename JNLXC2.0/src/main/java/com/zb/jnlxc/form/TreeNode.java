package com.zb.jnlxc.form;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 12-12-31
 * Time: 下午8:14
 * To change this template use File | Settings | File Templates.
 */
public class TreeNode {
    private String id;
    private String text;
    private String content;
    private String parentid;
    boolean isLeaf ;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getParentid() {
        return parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid;
    }

    public boolean isLeaf() {
        return isLeaf;
    }

    public void setLeaf(boolean leaf) {
        isLeaf = leaf;
    }
}
