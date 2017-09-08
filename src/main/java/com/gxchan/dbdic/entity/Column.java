package com.gxchan.dbdic.entity;

public class Column {
    private String name;
    private String type;
    private int length;
    //
    private String nullable;
    private String comments;

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNullable() {
        return nullable;
    }

    public void setNullable(String nullable) {
        this.nullable = nullable;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "Column [name=" + name + ", type=" + type + ", length=" + length + ", nullable=" + nullable + ", comments=" + comments + "]";
    }

}
