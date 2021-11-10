package model.dao.implementation.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;


public interface ObjectMapper<T> {
    T getFromResultSet(ResultSet resultSet) throws SQLException;

    T makeUnique(Map<Long, T> cache, T object);
}
