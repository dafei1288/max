package com.dafei1288.max;

import com.dafei1288.max.data.SqlLoader;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestSqlLoader {
    private static Connection conn;
    @BeforeClass
    public static void getConn() {
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://172.16.1.25:3306/air?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
        String username = "root";
        String password = "1";
        conn = null;
        try {
            Class.forName(driver); //classLoader,加载对应驱动
            conn = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //return conn;
    }

    @Test
    public void testLoadData(){
        try {
            //SqlLoader.loadData(conn,"SELECT t.* FROM air.facttable t LIMIT 501").forEach(System.out::println);
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
