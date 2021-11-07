package model.dao.implementation;

import exception.DaoException;
import model.dao.TourDao;
import model.dao.implementation.mapper.TourMapper;
import model.entity.Tour;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.*;

public class JDBCTourDao implements TourDao {
    public static final Logger log = LogManager.getLogger();

    private final Connection connection;
    private final ResourceBundle resourceBundle = ResourceBundle.getBundle("db");

    public JDBCTourDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Optional<Tour> findById(long id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                resourceBundle.getString("tour.find.by.id"))) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            Map<Long, Tour> tourMap = getToursFromResultSet(resultSet);
            return tourMap.values().stream().findAny();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private Map<Long, Tour> getToursFromResultSet(ResultSet resultSet)
            throws SQLException {
        Map<Long, Tour> tourMap = new LinkedHashMap<>();
        TourMapper tourMapper = new TourMapper();
        while (resultSet.next()) {
            Tour tour = tourMapper.getFromResultSet(resultSet);
            tourMapper.makeUnique(tourMap, tour);
        }
        return tourMap;
    }

    @Override
    public void create(Tour tour) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                resourceBundle.getString("tour.create"), Statement.RETURN_GENERATED_KEYS)) {
            fillTourStatement(tour, preparedStatement);
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                tour.setId(resultSet.getLong(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DaoException(e);
        }
    }

    private void fillTourStatement(Tour tour, PreparedStatement preparedStatement)
            throws SQLException {
        preparedStatement.setString(1, tour.getName());
        preparedStatement.setString(2, tour.getTourType().name());
        preparedStatement.setString(3, tour.getHotelType().name());
        preparedStatement.setInt(4, tour.getGroupSize());
        preparedStatement.setDouble(5, tour.getPrice());
        preparedStatement.setBoolean(6, tour.isHot());

    }

    @Override
    public void update(Tour tour) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                resourceBundle.getString("tour.update"))) {
            preparedStatement.setString(1, tour.getName());
            preparedStatement.setString(2, tour.getTourType().name());
            preparedStatement.setString(3, tour.getHotelType().name());
            preparedStatement.setInt(4, tour.getGroupSize());
            preparedStatement.setDouble(5, tour.getPrice());
            preparedStatement.setLong(6, tour.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DaoException(e);
        }
    }

    @Override
    public void delete(long id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                resourceBundle.getString("tour.delete"))) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }

    }

    @Override
    public List<Tour> findAllPageable(int page,
                                      int size,
                                      String columnToSort,
                                      String directionToSort) {
        try (Statement statement = connection.createStatement()) {
            String query = resourceBundle.getString("tour.find.all.pageable")
                    + " order by " + columnToSort
                    + " " + directionToSort + " "
                    + " limit " + size
                    + " offset " + (long) size * page;
            log.info("trying get tours in dao " + query);
            ResultSet resultSet = statement.executeQuery(query);

            Map<Long, Tour> tourMap = getToursFromResultSet(resultSet);

            return new ArrayList<>(tourMap.values());

        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public long tourRecords() {
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(
                    resourceBundle.getString("tour.count.records"));
            if (resultSet.next()) {
                return resultSet.getLong(1);
            }
            return 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }

    }

    @Override
    public void toggleHot(Tour tour) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                resourceBundle.getString("tour.toggle.hot"))) {
            preparedStatement.setBoolean(1, tour.isHot());
            preparedStatement.setLong(2, tour.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
}
