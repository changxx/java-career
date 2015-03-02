package com.changxx.practice.test.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author changxiangxiang
 * @date 2013年11月28日 下午7:58:04
 * @description
 */
public class WInsert {

    private final static String url = "jdbc:mysql://10.28.168.38:3306/pmpdb";
    private final static int    num = 530000;

    public Connection getConn() {
        Connection con = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            try {
                con = DriverManager.getConnection(url, "pmpdb", "pmpdb");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return con;
    }

    public void insert() {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            String sql = "INSERT INTO pmp_statistics_ur_staff_day VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            conn = this.getConn();
            conn.setAutoCommit(false);
            ps = conn.prepareStatement(sql);
            for (int i = 600000; i < 630000; i++) {
                ps.setInt(1, i + 1);
                ps.setString(2, "bjcxxx");
                ps.setString(3, "sdfff");
                ps.setString(4, "sdddddddddd/sdfffffffffffffff/asdfffffffff/sdfaa");
                ps.setDate(5, new java.sql.Date(new java.util.Date().getTime()));
                ps.setDouble(6, 10d);
                ps.setDouble(7, 10d);
                ps.setDouble(8, 10d);
                ps.setString(9, "type hours");
                ps.setDouble(10, 10d);
                ps.setDouble(11, 10d);
                ps.setDouble(12, 10d);
                ps.setDouble(13, 10d);
                ps.setInt(14, 1);
                ps.setDate(15, new java.sql.Date(new java.util.Date().getTime()));
                ps.addBatch();
            }
            Thread.sleep(500);
            System.out.println("插入开始......................");
            long start = System.currentTimeMillis();
            int[] ids = ps.executeBatch();
            conn.commit();
            System.out.println("插入完毕，插入数据" + ids.length + "条，耗时" + (System.currentTimeMillis() - start));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    public static void main(String[] args) {
        WInsert insert = new WInsert();
        insert.insert();
    }
}
