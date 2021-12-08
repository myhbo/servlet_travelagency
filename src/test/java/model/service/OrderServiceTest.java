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
                .price(100000D)
                .isHot(false)
                .build();
        return tour;
    }
    public static User createUser() {
        User user = User.builder()
                .id(1L)
                .email("admin@test.com")
                .password("$2a$10$.uMZfw3bYOeobAkninKiXuZJPTpB3lWzD0xFpRRmYDU7yBkADi5KC")
                .fullName("Bondarchuk Mykhailo")
                .role(Roles.ADMIN)
                .enabled(true)
                .build();
        return user;
    }

    @Test
    public void setDiscount() {
        OrderDTO orderDTO = OrderDTO.builder()
                .discount(17D)
                .build();
        assertTrue(orderService.setDiscount(39, orderDTO));
    }

    @Test
    public void shouldCreateOrder() {
        User user = createUser();
        Tour tour = createTour();
        assertTrue(orderService.createOrder(user.getId(), tour.getId()));
    }
}