package com.changxx.practice.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @author changxx on 15-7-21.
 */
public class UpdateTest {

    /**
     * @param args
     */
    //驱动程序就是之前在classpath中配置的JDBC的驱动程序的JAR 包中
    public static final String DBDRIVER = "com.mysql.jdbc.Driver";
    //连接地址是由各个数据库生产商单独提供的，所以需要单独记住
    public static final String DBURL = "jdbc:mysql://localhost:3306/test";
    //连接数据库的用户名
    public static final String DBUSER = "root";
    //连接数据库的密码
    public static final String DBPASS = "";

    public static void main(String[] args) throws Exception {
        Connection con = null; //表示数据库的连接对象
        Statement stmt = null;  //表示数据库的更新操作
        ResultSet result = null; //表示接收数据库的查询结果
        Class.forName(DBDRIVER); //1、使用CLASS 类加载驱动程序
        con = DriverManager.getConnection(DBURL, DBUSER, DBPASS); //2、连接数据库
        stmt = con.createStatement(); //3、Statement 接口需要通过Connection 接口进行实例化操作
        result = stmt.executeQuery("select name,age,address from java_study.person"); //执行SQL 语句，查询数据库
        while (result.next()) {
            String name = result.getString("name");
            int age = result.getInt("age");
            String address = result.getString("address");
            System.out.println(name + age + address);
        }
        result.close();
        con.close(); // 4、关闭数据库
    }
}
