package model.service;

import controller.dto.OrderDTO;
import exception.DaoException;
import model.dao.*;
import model.entity.Order;
import model.entity.Tour;
import model.entity.User;
import model.entity.enums.OrderStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collections;
import java.util.List;


public class OrderService {
    private static final Logger log = LogManager.getLogger();
    private final DaoFactory daoFactory = DaoFactory.getInstance();

    /**
     *
     * @param page number of specific page in a query
     * @param size amount of items on the page
     * @param columnToSort column of items to sort it
     * @param directionToSort direction to sort a column
     * @return list of specific orders that we get with a query,
     * parameters of which we provide
     */
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

    /**
     *
     * @param id of requested order
     * @return Optional of order or throw IllegalArgumentsException
     */
    public Order findOrderById(long id) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            OrderDao orderDao = daoFactory.createOrderDao(connection);
            return orderDao.findById(id).orElseThrow(IllegalArgumentException::new);
        } catch (DaoException e) {
        }
        return null;
    }

    /**
     *
     * @param userId id of user that ordering
     * @param tourId if of tour that user ordering
     * @return true if method completes
     */
    public boolean createOrder(long userId, long tourId) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            UserDao userDao = daoFactory.createUserDao(connection);
            TourDao tourDao = daoFactory.createTourDao(connection);
            OrderDao orderDao = daoFactory.createOrderDao(connection);

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
            return true;
        } catch (DaoException e) {
            log.error(e.getMessage());
            return false;
        }
    }

    /**
     * change status field of order taken by id. Sets it CONFIRMED
     * @param id of order
     */
    public void markOrderConfirmed(long id) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            OrderDao orderDao = daoFactory.createOrderDao(connection);
            UserDao userDao = daoFactory.createUserDao(connection);

            Order order = findOrderById(id);
            User user = order.getUser();

            order.setStatus(OrderStatus.CONFIRMED);

            orderDao.update(order);
            userDao.update(user);

        } catch (DaoException e) {
        }
    }

    /**
     * change status field of order taken by id. Sets it REJECTED
     * @param id of order
     */
    public void markOrderRejected(long id) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            OrderDao orderDao = daoFactory.createOrderDao(connection);

            Order order = findOrderById(id);
            order.setStatus(OrderStatus.REJECTED);
            orderDao.update(order);

        } catch (DaoException e) {
        }
    }

    /**
     *
     * @return amount of all orders
     */
    public long orderRecords() {
        try (DaoConnection connection = daoFactory.getConnection()) {
            OrderDao orderDao = daoFactory.createOrderDao(connection);
            return orderDao.orderRecords();
        } catch (DaoException e) {
        }
        return 0;
    }

    /**
     * applying amount of discount, to order with specific id. for calculating used default price of tour.
     * @param id of affecting order
     * @param orderDTO where is discount amount field
     * @return true in case of method completion
     */
    public boolean setDiscount(long id, OrderDTO orderDTO) {
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
                    .price(order.getTour().getPrice() * ((100 - orderDTO.getDiscount()) / 100))
                    .discount(orderDTO.getDiscount())
                    .build();

            orderDao.update(orderBuilder);
            userDao.update(user);
            log.info("discount set");
            return true;
        } catch (Exception e) {
            return false;
        }

    }
}
