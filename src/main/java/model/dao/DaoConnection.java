package model.dao;

import java.sql.Connection;

public interface DaoConnection extends AutoCloseable {
    @Override
    default void close() throws Exception {

    }
    Connection getConnection();
}
