package model.dao.implementation;

import exception.DaoException;
import model.dao.UserDao;
import model.dao.implementation.mapper.UserMapper;
import model.entity.User;
import model.entity.enums.Roles;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;

public class JDBCUserDao implements UserDao {
    private final Connection connection;
    private final ResourceBundle resourceBundle = ResourceBundle.getBundle("db");

    public JDBCUserDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Optional<User> findById(long id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    resourceBundle.getString("user.find.by.id"));
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            Map<Long, User> userMap = getUsersFromResultSet(resultSet);
            return userMap.values().stream().findAny();
        }
        catch (SQLException e) {
           throw new DaoException(e);
        }
    }


    private Map<Long, User> getUsersFromResultSet(ResultSet resultSet)
            throws SQLException {
        Map<Long, User> userMap = new LinkedHashMap<>();
        UserMapper userMapper = new UserMapper();

        while (resultSet.next()) {
            User user = userMapper.getFromResultSet(resultSet);
            userMapper.makeUnique(userMap, user);
        }


        for (User user : userMap.values()) {
            try (PreparedStatement userRolesPS = connection.prepareStatement(
                        resourceBundle.getString("user.get.role"))) {
                userRolesPS.setLong(1, user.getId());
                ResultSet userRolesRS = userRolesPS.executeQuery();

                while (userRolesRS.next()) {
                    Roles role = Roles.valueOf(userRolesRS.getString("user_roles.role"));
                    user.getRole().add(role);
                }
            }
        }
        return userMap;
    }

    @Override
    public void create(User entity) {

    }

    @Override
    public void update(User entity) {

    }

    @Override
    public void delete(User entity) {

    }

    @Override
    public Optional<User> findByEmail(String email) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                resourceBundle.getString("user.find.by.email"))) {
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            Map<Long, User> userMap = getUsersFromResultSet(resultSet);
            return userMap.values().stream().findAny();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
}
