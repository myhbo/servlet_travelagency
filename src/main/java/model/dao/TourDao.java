package model.dao;

import model.entity.Tour;

import java.util.List;

public interface TourDao extends GenericDao<Tour> {
    List<Tour> findAllPageable(int page,
                               int size,
                               String columnToSort,
                               String directionToSort);
    long tourRecords();
    void toggleHot(Tour tour);

}
