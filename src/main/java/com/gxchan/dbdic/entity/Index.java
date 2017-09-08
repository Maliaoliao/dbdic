package com.gxchan.dbdic.entity;

/**
 * @author gxchan
 * <pre><b>email: </b>gengxin.chen@hand-china.com</pre>
 */
public class Index {
    private String indexName;
    private String tableName;
    private String columnNames;

    public Index(String indexName, String tableName, String columnNames) {
        this.indexName = indexName;
        this.tableName = tableName;
        this.columnNames = columnNames;
    }

    public String getIndexName() {
        return indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getColumnNames() {
        return columnNames;
    }

    public void setColumnNames(String columnNames) {
        this.columnNames = columnNames;
    }
}
