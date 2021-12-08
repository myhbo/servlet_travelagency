package model.service;

import controller.dto.TourDTO;
import model.dao.TourDao;
import model.dao.UserDao;
import model.entity.Tour;
import model.entity.User;
import model.entity.enums.HotelType;
import model.entity.enums.Roles;
import model.entity.enums.TourType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collections;

import static org.junit.Assert.*;


@RunWith(MockitoJUnitRunner.class)
public class TourServiceTest {

    @Mock
    private TourDao tourDao;
    @InjectMocks
    private TourService tourService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    public static Tour createTour() {
        Tour tour = Tour.builder()
                .id(1L)
                .name("Марс")
                .tourType(TourType.EXCURSION)
                .hotelType(HotelType.RESORT)
                .groupSize(1)
                .price(10000D)
                .isHot(true)
                .build();
        return tour;
    }
    public static TourDTO createTourDTO() {
        TourDTO tourDTO = TourDTO.builder()
                .name("Горгани")
                .tourType(TourType.EXCURSION)
                .hotelType(HotelType.HOSTEL)
                .groupSize(2)
                .price(1500D)
                .build();
        return tourDTO;
    }



    @Test
    public void addTour() {
        TourDTO tourDTO = createTourDTO();
        assertTrue(tourService.addTour(tourDTO));
    }

    @Test
    public void updateTour() {
        TourDTO tourDTO = createTourDTO();
        assertTrue(tourService.updateTour(1, tourDTO));
    }

    @Test
    public void toggleHot() {
        Tour tour = createTour();
        assertTrue(tourService.toggleHot(tour.getId()));
    }
}