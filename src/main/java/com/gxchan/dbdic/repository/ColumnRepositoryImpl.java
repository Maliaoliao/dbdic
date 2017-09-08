package com.gxchan.dbdic.repository;

import com.gxchan.dbdic.entity.Column;
import com.gxchan.dbdic.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ColumnRepositoryImpl implements ColumnRepository {
    String sql = "select tc.column_name, tc.data_type, tc.data_length, tc.nullable, cc.comments from user_tab_columns tc, user_col_comments cc "
            + "where tc.table_name = cc.table_name(+) and tc.column_name = cc.column_name(+) and tc.table_name = ?";

    public List<Column> query(String tableName) {
        List<Column> result = new ArrayList<Column>();
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            if (conn != null) {
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, tableName);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String name = rs.getString("COLUMN_NAME");
                    String comments = rs.getString("COMMENTS");
                    String type = rs.getString("DATA_TYPE");
                    int length = rs.getInt("DATA_LENGTH");
                    String nullable = rs.getString("NULLABLE");
                    Column col = new Column();
                    col.setName(name);
                    col.setComments(comments);
                    if(type.startsWith("TIMESTAMP") || type.startsWith("DATE")) {
                        col.setType("DATETIME");
                        col.setLength(0);
                    } else {
                        col.setType(type);
                        col.setLength(length);
                    }
                    col.setNullable(nullable);
                    result.add(col);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

}
