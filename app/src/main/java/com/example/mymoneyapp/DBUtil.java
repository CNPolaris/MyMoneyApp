package com.example.mymoneyapp;
import android.app.Dialog;
import android.provider.ContactsContract;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.Data;

import org.w3c.dom.Text;

import java.lang.reflect.Type;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DBUtil {
    //电脑ip地址可能会发生改变，要及时检查
    private static String IP = "192.168.31.150";
    private static String DBName = "moneyBookDB";
    private static String USER = "sa";
    private static String PWD = "123";

    public static float zhichu=0;
    public static float shouru=0;
    public static float monthzhichu=0;
    public static float monthshouru=0;
    private static Connection getSQLConnection() throws SQLException {
        Connection con = null;
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:jtds:sqlserver://" + IP + ":1433/" + DBName + ";characterEncoding=GBK", USER, PWD);
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
    /*插入语句，将新的用户名和密码插入到userinfo*/
    public static void Insert(String t, String s) throws SQLException {
        PreparedStatement pstm = null;
        Connection connection = null;
        Statement stmt=null;
        String sql = "insert into UserInfo(username,password)values(?,?)";
        try {
            connection = getSQLConnection();
            pstm = connection.prepareStatement(sql);
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
                resultSet.close();
                preparedStatement.close();
                connection.close();
                System.out.println("登陆打开的数据库关闭成功");
            } catch (Exception e) {
                System.out.println("出错2");
                e.printStackTrace();
            }
        }
        return result;
    }
    /*查询用户是否存在
     *输入用户名和密码查询
     */
    public static boolean serach(String name,String scert) throws SQLException {
        String checkSQL="select * from UserInfo where username=?";
        PreparedStatement pstmcheckt=null;
        Connection connection = null;
        ResultSet resultSet=null;
        try{
            connection=getSQLConnection();
            pstmcheckt=connection.prepareStatement(checkSQL);
            pstmcheckt.setString(1,name);
            resultSet=pstmcheckt.executeQuery();
            if(resultSet.next()){
                return true;
            }
            else{
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("查询失败");
            return false;
        }finally {
            connection.close();
            pstmcheckt.close();
            resultSet.close();
        }
    }
    /*连接数据库进行注册账户*/
    public static boolean longinInCheck(String username,String password) throws SQLException {
        /*在数据库中检查是否已经存在用户名*/
        String checkSQL = "select * from UserInfo where username=?";
        //当符合条件时，向userinfo表中插入数据
        String insertSQL = "insert into UserInfo(username,password)values(?,?)";
        //符合注册条件时，为用户创建新的表
        PreparedStatement pstmcheckt = null;
        PreparedStatement pstminsert = null;
        Connection connection = null;
        ResultSet resultSet = null;
        boolean exist=serach(username,password);
        if(exist==true){
            return false;
        }
        else{
            Insert(username,password);
            creatData(username);
            return true;
        }
    }
    public static void creatData(String username) throws SQLException {
        String tablename="data"+username;
        String tableSql="create table "+tablename+"(number VARCHAR(10) not null PRIMARY key,revenue VARCHAR(10) not NULL,types VARCHAR(10) not NULL,Amount FLOAT not null,tradeNotes VARCHAR(53),time Datetime)";
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        try {
            connection=getSQLConnection();
            preparedStatement=connection.prepareStatement(tableSql);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            preparedStatement.close();
            connection.close();
        }
    }

    //往用户收支数据库中添加收支记录(用户名，收支类型,具体分类，价格，备注)
    public static void InsertData(String username,String revenues,String type, float amount, String trade){
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        PreparedStatement preparedStatement2=null;
        ResultSet resultSet=null;
        String ownData= "data"+username;System.out.println(ownData);
        //String lensql="select max(number) from data1875091912";
        //String insertsql="insert into data1875091912 (number,types,Amount,tradeNotes,time) values(?,?,?,?,getdate())";
        //操作指定的表
        String lensql="select max(number) from "+ownData;
        String insertsql="insert into "+ownData+ "(number,revenue,types,Amount,tradeNotes,time) values(?,?,?,?,?,getdate())";
        String lastone="";
        //连接数据库
        try {
            connection=getSQLConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //sql语句执行
        try{//找到最后面一个记录编号
            preparedStatement=connection.prepareStatement(lensql);
            resultSet=preparedStatement.executeQuery();
                while(resultSet.next()){
                    //判断一下是不是已经有序号在表中
                    String temp=resultSet.getString(1);
                    if(temp!=null){//有的话加一
                        lastone=String.valueOf(Integer.valueOf(temp)+1);
                        System.out.println(lastone);
                    }
                    else {//没有的话设为1
                        lastone="1";
                    }
                }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try{
            //开始进行插入操作，收支时间已经写在sql语句中
            preparedStatement2=connection.prepareStatement(insertsql);
            preparedStatement2.setString(1,lastone);
            preparedStatement2.setString(2,revenues);
            preparedStatement2.setString(3,type);
            preparedStatement2.setFloat(4,amount);
            preparedStatement2.setString(5,trade);
            preparedStatement2.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try{
            resultSet.close();
            preparedStatement.close();
            preparedStatement2.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //从数据库中查询收支数据
    public static void inquireData(String username,String date){
        Connection connection=null;
        ResultSet resultSet=null;
        Statement statement=null;
        ResultSet resultSetzhi=null,resultSetmonthzhi=null;
        Statement statementzhi=null,statementmonthzhi=null;
        PreparedStatement preparedStatement=null;
        ResultSet resultSetshou=null,resultSetmonthshou=null;
        Statement statementshou = null,statementmonthshou=null;
        String userdata="data"+username;
        String dateSQL="select * from "+userdata+" where CONVERT(VARCHAR,time,120)like '%"+date+"%'";
        //更新userInfo表中的收支信息
        String insertDataSQL="UPDATE UserInfo set income=? ,expend=? ,balance=? where username='"+username+"'";
        String zhichuSQL="select SUM(Amount) FROM "+userdata+" where Amount<=0";//查询所有的支出
        String shouruSQL="select SUM(Amount) FROM "+userdata+" where Amount>=0";//查询所有的收入
        //按照月份查询收入、支出金额
        String monthZhiChuSQl="SELECT SUM(Amount)FROM "+userdata+" as data_a WHERE Amount<=0 and EXISTS(SELECT * FROM "+userdata +" as data_b WHERE CONVERT(VARCHAR,time,120)like '%"+date+"%'and data_a.number=data_b.number)";
        String monthShouRuSQl="SELECT SUM(Amount)FROM "+userdata+" as data_a WHERE Amount>=0 and EXISTS(SELECT * FROM "+userdata +" as data_b WHERE CONVERT(VARCHAR,time,120)like '%"+date+"%'and data_a.number=data_b.number)";
        try{
            connection=getSQLConnection();
            statement=connection.createStatement();
            resultSet=statement.executeQuery(dateSQL);
            List<GetData>list=new ArrayList<GetData>();
            while (resultSet.next()){
                /*String bei=resultSet.getString("Amount");
                System.out.println(bei);*/
                list.add(new GetData(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),resultSet.getFloat(4),resultSet.getString(5),resultSet.getDate(6)));
            }
            for(GetData s:list){
                System.out.println(s);
            }
            //统计收入和支出
            statementshou=connection.createStatement();
            resultSetshou=statementshou.executeQuery(shouruSQL);
            statementzhi=connection.createStatement();
            resultSetzhi=statementzhi.executeQuery(zhichuSQL);
            if(resultSetshou.next()&&resultSetzhi.next()){
                zhichu=resultSetzhi.getFloat(1);
                shouru=resultSetshou.getFloat(1);
            }else{
                zhichu=0;
                shouru=0;
            }
            //更新收支
            preparedStatement=connection.prepareStatement(insertDataSQL);
            preparedStatement.setFloat(1,shouru);
            preparedStatement.setFloat(2,zhichu);
            preparedStatement.setFloat(3,shouru+zhichu);
            preparedStatement.executeUpdate();
            //按月查询收支的结果显示
            //支出
            statementmonthzhi=connection.createStatement();
            resultSetmonthzhi=statementmonthzhi.executeQuery(monthZhiChuSQl);
            //收入
            statementmonthshou=connection.createStatement();
            resultSetmonthshou=statementmonthshou.executeQuery(monthShouRuSQl);
            if(resultSetmonthzhi.next()&&resultSetmonthshou.next()){
                monthzhichu=resultSetmonthzhi.getFloat(1);
                monthshouru=resultSetmonthshou.getFloat(1);
            }else{
                monthzhichu=0;
                monthshouru=0;
            }
            System.out.println("日收入"+monthshouru);
            System.out.println("日支出"+monthzhichu);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try{
                resultSet.close();
                statement.close();
                resultSetzhi.close();
                statementzhi.close();

                preparedStatement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    //清除用户的收支表的内容
    public static void ClearData(String username){
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        String ownData="data"+username;
        String clearSql="delete from "+ownData;
        try{
            connection=getSQLConnection();
            preparedStatement=connection.prepareStatement(clearSql);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try{
                preparedStatement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    //用户注销账户
    public static void logout(String username,String password){
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        String ownData= "data"+username;
        String dropSql="drop table "+ownData;
        String dropuser="delete from UserInfo where username=?";
        try{
            connection=getSQLConnection();
            preparedStatement=connection.prepareStatement(dropSql);
            preparedStatement.executeUpdate();
            preparedStatement=connection.prepareStatement(dropuser);
            preparedStatement.setString(1,username);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try{
                preparedStatement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
