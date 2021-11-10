package model.dao.implementation.mapper;

import model.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;


public class UserMapper implements ObjectMapper<User>{
    @Override
    public User getFromResultSet(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getLong("users.id"));
        user.setEmail(resultSet.getString("users.email"));
        user.setPassword(resultSet.getString("users.password"));
        user.setFullName(resultSet.getString("users.full_name"));
        user.setEnabled(resultSet.getBoolean("users.enabled"));
        return user;
    }

    @Override
    public User makeUnique(Map<Long, User> cache, User user) {
        cache.putIfAbsent(user.getId(), user);
        return cache.get(user.getId());
    }
}
