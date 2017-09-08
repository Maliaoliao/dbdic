package com.gxchan.dbdic.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBUtil {

    public static Connection getConnection() {
        Connection result = null;
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");// 加载Oracle驱动程序
            String url = "jdbc:oracle:thin:@haasgz.hand-china.com:34221:FSSCDEV";
            String user = "taxp_dev";// 用户名,系统默认的账户名
            String password = "taxp_dev";
            result = DriverManager.getConnection(url, user, password);// 获取连接
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
