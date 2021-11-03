package model.dao;

import java.sql.Connection;

public interface DaoConnection extends AutoCloseable {

    @Override
    void close();

    Connection getConnection();
}
