package model.service;

import controller.command.order.OrderMarkRejected;
import controller.dto.OrderDTO;
import exception.DaoException;
import model.dao.*;
import model.entity.Order;
import model.entity.Tour;
import model.entity.User;
import model.entity.enums.OrderStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.validation.*;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class OrderService {
    private static final Logger log = LogManager.getLogger();
    private final DaoFactory daoFactory = DaoFactory.getInstance();

    public List<Order> findAllOrdersPageable(int page,
                                             int size,
                                             String columnToSort,
                                             String directionToSort) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            OrderDao orderDao = daoFactory.createOrderDao(connection);
            return orderDao.findAllPageable(page, size, columnToSort, directionToSort);
        } catch (DaoException e) {
        }
        return Collections.emptyList();
    }

    public Order findOrderById(long id) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            OrderDao orderDao = daoFactory.createOrderDao(connection);
            return orderDao.findById(id).orElseThrow(IllegalArgumentException::new);
        } catch (DaoException e) {
        }
        return null;
    }

    public void createOrder(long userId, long tourId) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            UserDao userDao = daoFactory.createUserDao(connection);
            TourDao tourDao = daoFactory.createTourDao(connection);
            OrderDao orderDao = daoFactory.createOrderDao(connection);

            //connection.beginTransaction();

            User user = userDao.findById(userId).orElseThrow(IllegalArgumentException::new);
            Tour tour = tourDao.findById(tourId).orElseThrow(IllegalArgumentException::new);
            Order order = Order.builder()
                    .user(user)
                    .tour(tour)
                    .status(OrderStatus.PROCESSING)
                    .price(tour.getPrice())
                    .discount(0.0)
                    .build();

            orderDao.create(order);

            //connection.commit();
        } catch (DaoException e) {
            e.printStackTrace();

        }
    }

    public void markOrderConfirmed(long id) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            OrderDao orderDao = daoFactory.createOrderDao(connection);
            UserDao userDao = daoFactory.createUserDao(connection);

            //connection.beginTransaction();

            Order order = findOrderById(id);
            User user = order.getUser();

            order.setStatus(OrderStatus.CONFIRMED);


            orderDao.update(order);
            userDao.update(user);


            //connection.commit();
        } catch (DaoException e) {
        }
    }

    public void markOrderRejected(long id) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            OrderDao orderDao = daoFactory.createOrderDao(connection);

            //connection.beginTransaction();

            Order order = findOrderById(id);
            order.setStatus(OrderStatus.REJECTED);
            orderDao.update(order);

            //connection.commit();
        } catch (DaoException e) {

        }
    }

    public long orderRecords() {
        try (DaoConnection connection = daoFactory.getConnection()) {
            OrderDao orderDao = daoFactory.createOrderDao(connection);
            return orderDao.orderRecords();
        } catch (DaoException e) {
        }
        return 0;
    }

    public void setDiscount(long id, OrderDTO orderDTO) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            OrderDao orderDao = daoFactory.createOrderDao(connection);
            UserDao userDao = daoFactory.createUserDao(connection);

            Order order = findOrderById(id);
            User user = order.getUser();

            Order orderBuilder = Order.builder()
                    .id(id)
                    .tour(order.getTour())
                    .user(order.getUser())
                    .status(order.getStatus())
                    .price(order.getTour().getPrice() * ((100 - orderDTO.getDiscount())/100))
                    .discount(orderDTO.getDiscount())
                    .build();

            ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
            Validator validator = validatorFactory.getValidator();
            Set<ConstraintViolation<Order>> validate = validator.validate(orderBuilder);

            validatorFactory.close();

            orderDao.update(orderBuilder);
            userDao.update(user);

            log.info("discount set in order service");
        }

    }
}
