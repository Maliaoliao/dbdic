package com.gxchan.dbdic.entity;

import java.util.List;

public class Table {
    private String name;
    private String comments;
    private List<Column> columns;
    private List<Index> indexes;

    public Table() {
    }

    public Table(String name, String comments) {
        this.name = name;
        this.comments = comments;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public List<Index> getIndexes() {
        return indexes;
    }

    public void setIndexes(List<Index> indexes) {
        this.indexes = indexes;
    }
}
