package model.dao.implementation;

import model.dao.*;

import javax.sql.DataSource;
import java.sql.SQLException;

public class JDBCDaoFactory extends DaoFactory {
    private final DataSource dataSource = ConnectionPoolHolder.getDataSource();


    @Override
    public UserDao createUserDao(DaoConnection connection) {
        return new JDBCUserDao(connection.getConnection());
    }

    @Override
    public TourDao createTourDao(DaoConnection connection) {
        return new JDBCTourDao(connection.getConnection());
    }

    @Override
    public OrderDao createOrderDao(DaoConnection connection) {
        return new JDBCOrderDao(connection.getConnection());
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
