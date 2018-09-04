package com.abosen.mybatis.demo;

import com.abosen.mybatis.po.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author qiubaisen
 * @date 2018/9/3
 */
public class MybatisDemoTest {
    private SqlSessionFactory sqlSessionFactory;

    @Before
    public void init() {
        //指定全局配置文件路径
        String resource = "sqlMapConfig.xml";
        try {
            //加载资源文件（全局配置文件和映射文件）
            InputStream inputStream = Resources.getResourceAsStream(resource);
            //构建者模式，创建SqlSessionFactory对象
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSelect() {
        //由SqlSessionFactory工厂去创建SqlSession
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //调用sqlSession接口，去实现数据库的增删改查操作
        User user = sqlSession.selectOne("findUserById", 1);
        System.out.println(user);
        //释放资源
        sqlSession.close();
    }
}
