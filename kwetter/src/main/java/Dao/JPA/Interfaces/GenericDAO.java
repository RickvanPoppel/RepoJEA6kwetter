package Dao.JPA.Interfaces;

import java.util.List;

public interface GenericDAO<T> {
    List<T> getAll();
    T create (T t);
    void delete (Object id);
    T find (Object id);
    T update(T t);
}
