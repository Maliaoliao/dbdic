package com.gxchan.dbdic.repository;

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
public class SequenceRepositoryImpl implements SequenceRepository {

    private static final String SQL = "select t.sequence_name from user_sequences t where t.sequence_name like 'TSP_%'";
    @Override
    public List<String> query() {
        List<String> result = new ArrayList<>();
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            if (conn != null) {
                PreparedStatement ps = conn.prepareStatement(SQL);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String name = rs.getString("sequence_name");
                    result.add(name);
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
