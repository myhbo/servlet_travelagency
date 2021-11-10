package model.dao;

import java.util.Optional;

public interface GenericDao<T> {
    Optional<T> findById(long id);
    boolean create(T entity);
    void update(T entity);
    void delete(long id);


}
