package model.dao.implementation;

import exception.DaoException;
import jakarta.validation.Valid;
import model.dao.OrderDao;
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

public class JDBCOrderDao implements OrderDao {
    public static final Logger log = LogManager.getLogger();

    private final Connection connection;
    private final ResourceBundle resourceBundle = ResourceBundle.getBundle("db");

    public JDBCOrderDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Optional<Order> findById(long id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                resourceBundle.getString("order.find.by.id"))) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            Map<Long, Order> orderMap = getOrdersFromResultSet(resultSet);
            return orderMap.values().stream().findAny();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<Order> findAllPageable(int page,
                                       int size,
                                       String columnToSort,
                                       String directionToSort) {
        try (Statement st = connection.createStatement()) {
            String query = resourceBundle.getString("order.find.all.pageable") +
                    " order by " + columnToSort +
                    " " + directionToSort +
                    " limit " + size +
                    " offset " + (long) size * page;
            ResultSet rs = st.executeQuery(query);
            Map<Long, Order> tourMap = getOrdersFromResultSet(rs);
            return new ArrayList<>(tourMap.values());
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public boolean create(Order order) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                resourceBundle.getString("order.create"))) {
            fillOrderStatement(order, preparedStatement);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DaoException(e);
        }
    }

    @Override
    public void update(@Valid Order order) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                resourceBundle.getString("order.update"))) {
            fillOrderStatement(order, preparedStatement);
            preparedStatement.setLong(6, order.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DaoException(e);
        }
    }

    @Override
    public void delete(long id) {
    }

    @Override
    public long orderRecords() {
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(
                    resourceBundle.getString("order.count.records"));
            if (resultSet.next()) {
                return resultSet.getLong(1);
            } else return 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DaoException(e);
        }
    }

    private void fillOrderStatement(Order order, PreparedStatement preparedStatement)
            throws SQLException {
        preparedStatement.setLong(1, order.getTour().getId());
        preparedStatement.setLong(2, order.getUser().getId());
        preparedStatement.setString(3, order.getStatus().name());
        preparedStatement.setDouble(4, order.getPrice());
        preparedStatement.setDouble(5, order.getDiscount());
    }

    private Map<Long, Order> getOrdersFromResultSet(ResultSet resultSet)
            throws SQLException {
        Map<Long, Order> orderMap = new LinkedHashMap<>();
        Map<Long, User> userMap = new HashMap<>();
        Map<Long, Tour> tourMap = new HashMap<>();

        OrderMapper orderMapper = new OrderMapper();
        UserMapper userMapper = new UserMapper();
        TourMapper tourMapper = new TourMapper();

        while (resultSet.next()) {
            Order order = orderMapper.getFromResultSet(resultSet);
            orderMapper.makeUnique(orderMap, order);
        }

        for (Order order : orderMap.values()) {
            try (PreparedStatement toursPS = connection.prepareStatement(
                    resourceBundle.getString("order.join.tour"))) {
                toursPS.setLong(1, order.getId());
                ResultSet toursResultSet = toursPS.executeQuery();
                while (toursResultSet.next()) {
                    Tour tour = tourMapper.getFromResultSet(toursResultSet);
                    tour = tourMapper.makeUnique(tourMap, tour);
                    if (tour.getId() != 0) {
                        order.setTour(tour);
                    }
                }
            }

            try (PreparedStatement usersPS = connection.prepareStatement(
                    resourceBundle.getString("order.join.user"))) {
                usersPS.setLong(1, order.getId());
                ResultSet usersResultSet = usersPS.executeQuery();
                while (usersResultSet.next()) {
                    User user = userMapper.getFromResultSet(usersResultSet);
                    user = userMapper.makeUnique(userMap, user);
                    if (usersResultSet.getString("user_roles.role") != null) {
                        Roles role = Roles
                                .valueOf(usersResultSet.getString("user_roles.role"));
                        user.getRole().add(role);
                    }
                    if (user.getId() != 0) {
                        order.setUser(user);
                    }
                }
            }
        }
        return orderMap;
    }
}
