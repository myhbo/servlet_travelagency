package model.dao.implementation;

import exception.DaoException;
import model.dao.DaoConnection;
import model.dao.UserDao;
import model.dao.implementation.mapper.UserMapper;
import model.entity.User;
import model.entity.enums.Roles;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.management.relation.Role;
import java.sql.*;
import java.util.*;

public class JDBCUserDao implements UserDao {
    public static final Logger log = LogManager.getLogger();

    private final Connection connection;
    private final ResourceBundle resourceBundle = ResourceBundle.getBundle("db");

    public JDBCUserDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Optional<User> findById(long id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                resourceBundle.getString("user.find.by.id"))) {
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
    public void create(User user) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                resourceBundle.getString("user.create"), Statement.RETURN_GENERATED_KEYS)) {
            fillUserStatement(user, preparedStatement);
            preparedStatement.setBoolean(4, user.isEnabled());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                user.setId(resultSet.getLong(1));
            }
            try (PreparedStatement rolesPS = connection.prepareStatement(
                    resourceBundle.getString("role.create"))) {
                for (Roles role : user.getRole()) {
                    rolesPS.setLong(1, user.getId());
                    rolesPS.setString(2, role.name());
                    rolesPS.executeUpdate();
                }
            } catch (SQLException e) {
                throw new DaoException(e);
            }
        } catch (SQLException e) {

        }

    }

    @Override
    public void update(User user) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                resourceBundle.getString("user.update"))) {
            fillUserStatement(user, preparedStatement);
            preparedStatement.setLong(4, user.getId());
            preparedStatement.executeUpdate();

            try (PreparedStatement roleDeletePS = connection.prepareStatement(
                resourceBundle.getString("role.delete.by.id"))) {
                roleDeletePS.setLong(1, user.getId());
                roleDeletePS.executeUpdate();
            } try (PreparedStatement roleCreatePS = connection.prepareStatement(
                    resourceBundle.getString("role.create"))){
                for (Roles role : user.getRole()) {
                    roleCreatePS.setLong(1, user.getId());
                    roleCreatePS.setString(2, role.name());
                    roleCreatePS.executeUpdate();
                }

            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }

    }

    @Override
    public void delete(long id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                resourceBundle.getString("user.delete"))) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }

    }

    public void updateEnabled(long id, boolean enabled) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                resourceBundle.getString("user.update.enabled"))){
            preparedStatement.setBoolean(1, enabled);
            preparedStatement.setLong(2, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
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

    @Override
    public List<User> findAllPageable(int page, int size) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                resourceBundle.getString("user.find.all.pageable"))) {
            preparedStatement.setLong(1, size);
            preparedStatement.setLong(2, (long) size * page);
            ResultSet resultSet = preparedStatement.executeQuery();
            Map<Long, User> userMap = getUsersFromResultSet(resultSet);

            return new ArrayList<>(userMap.values());
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public long usersRecords() {
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(resourceBundle
                    .getString("user.count.records"));
            if (resultSet.next()) {
                return resultSet.getLong(1);
            }
            return 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }

    }

    private void fillUserStatement(User user, PreparedStatement preparedStatement)
            throws SQLException {
        preparedStatement.setString(1, user.getEmail());
        preparedStatement.setString(2, user.getPassword());
        preparedStatement.setString(3, user.getFullName());
    }
}
