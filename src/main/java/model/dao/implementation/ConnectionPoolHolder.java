package model.dao.implementation;

import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;
import java.util.ResourceBundle;

public class ConnectionPoolHolder {
    private static final ResourceBundle resourceBundle = ResourceBundle.getBundle("db");
    private static volatile DataSource dataSource;
    static DataSource getDataSource() {
        if (dataSource == null) {
            synchronized (ConnectionPoolHolder.class) {
                if (dataSource == null) {
                    BasicDataSource ourDataSource = new BasicDataSource();
                    ourDataSource.setUrl(resourceBundle.getString("db"));
                    ourDataSource.setUsername(resourceBundle.getString("db.username"));
                    ourDataSource.setPassword(resourceBundle.getString("db.password"));
                    ourDataSource.setDriverClassName(resourceBundle.getString("db.driver"));
                    dataSource = ourDataSource;
                }
            }
        }
        return dataSource;
    }
}
