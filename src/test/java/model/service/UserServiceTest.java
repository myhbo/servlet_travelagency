package model.service;

import controller.dto.NewUserDTO;
import controller.dto.UserDTO;
import model.dao.UserDao;
import model.entity.User;
import model.entity.enums.Roles;
import model.service.UserService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
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
                .email("polo@tests.com")
                .password("admin")
                .fullName("Ihor Vaschenko")
                .roles(Collections.singleton(Roles.USER))
                .enabled(false)
                .build();
        return user;
    }

    @Test
    public void shouldAddUser() {
        when(userDao.create(any(User.class))).thenReturn(true);
        NewUserDTO user = NewUserDTO.builder()
                .email("polo@tests.com")
                .password("admin")
                .build();
        assertTrue(userService.addNewUser(user));
    }

    @Test
    public void shouldUpdate() {
        UserDTO userDTO = UserDTO.builder()
                .id(1L)
                .email("polo@tests.com")
                .password("admin")
                .fullName("Ihor Vaschenko")
                .roles(Collections.singleton(Roles.MANAGER))
                .build();
        assertTrue(userService.updateUser(userDTO));

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
