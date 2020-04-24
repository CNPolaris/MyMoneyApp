package com.example.mymoneyapp;
import android.app.Dialog;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
public class DBUtil {
    private static String IP = "192.168.31.149";
    private static String DBName = "moneyBookDB";
    private static String USER = "sa";
    private static String PWD = "123";

    private static Connection getSQLConnection() throws SQLException {
        Connection con = null;
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            //con=DriverManager.getConnection(url, USER, PWD);
            //con=DriverManager.getConnection("jdbc:jtds:sqlserver://" + IP + ":1433/" + DBName + ";useunicode=true;characterEncoding=UTF-8", USER, PWD);
            con = DriverManager.getConnection("jdbc:jtds:sqlserver://" + IP + ":1433/" + DBName + ";charset=utf-8", USER, PWD);
            System.out.println("连接成功");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("导入失败");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("连接失败");
        }
        return con;
    }

    public void Insert() throws SQLException {
        PreparedStatement pstm = null;
        Connection connection = null;
        Statement stmt=null;
        String sql = "insert into UserInfo(username,password)values('xiao','789')";
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            System.out.println("导入成功");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("导入失败");
        }
        try {
            connection = getSQLConnection();
            stmt=connection.createStatement();
            //con=DriverManager.getConnection("jdbc:jtds:sqlserver://" + IP + ":1433/" + DBName + ";useunicode=true;characterEncoding=UTF-8", USER, PWD);
            //pstm = connection.prepareStatement(sql);
            stmt.executeUpdate(sql);
            System.out.println("插入成功");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("插入出错");
        } finally {
            try {
                pstm.close();
                connection.close();
                System.out.println("关闭成功");
            } catch (Exception e2) {
                e2.printStackTrace();
                System.out.println("关闭失败");
            }

        }
    }

    public static void sle() throws SQLException {
        PreparedStatement pstm = null;
        String s="";
        Connection connection = null;
        ResultSet result=null;
        Statement statement=null;
        String sql = "select *from UserInfo";
        try {
            connection = getSQLConnection();
            //con=DriverManager.getConnection("jdbc:jtds:sqlserver://" + IP + ":1433/" + DBName + ";useunicode=true;characterEncoding=UTF-8", USER, PWD);
            pstm = connection.prepareStatement(sql);
            statement=connection.createStatement();
            result=statement.executeQuery(sql);
            while(result.next())
            {
                String s1 = result.getString("username");
                String s2 = result.getString("password");
                s += s1+" - " + s2 + "\n";
                System.out.println(s1+" - "+s2);
            }
            System.out.println(result);
            System.out.println("成功");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("插入出错");
        } finally {
            try {
                pstm.close();
                connection.close();
                statement.close();
                result.close();
                System.out.println("关闭成功");
            } catch (Exception e2) {
                e2.printStackTrace();
                System.out.println("关闭失败");
            }

        }
    }
}
