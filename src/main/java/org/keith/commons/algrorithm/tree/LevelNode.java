package org.keith.commons.algrorithm.tree;

public class LevelNode {
    private int level;
    private String parentId;
    private String edge;

    public LevelNode() {}
    public LevelNode(int level, String parentId) {
        this.level = level;
        this.parentId = parentId;
    }
    public LevelNode(int level, String parentId, String edge) {
        this.level = level;
        this.edge = edge;
        this.parentId = parentId;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getEdge() {
        return edge;
    }

    public void setEdge(String edge) {
        this.edge = edge;
    }
}
