package model.service;

import controller.dto.NewUserDTO;
import controller.dto.UserDTO;
import exception.EmailNotUniqueException;
import model.dao.UserDao;
import model.entity.User;
import model.entity.enums.Roles;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collections;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    private UserDao userDao;

    @InjectMocks
    private UserService userService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    public static User createUser() {
        User user = User.builder()
                .id(1L)
                .email("adm@test.com")
                .password("$2a$10$.uMZfw3bYOeobAkninKiXuZJPTpB3lWzD0xFpRRmYDU7yBkADi5KC")
                .fullName("Bondarchuk Mykhailo")
                .role(Roles.ADMIN)
                .enabled(true)
                .build();
        return user;
    }

    @Test
    public void shouldAddUser() {
        NewUserDTO user = NewUserDTO.builder()
                .email("admin@test.com")
                .fullName("Bondarchuk Mykhailo")
                .password("$2a$10$.uMZfw3bYOeobAkninKiXuZJPTpB3lWzD0xFpRRmYDU7yBkADi5KC")
                .build();
        assertTrue(userService.addNewUser(user));
    }

    @Test
    public void shouldBanUser() {
        User user = createUser();
        assertTrue(userService.banUser(user.getId()));
    }

    @Test
    public void shouldUnbanUser() {
        User user = createUser();
        assertTrue(userService.unbanUser(user.getId()));
    }



}
