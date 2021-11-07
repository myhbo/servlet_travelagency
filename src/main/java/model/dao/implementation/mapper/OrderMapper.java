package model.dao.implementation.mapper;

import model.entity.Order;
import model.entity.enums.OrderStatus;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class OrderMapper implements ObjectMapper<Order> {

    @Override
    public Order getFromResultSet(ResultSet resultSet) throws SQLException {
        Order order = new Order();
        order.setId(resultSet.getLong("orders.id"));

        String orderStatus = resultSet.getString("orders.status");
        order.setStatus(orderStatus != null ? OrderStatus.valueOf(orderStatus) : null);

        order.setPrice(resultSet.getDouble("orders.price"));
        order.setDiscount(resultSet.getDouble("orders.discount"));
        return order;
    }

    @Override
    public Order makeUnique(Map<Long, Order> cache, Order order) {
        cache.putIfAbsent(order.getId(), order);
        return cache.get(order.getId());
    }
}
