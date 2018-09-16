import com.abosen.mybatis.mapper.UserMapper;
import com.abosen.mybatis.po.OrderExtend;
import com.abosen.mybatis.po.UserExtend;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

/**
 * @author qiubaisen
 * @date 2018/9/16
 */
public class LazyLoadingTest {

    private SqlSessionFactory getSqlSessionFactory(String resource) throws IOException {
        return new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream(resource));
    }


    @Test
    public void testDirectLoad() throws IOException {
        /**
         * 直接加载
         * 一次性加载所有内容
         * 存在多条sql
         */
        SqlSessionFactory defaultFactory = getSqlSessionFactory("sqlMapConfig.xml");
        SqlSession sqlSession = defaultFactory.openSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        UserExtend userExtend = mapper.selectUserWithOrders(1);
        System.out.println(userExtend);
    }

    @Test
    public void testInvasiveLazyLoading() throws IOException {
        /**
         * 侵入式延迟加载
         * 当访问对象详情 才进行查询
         * 只要访问对象信息 就进行查询
         */
        SqlSessionFactory defaultFactory = getSqlSessionFactory("mybatis_configs/invasiveLazyConfig.xml");
        SqlSession sqlSession = defaultFactory.openSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        UserExtend userExtend = mapper.selectUserWithOrders(1);
        System.out.println("查询非嵌套对象信息");
        userExtend.getUsername();
        System.out.println("查询嵌套对象信息");
        userExtend.getOrders();
    }

    @Test
    public void testDeepLazyLoading() throws IOException {
        /**
         * 深度延迟加载
         * 访问对象信息非嵌套信息 不进行查询
         * 访问对象嵌套信息 进行查询
         */
        SqlSessionFactory defaultFactory = getSqlSessionFactory("mybatis_configs/deepLazyConfig.xml");
        SqlSession sqlSession = defaultFactory.openSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        UserExtend userExtend = mapper.selectUserWithOrders(1);
        System.out.println("查询非嵌套对象信息");
        userExtend.getUsername();
        System.out.println("查询嵌套对象信息");
        userExtend.getOrders();
    }


}
