package org.keith.commons.algrorithm.tree;

public class Node {
    private String id;
    private String name;
    private String leftId;
    private String rightId;
    private String leftEdge;
    private String rightEdge;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLeftId() {
        return leftId;
    }

    public void setLeftId(String leftId) {
        this.leftId = leftId;
    }

    public String getRightId() {
        return rightId;
    }

    public void setRightId(String rightId) {
        this.rightId = rightId;
    }

    public String getLeftEdge() {
        return leftEdge;
    }

    public void setLeftEdge(String leftEdge) {
        this.leftEdge = leftEdge;
    }

    public String getRightEdge() {
        return rightEdge;
    }

    public void setRightEdge(String rightEdge) {
        this.rightEdge = rightEdge;
    }
}
