package model.service;

import controller.dto.OrderDTO;
import model.dao.OrderDao;
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
public class OrderServiceTest {

    @Mock
    private OrderDao orderDao;
    @InjectMocks
    private OrderService orderService;

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
                .isHot(false)
                .build();
        return tour;
    }
    public static User createUser() {
        User user = User.builder()
                .id(1L)
                .email("polo@tests.com")
                .password("admin")
                .fullName("Ihor Vaschenko")
                .role(Roles.USER)
                .enabled(false)
                .build();
        return user;
    }

    @Test
    public void setDiscount() {
        OrderDTO orderDTO = OrderDTO.builder()
                .discount(50D)
                .build();
        assertTrue(orderService.setDiscount(1, orderDTO));
    }

    @Test
    public void shouldCreateOrder() {
        User user = createUser();
        Tour tour = createTour();
        assertTrue(orderService.createOrder(user.getId(), tour.getId()));
    }
}