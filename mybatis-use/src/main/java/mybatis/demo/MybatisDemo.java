package mybatis.demo;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import mybatis.dao.UserDao;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import javax.sql.DataSource;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Properties;
/**
 * 快速演示
 *
 * @author PengFuLin
 * 2023/2/21 22:50
 */
public class MybatisDemo {

    public static void main(String[] args) {
//        SqlSessionFactory sqlSessionFactory = code();  //代码配置
        SqlSessionFactory sqlSessionFactory = config(); //配置文件
        operate(sqlSessionFactory);
    }

    private static void operate(SqlSessionFactory sqlSessionFactory){
        //创建会话
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //指定访问
        codeOperate(sqlSession);
        configOperate(sqlSession);
    }

    private static void configOperate(SqlSession sqlSession){
        //直接访问：mybatis中，如果id属性在环境中是唯一的，则可以用简便方式直接访问，否则需要前缀拼凑命名空间访问
//        List<Object> objects = sqlSession.selectList("findUserMapper");
//        System.out.println(objects);
        //映射器访问
        UserDao mapper = sqlSession.getMapper(UserDao.class);
        List<Map<?, ?>> mapperUser = mapper.findUserMapper();
        System.out.println(mapperUser);
    }

    private static void codeOperate(SqlSession sqlSession){
        //直接访问：mybatis中，如果id属性在环境中是唯一的，则可以用简便方式直接访问，否则需要前缀拼凑命名空间访问
//        List<Object> objects = sqlSession.selectList("findUser");
//        System.out.println(objects);
        //映射器访问
        UserDao mapper = sqlSession.getMapper(UserDao.class);
        List<Map<?, ?>> mapperUser = mapper.findUser();
        System.out.println(mapperUser);
    }

    private static SqlSessionFactory config(){
        InputStream resource = MybatisDemo.class.getResourceAsStream("/conf/mybatis-demo-conf.xml");
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        return sqlSessionFactoryBuilder.build(resource);
    }

    private static SqlSessionFactory code(){
        //事务工厂
        JdbcTransactionFactory jdbcTransactionFactory = new JdbcTransactionFactory();
        //数据源
        DataSource dataSource = getDataSource();
        //mybatis基础环境
        Environment environment = new Environment("demo", jdbcTransactionFactory, dataSource);

        //mybatis运行配置
        Configuration configuration = new Configuration(environment);

        //添加mapper映射器：
//        configuration.addMappers("mybatis.dao");  //使用路径
        configuration.addMapper(UserDao.class); //使用类型

        //创建会话工厂构建:用Configuration创建SqlSessionFactory
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        return sqlSessionFactoryBuilder.build(configuration);
    }

    private static DataSource getDataSource(){
        Properties properties = new Properties();
        try {
            properties.load(MybatisDemo.class.getResourceAsStream("/druid.properties"));
            return DruidDataSourceFactory.createDataSource(properties);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
