
package dao;

import java.util.List;

public interface IDAO<T, K> {
    public boolean create(T entity);
    public List<T> readAll();
    public T readById(K id);
    public boolean update(T entity);
    public boolean delete(K id);
    public List<T> search(String searchTerm);
}