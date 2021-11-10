package model.service;

import controller.dto.TourDTO;
import exception.DaoException;
import model.dao.DaoConnection;
import model.dao.DaoFactory;
import model.dao.TourDao;
import model.entity.Tour;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collections;
import java.util.List;

public class TourService {
    private static final Logger log = LogManager.getLogger();

    private final DaoFactory daoFactory = DaoFactory.getInstance();

    /**
     *
     * @param page number of specific page in a query
     * @param size amount of items on the page
     * @param columnToSort column of items to sort it
     * @param directionToSort direction to sort a column
     * @return list of specific tours that we get with a query,
     * parameters of which we provide
     */
    public List<Tour> findAllToursPageable(int page,
                                           int size,
                                           String columnToSort,
                                           String directionToSort) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            TourDao tourDao = daoFactory.createTourDao(connection);
            log.info("trying get tours");
            return tourDao.findAllPageable(page, size, columnToSort, directionToSort);
        } catch (DaoException e) {
            return Collections.emptyList();
        }
    }
    public Tour findTourById(long id) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            TourDao tourDao = daoFactory.createTourDao(connection);
            return tourDao.findById(id).orElseThrow(IllegalAccessError::new);

        } catch (DaoException e) {

        }
        return null;
    }

    public boolean addTour(TourDTO tourDTO) {
        Tour tour = Tour.builder()
                .name(tourDTO.getName())
                .tourType(tourDTO.getTourType())
                .hotelType(tourDTO.getHotelType())
                .groupSize(tourDTO.getGroupSize())
                .price(tourDTO.getPrice())
                .isHot(false)
                .build();
        try (DaoConnection connection = daoFactory.getConnection()) {
            TourDao tourDao = daoFactory.createTourDao(connection);
            tourDao.create(tour);
            log.info("tour create");
            return true;
        } catch (DaoException e) {
            log.info("tour cannot create");
            return false;
        }
    }

    public boolean updateTour(long id, TourDTO tourDTO) {
        Tour tour = Tour.builder()
                .id(id)
                .name(tourDTO.getName())
                .tourType(tourDTO.getTourType())
                .hotelType(tourDTO.getHotelType())
                .groupSize(tourDTO.getGroupSize())
                .price(tourDTO.getPrice())
                .build();
        try (DaoConnection connection = daoFactory.getConnection()) {
            TourDao tourDao = daoFactory.createTourDao(connection);
            tourDao.update(tour);
            return true;
        } catch (DaoException e) {
            return false;
        }
    }

    public void deleteTour(long id) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            TourDao tourDao = daoFactory.createTourDao(connection);
            tourDao.delete(id);
        } catch (DaoException e) {

        }
    }

    public boolean toggleHot(long id) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            TourDao tourDao = daoFactory.createTourDao(connection);
            Tour tour = findTourById(id);
            tour.toggleHot();
            tourDao.toggleHot(tour);
            return true;
        }
    }

    public long tourRecords() {
        try (DaoConnection connection = daoFactory.getConnection()) {
            TourDao tourDao = daoFactory.createTourDao(connection);
            return tourDao.tourRecords();
        } catch (DaoException e) {

        }
        return 0;
    }

}
