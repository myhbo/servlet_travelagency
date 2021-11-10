package model.dao.implementation.mapper;

import model.entity.Tour;
import model.entity.enums.HotelType;
import model.entity.enums.TourType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;


public class TourMapper implements ObjectMapper<Tour> {
    @Override
    public Tour getFromResultSet(ResultSet resultSet) throws SQLException {
        Tour tour = new Tour();
        tour.setId(resultSet.getLong("tours.id"));
        tour.setName(resultSet.getString("tours.name"));

        String tourType = resultSet.getString("tours.tour_type");
        tour.setTourType(tourType != null ? TourType.valueOf(tourType) : null);

        String hotelType = resultSet.getString("tours.hotel_type");
        tour.setHotelType(hotelType != null ? HotelType.valueOf(hotelType) : null);

        tour.setGroupSize(resultSet.getInt("tours.group_size"));
        tour.setPrice(resultSet.getDouble("tours.price"));
        tour.setHot(resultSet.getBoolean("tours.is_hot"));
        return tour;
    }

    @Override
    public Tour makeUnique(Map<Long, Tour> cache, Tour tour) {
        cache.putIfAbsent(tour.getId(), tour);
        return cache.get(tour.getId());
    }
}
