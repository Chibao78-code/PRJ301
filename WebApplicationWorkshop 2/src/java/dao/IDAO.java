package dao;

import java.util.List;

public interface IDAO<T, K> {
    boolean create(T entity);
    List<T> readAll();
    T readById(K id);
    boolean update(T entity);
    boolean delete(K id);
    List<T> search(String searchTerm);
}