package model.service;

import controller.dto.NewUserDTO;
import controller.dto.UserDTO;
import exception.EmailNotUniqueException;
import model.dao.DaoConnection;
import model.dao.DaoFactory;
import model.dao.UserDao;
import model.entity.User;
import exception.DaoException;
import model.entity.enums.Roles;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collections;
import java.util.List;
import java.util.Optional;


public class UserService {
    private static final Logger log = LogManager.getLogger();

    private final DaoFactory daoFactory = DaoFactory.getInstance();

    public User findUserById(long id) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            UserDao userDao = daoFactory.createUserDao(connection);
            return userDao.findById(id).orElseThrow(IllegalArgumentException::new);
        } catch (DaoException e) {

        }
        return null;
    }

    public Optional<User> findUserByEmail(String email) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            UserDao userDao = daoFactory.createUserDao(connection);
            return userDao.findByEmail(email);
        } catch (DaoException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public boolean addNewUser(NewUserDTO newUserDTO) {
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
            return true;
        } catch (DaoException e) {
            throw new EmailNotUniqueException();
        }
    }

    public boolean updateUser(UserDTO userDTO) {
        User user = User.builder()
                .id(userDTO.getId())
                .email(userDTO.getEmail())
                .password(userDTO.getPassword())
                .fullName(userDTO.getFullName())
                .roles(userDTO.getRole())
                .build();

        try (DaoConnection connection = daoFactory.getConnection()){
            UserDao userDao = daoFactory.createUserDao(connection);
            if (userDTO.getPassword().isEmpty()) {
                String remainingPassword = (findUserById(userDTO.getId()).getPassword());
                user.setPassword(remainingPassword);
            }
            userDao.update(user);
            log.info("updated userservice");
            return true;
        } catch (DaoException e) {
            throw new EmailNotUniqueException();
        }
    }

    public boolean  banUser(long id) {
        try (DaoConnection connection = daoFactory.getConnection()){
            UserDao userDao = daoFactory.createUserDao(connection);
            userDao.updateEnabled(id, false);
            return true;
        } catch (DaoException e) {
            return false;
        }
    }

    public boolean unbanUser(long id) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            UserDao userDao = daoFactory.createUserDao(connection);
            userDao.updateEnabled(id, true);
            return true;
        } catch (DaoException e) {
            return false;
        }
    }

    public void deleteUser(long id) {
        try (DaoConnection connection = daoFactory.getConnection()){
            UserDao userDao = daoFactory.createUserDao(connection);
            userDao.delete(id);
        } catch (DaoException e) {

        }
    }

    /**
     *
     * @param page number of specific page in a query
     * @param size amount of items on the page
     * @return list of specific orders that we get with a query,
     * parameters of which we provide
     */
    public List<User> findAllUsersPageable(int page, int size) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            UserDao userDao = daoFactory.createUserDao(connection);
            return userDao.findAllPageable(page, size);

        } catch (DaoException e) {
            return Collections.emptyList();
        }
    }

    public long getUsersRecords() {
        try (DaoConnection connection = daoFactory.getConnection()) {
            UserDao userDao = daoFactory.createUserDao(connection);
            return userDao.usersRecords();
        } catch (DaoException e) {
        }
        return 0;
    }
}
