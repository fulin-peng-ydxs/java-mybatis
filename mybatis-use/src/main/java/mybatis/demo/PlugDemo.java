package mybatis.demo;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import mybatis.dao.UserDao;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import java.util.List;
import java.util.Map;

public class PlugDemo {


    public static void main(String[] args) {
        SqlSessionFactory sqlSessionFactory = MybatisDemo.config();
        UserDao userDao = sqlSessionFactory.openSession().getMapper(UserDao.class);
        page(userDao);
        pageInfo(userDao);
        customerPage(sqlSessionFactory.openSession());
    }

    private static void customerPage(SqlSession sqlSession){
        List<Object> findUser = sqlSession.selectList("findUser", null, new RowBounds(0, 1));
        System.out.println(findUser);
    }


    private static void page(UserDao userDao){
        //开启分页，会拦截下面第一个执行的查询操作
        System.out.println("===============page==============================");
        Page<Object> page = PageHelper.startPage(1, 10);
        List<Map<?, ?>> user = userDao.findUser();  //这个会被拦截生成List的代理对象Page返回
        System.out.println(user);
        System.out.println(page);
        System.out.println("===============no page=========================");
        user = userDao.findUser(); //这个不会处理，如果需要，则需要重新调用上面的分页方法
        System.out.println(user);
    }

    private static void pageInfo(UserDao userDao){
        PageHelper.startPage(1, 10);
        List<Map<?, ?>> user = userDao.findUser();  //这个会被拦截生成List的代理对象Page返回
        PageInfo<Map<?, ?>> mapPageInfo = new PageInfo<>(user);  //PageInfo会比Page对象的信息更加详细
        System.out.println(mapPageInfo);
    }
}

