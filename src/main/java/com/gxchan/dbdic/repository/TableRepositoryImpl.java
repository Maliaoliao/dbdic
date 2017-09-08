package com.gxchan.dbdic.repository;

import com.gxchan.dbdic.entity.Table;
import com.gxchan.dbdic.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TableRepositoryImpl implements TableRepository {
    //排队Hyperion和ODI相关的表
    String tableSql = "select table_name, comments from user_tab_comments where table_name  like 'TSP_%' and table_type = 'TABLE' order by table_name";

    public List<Table> query() {
        List<Table> result = new ArrayList<Table>();
        Connection conn = null;
        try {

            conn = DBUtil.getConnection();
            if (conn != null) {
                PreparedStatement ps = conn.prepareStatement(tableSql);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String name = rs.getString("TABLE_NAME");
                    String comments = rs.getString("COMMENTS");
                    result.add(new Table(name, comments));
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
