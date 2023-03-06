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
 * ������ʾ
 *
 * @author PengFuLin
 * 2023/2/21 22:50
 */
public class MybatisDemo {

    public static void main(String[] args) {
//        SqlSessionFactory sqlSessionFactory = code();  //��������
        SqlSessionFactory sqlSessionFactory = config(); //�����ļ�
        operate(sqlSessionFactory);
    }

    private static void operate(SqlSessionFactory sqlSessionFactory){
        //�����Ự
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //ָ������
        codeOperate(sqlSession);
        configOperate(sqlSession);
    }

    private static void configOperate(SqlSession sqlSession){
        //ֱ�ӷ��ʣ�mybatis�У����id�����ڻ�������Ψһ�ģ�������ü�㷽ʽֱ�ӷ��ʣ�������Ҫǰ׺ƴ�������ռ����
//        List<Object> objects = sqlSession.selectList("findUserMapper");
//        System.out.println(objects);
        //ӳ��������
        UserDao mapper = sqlSession.getMapper(UserDao.class);
        List<Map<?, ?>> mapperUser = mapper.findUserMapper();
        System.out.println(mapperUser);
    }

    private static void codeOperate(SqlSession sqlSession){
        //ֱ�ӷ��ʣ�mybatis�У����id�����ڻ�������Ψһ�ģ�������ü�㷽ʽֱ�ӷ��ʣ�������Ҫǰ׺ƴ�������ռ����
//        List<Object> objects = sqlSession.selectList("findUser");
//        System.out.println(objects);
        //ӳ��������
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
        //���񹤳�
        JdbcTransactionFactory jdbcTransactionFactory = new JdbcTransactionFactory();
        //����Դ
        DataSource dataSource = getDataSource();
        //mybatis��������
        Environment environment = new Environment("demo", jdbcTransactionFactory, dataSource);

        //mybatis��������
        Configuration configuration = new Configuration(environment);

        //���mapperӳ������
//        configuration.addMappers("mybatis.dao");  //ʹ��·��
        configuration.addMapper(UserDao.class); //ʹ������

        //�����Ự��������:��Configuration����SqlSessionFactory
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
