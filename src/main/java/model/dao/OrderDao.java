package model.dao;

import model.entity.Order;

import java.util.List;

public interface OrderDao extends GenericDao<Order>{
    List<Order> findAllPageable(int page, int size, String columnToSort, String directionToSort);
    long orderRecords();

}
