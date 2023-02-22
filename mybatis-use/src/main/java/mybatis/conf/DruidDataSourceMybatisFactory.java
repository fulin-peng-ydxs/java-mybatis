package mybatis.conf;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.apache.ibatis.datasource.DataSourceFactory;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * @author PengFuLin
 * 2023/2/22 22:37
 */
public class DruidDataSourceMybatisFactory implements DataSourceFactory {
    Properties properties=null;

    @Override
    public void setProperties(Properties properties) {
        this.properties=properties;
    }

    @Override
    public DataSource getDataSource() {
        try {
            return DruidDataSourceFactory.createDataSource(properties);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
