package hr.algebra.kompassgo._interface;

import java.util.List;
import java.util.Optional;

public interface ICrud <T,ID> {
    List<T> findAll();
    Optional<T> findById(ID id);
    void save(T entity);
    void update(T entity);
    void deleteById(ID id);
}
