package model.dao.implementation;

import model.dao.DaoConnection;

import java.sql.Connection;
import java.sql.SQLException;

public class JDBCDaoConnection implements DaoConnection {
    private final Connection connection;

    public JDBCDaoConnection(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Connection getConnection() {
        return connection;
    }
}
