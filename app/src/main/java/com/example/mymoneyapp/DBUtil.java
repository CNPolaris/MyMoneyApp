package com.example.mymoneyapp;
import android.app.Dialog;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import java.lang.reflect.Type;
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
        String t="dada";
        String s="789";
        //String sql = "insert into UserInfo(username,password)values('data','789')";
        //String sql = "insert into UserInfo(username,password)values('"+t+"')"+",'"+"789"+"')";
        String sql = "insert into UserInfo(username,password)values(?,?)";
        try {
            connection = getSQLConnection();
            //stmt=connection.createStatement();
            //con=DriverManager.getConnection("jdbc:jtds:sqlserver://" + IP + ":1433/" + DBName + ";useunicode=true;characterEncoding=UTF-8", USER, PWD);
            pstm = connection.prepareStatement(sql);
            //stmt.executeUpdate(sql);
            pstm.setString(1,t);
            pstm.setString(2,s);
            pstm.executeUpdate();
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
        //PreparedStatement pstm = null;
        String s="";
        Connection connection = null;
        ResultSet result=null;
        Statement statement=null;
        String sql = "select *from UserInfo";
        try {
            connection = getSQLConnection();
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
                //pstm.close();
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
    /*登陆检查是否存在该用户，以及用户存在时密码是否正确*/
    public int loginCheck(String loginName, String loginPass) throws SQLException {
        /*打开UserInfo数据库，查看用户信息，检查是否存在用户*/
        /*返回值1代表不存在用户，2代表存在用户密码正确，3代表密码不正确*/
        String checkSQL="select * from UserInfo where username=?";
        int result=1;
        PreparedStatement preparedStatement=null;
        Connection connection = null;
        ResultSet resultSet=null;

        try {
            connection=getSQLConnection();
            preparedStatement=connection.prepareStatement(checkSQL);
            preparedStatement.setString(1,loginName);
            resultSet=preparedStatement.executeQuery();
            /*先查看resultset的结果集是否为空*/
            if(resultSet.next()){
                /*存在该用户就把密码取出，与输入密码进行比较，因为在userinfo中username是主键，不会有重复*/
                String pass=resultSet.getString("password").replace(" ","");
                /*检查密码是否正确*/
                if(pass.equalsIgnoreCase(loginPass)){
                   result=2;
                }
                else {
                    result=3;
                }
            }
            else {
                result=1;
            }

        } catch (SQLException e) {
            System.out.println("出错1");
            e.printStackTrace();
        }finally {
            try{
                connection.close();
                preparedStatement.close();
                resultSet.close();
                System.out.println("登陆打开的数据库关闭成功");
            } catch (Exception e) {
                System.out.println("出错2");
                e.printStackTrace();
            }
        }
        return result;
    }
    /*连接数据库进行注册账户*/
    public static boolean longinInCheck(String username,String password) throws SQLException {
        /*在数据库中检查是否已经存在用户名*/
        String checkSQL="select * from UserInfo where username=?";
        String insertSQL="insert into UserInfo(username,password)values(?,?)";
        PreparedStatement pstmcheckt=null;
        PreparedStatement pstminsert=null;
        Connection connection = null;
        ResultSet resultSet=null;
        try {
            connection=getSQLConnection();//连接userinfo
            //执行用户名检查
            pstmcheckt=connection.prepareStatement(checkSQL);
            pstmcheckt.setString(1,username);
            resultSet=pstmcheckt.executeQuery();//保存查询结果
            //判断是否已经存在
            if(resultSet.next()){
                return false;//存在就返回false
            }
            else{//不存在就注册
                pstminsert=connection.prepareStatement(insertSQL);
                pstminsert.setString(1,username);
                pstminsert.setString(2,password);
                pstminsert.executeUpdate();
                return true;//返回true提示注册成功
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }finally {
            try{//关闭
                connection.close();
                pstminsert.close();
                pstmcheckt.close();
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
