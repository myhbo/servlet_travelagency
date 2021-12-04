package model.dao.implementation;

import exception.DaoException;
import model.dao.UserDao;
import model.dao.implementation.mapper.OrderMapper;
import model.dao.implementation.mapper.TourMapper;
import model.dao.implementation.mapper.UserMapper;
import model.entity.Order;
import model.entity.Tour;
import model.entity.User;
import model.entity.enums.Roles;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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

        Map<Long, Tour> tourMap = new LinkedHashMap<>();
        TourMapper tourMapper = new TourMapper();

        Map<Long, Order> orderMap = new LinkedHashMap<>();
        OrderMapper orderMapper = new OrderMapper();


        while (resultSet.next()) {
            User user = userMapper.getFromResultSet(resultSet);
            userMapper.makeUnique(userMap, user);
        }


        for (User user : userMap.values()) {
            try (PreparedStatement ordersPS = connection.prepareStatement(
                    resourceBundle.getString("user.join.orders.tours"))) {
                ordersPS.setLong(1, user.getId());
                ResultSet userOrdersResultSet = ordersPS.executeQuery();

                while (userOrdersResultSet.next()) {
                    Order order = orderMapper.getFromResultSet(userOrdersResultSet);
                    Tour tour = tourMapper.getFromResultSet(userOrdersResultSet);

                    order = orderMapper.makeUnique(orderMap, order);
                    tour = tourMapper.makeUnique(tourMap, tour);

                    if ((order.getId() != 0) && !user.getOrders().contains(order)) {
                        order.setUser(user);
                        order.setTour(tour);
                        user.getOrders().add(order);
                    }
                }
            }
        }
        return userMap;
    }

    @Override
    public boolean create(User user) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                resourceBundle.getString("user.create"), Statement.RETURN_GENERATED_KEYS)) {
            fillUserStatement(user, preparedStatement);
            preparedStatement.setBoolean(5, user.isEnabled());
            preparedStatement.setString(4, user.getRole().name());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                user.setId(resultSet.getLong(1));
            }
            return true;
        } catch (SQLException e) {
            log.info("cant create" + e.getMessage());
            return false;
        }

    }

    @Override
    public void update(User user) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                resourceBundle.getString("user.update"))) {
            fillUserStatement(user, preparedStatement);
            preparedStatement.setLong(5, user.getId());
            log.info(preparedStatement);
            preparedStatement.executeUpdate();

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
    public List<User> findAllPageable(int page,
                                      int size,
                                      String columnToSort,
                                      String directionToSort) {
        try (Statement statement = connection.createStatement()) {
            String query = resourceBundle.getString("user.find.all.pageable")
                    + " order by " + columnToSort
                    + " " + directionToSort + " "
                    + " limit " + size
                    + " offset " + (long) size * page;
            ResultSet resultSet = statement.executeQuery(query);

            Map<Long, User> userMap = getUsersFromResultSet(resultSet);

            return new ArrayList<>(userMap.values());

        } catch (SQLException e) {
            log.error("cant get users " + e.getMessage());
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
        preparedStatement.setString(2, user.getFullName());
        preparedStatement.setString(3, user.getPassword());
        preparedStatement.setString(4, user.getRole().name());
    }
}
