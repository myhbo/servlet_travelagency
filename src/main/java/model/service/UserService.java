package model.service;

import controller.dto.NewUserDTO;
import model.dao.DaoConnection;
import model.dao.DaoFactory;
import model.dao.UserDao;
import model.entity.User;
import exception.DaoException;
import model.entity.enums.Roles;

import java.util.Collections;
import java.util.Optional;


public class UserService {
    private final DaoFactory daoFactory = DaoFactory.getInstance();

    public Optional<User> findUserById(long id) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            UserDao userDao = daoFactory.createUserDao(connection);
            return userDao.findById(id);
        } catch (DaoException e) {
            e.printStackTrace();
            return Optional.empty();
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public Optional<User> findUserByEmail(String email) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            UserDao userDao = daoFactory.createUserDao(connection);
            return userDao.findByEmail(email);
        } catch (DaoException e) {
            e.printStackTrace();
            return Optional.empty();
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public void makeNewUser(NewUserDTO newUserDTO) {
        User user = User.builder()
                .email(newUserDTO.getEmail())
                .password(newUserDTO.getPassword())
                .fullName(newUserDTO.getFullName())
                .enabled(true)
                .roles(Collections.singleton(Roles.USER))
                .build();
        try (DaoConnection connection = daoFactory.getConnection()) {
            UserDao userDao = daoFactory.createUserDao(connection);
            userDao.create(user);
        } catch (DaoException e) {

        } catch (Exception e) {

        }
    }
}
