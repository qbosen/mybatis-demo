import com.abosen.mybatis.mapper.OrdersMapper;
import com.abosen.mybatis.po.Orders;
import com.abosen.mybatis.po.OrdersExample;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

/**
 * @author qiubaisen
 * @date 2018/9/16
 */
public class ReverseEngineeringTest {
    private SqlSessionFactory sqlSessionFactory;

    @Before
    public void init() {
        //加载配置文件
        String resource = "sqlMapConfig.xml";
        try {
            InputStream inputStream = Resources.getResourceAsStream(resource);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSelectOrderById() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        OrdersMapper mapper = sqlSession.getMapper(OrdersMapper.class);
        Orders orders = mapper.selectByPrimaryKey(3);
        System.out.println(orders);
    }


    @Test
    public void testOrdersExampleIdBetween() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        OrdersMapper mapper = sqlSession.getMapper(OrdersMapper.class);
        OrdersExample ordersExample = new OrdersExample();
        OrdersExample.Criteria criteria = ordersExample.createCriteria();
        criteria.andIdBetween(1, 4);
        mapper.selectByExample(ordersExample);
    }


    @Test
    public void testOrdersExampleComplex() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        OrdersMapper mapper = sqlSession.getMapper(OrdersMapper.class);
        OrdersExample ordersExample = new OrdersExample();
        ordersExample.or()
                .andIdGreaterThan(4)
                .andUserIdEqualTo(1)
                .andNumberIn(Arrays.asList("1000010", "1000011", "1000012"));
        mapper.selectByExample(ordersExample);
    }

}
