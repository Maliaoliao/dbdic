package com.gxchan.dbdic.repository;

import com.gxchan.dbdic.entity.Index;
import com.gxchan.dbdic.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author gxchan
 * <pre><b>email: </b>gengxin.chen@hand-china.com</pre>
 */
public class IndexRepositoryImpl implements IndexRepository {

    private static final String SQL = "SELECT t.index_name, t.table_name, " +
            "LISTAGG(t.column_name, ',') within GROUP (order by t.column_position) as column_names " +
            "FROM user_ind_columns t where t.index_name like 'TSP_%' " +
            "group by t.index_name, t.table_name";
    @Override
    public List<Index> query() {
        List<Index> result = new ArrayList<>();
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            if (conn != null) {
                PreparedStatement ps = conn.prepareStatement(SQL);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String indexName = rs.getString("index_name");
                    String tableName = rs.getString("table_name");
                    String columnNames = rs.getString("column_names");
                    result.add(new Index(indexName, tableName, columnNames));
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
