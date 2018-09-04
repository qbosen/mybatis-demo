package com.abosen.mybatis.demo;


import java.sql.*;

/**
 * @author qiubaisen
 * @date 2018/9/3
 */
public class JDBCDemoTest {
    public static void main(String[] args) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            /*
            对于 mysql8.0+ 需要设置驱动地址 *.cj.jdbc.*
            url 中需要指定 useSSL=false
            */


            // 加载数据库驱动
            Class.forName("com.mysql.cj.jdbc.Driver");

            // 通过驱动管理类获取数据库链接connection = DriverManager
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/ssm?characterEncoding=utf-8&useSSL=false",
                    "abosen",
                    "abosen"
            );

            // 定义sql语句 ?表示占位符
            String sql = "select * from user where username = ?";
            // 获取预处理 statement
            preparedStatement = connection.prepareStatement(sql);

            // 设置参数，第一个参数为 sql 语句中参数的序号（从 1 开始），第二个参数为设置的
            preparedStatement.setString(1, "王五");
            // 向数据库发出 sql 执行查询，查询出结果集
            resultSet = preparedStatement.executeQuery();
            // 遍历查询结果集
            while (resultSet.next()) {
                System.out.println(resultSet.getString("id") + " " + resultSet.getString("username"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 释放资源
            // 与创建资源方向相反
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
