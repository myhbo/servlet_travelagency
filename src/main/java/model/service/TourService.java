package model.service;

import controller.dto.TourDTO;
import exception.DaoException;
import model.dao.DaoConnection;
import model.dao.DaoFactory;
import model.dao.TourDao;
import model.entity.Tour;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class TourService {
    private static final Logger log = LogManager.getLogger();

    private final DaoFactory daoFactory = DaoFactory.getInstance();

    public List<Tour> findAllToursPageable(int page,
                                           int size,
                                           String columnToSort,
                                           String directionToSort) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            TourDao tourDao = daoFactory.createTourDao(connection);
            log.info("trying get tours in service");
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

    public void addTour(TourDTO tourDTO) {
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
            log.info("tour create service");
        } catch (DaoException e) {
            log.info("tour cannot create service");
        }
    }

    public void updateTour(long id, TourDTO tourDTO) {
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
        } catch (DaoException e) {

        }
    }

    public void deleteTour(long id) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            TourDao tourDao = daoFactory.createTourDao(connection);
            tourDao.delete(id);
        } catch (DaoException e) {

        }
    }

    public void toggleHot(long id) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            TourDao tourDao = daoFactory.createTourDao(connection);
            Tour tour = findTourById(id);
            tour.toggleHot();
            tourDao.toggleHot(tour);
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
