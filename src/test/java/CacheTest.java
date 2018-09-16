import com.abosen.mybatis.mapper.UserMapper;
import com.abosen.mybatis.po.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

/**
 * @author qiubaisen
 * @date 2018/9/16
 */
public class CacheTest {
    /**
     * 同一个 session 一级缓存
     * 同一个 mapper 二级缓存
     */
    private SqlSessionFactory defaultFactory;
    private SqlSessionFactory cacheFactory;

    @Before
    public void init() {
        //加载配置文件
        String resource = "sqlMapConfig.xml";
        String cacheEnableResource = "mybatis_configs/cacheConfig.xml";
        try {
            defaultFactory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream(resource));
            cacheFactory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream(cacheEnableResource));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testOneLevelCache() {
        SqlSession sqlSession = defaultFactory.openSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        // 第一次
        System.out.println(mapper.findUserById(1));
        // 第二次 从缓存中查询, 只会有一次sql查询
        System.out.println(mapper.findUserById(1));
    }

    @Test
    public void testOneLevelCache2() {
        SqlSession sqlSession = defaultFactory.openSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        // 第一次
        System.out.println(mapper.findUserById(1));
        // 执行一次修改操作 会清空一级缓存
        User user = new User();
        user.setUsername("山竹");
        user.setAddress("广东");
        mapper.insertUser(user);

        // 第二次 没有缓存， 共三句sql
        System.out.println(mapper.findUserById(1));
    }

    /**
     * 开启二级缓存
     * 1. 需要在配置中开启总开关
     * 2. 在mapper.xml中开启
     * 3. 数据需要实现 serializable 接口， 如果存在父类，父类页需要实现序列号接口
     */

    @Test
    public void testTwoLevelCache() {
        // 缓存命中 一次sql
        SqlSession sqlSession1 = cacheFactory.openSession();
        SqlSession sqlSession2 = cacheFactory.openSession();

        UserMapper mapper1 = sqlSession1.getMapper(UserMapper.class);
        UserMapper mapper2 = sqlSession2.getMapper(UserMapper.class);

        System.out.println(mapper1.findUserById(1));
        sqlSession1.close();

        System.out.println(mapper2.findUserById(1));
        sqlSession2.close();

    }

    @Test
    public void testTwoLevelCache2() {
        // 一次update操作 清空缓存 所以存在3次sql
        SqlSession sqlSession1 = cacheFactory.openSession();
        SqlSession sqlSession2 = cacheFactory.openSession();
        SqlSession sqlSession3 = cacheFactory.openSession();

        UserMapper mapper1 = sqlSession1.getMapper(UserMapper.class);
        UserMapper mapper2 = sqlSession2.getMapper(UserMapper.class);
        UserMapper mapper3 = sqlSession2.getMapper(UserMapper.class);

        System.out.println(mapper1.findUserById(1));
        sqlSession1.close();

        User user = new User();
        user.setId(1);
        user.setUsername("新的名字");
        mapper2.updateUser(user);

        System.out.println(mapper3.findUserById(1));
        sqlSession3.close();

    }
}
