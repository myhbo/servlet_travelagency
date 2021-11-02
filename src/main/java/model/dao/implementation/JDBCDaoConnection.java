package model.dao.implementation;

import model.dao.DaoConnection;

import java.sql.Connection;

public class JDBCDaoConnection implements DaoConnection {
    private final Connection connection;

    public JDBCDaoConnection(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Connection getConnection() {
        return connection;
    }
}
