package model.dao.implementation;

import model.dao.DaoConnection;
import model.dao.DaoFactory;
import model.dao.UserDao;

import javax.sql.DataSource;
import java.sql.SQLException;

public class JDBCDaoFactory extends DaoFactory {
    private final DataSource dataSource = ConnectionPoolHolder.getDataSource();


    @Override
    public UserDao createUserDao(DaoConnection connection) {
        return new JDBCUserDao(connection.getConnection());
    }

    @Override
    public DaoConnection getConnection() {
        try {
            return new JDBCDaoConnection(dataSource.getConnection());
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
