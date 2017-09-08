package com.gxchan.dbdic.repository;

import java.util.List;

import com.gxchan.dbdic.entity.Column;

public interface ColumnRepository {

    public List<Column> query(String tableName);
}
