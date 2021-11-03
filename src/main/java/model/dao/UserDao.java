package model.dao;

import model.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserDao extends GenericDao<User> {
    Optional<User> findByEmail(String email);
    List<User> findAllPageable(int page, int size);
    long usersRecords();
    void updateEnabled(long id, boolean enabled);
}
