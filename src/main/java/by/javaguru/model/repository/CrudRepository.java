package by.javaguru.model.repository;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<K, E> {
    Optional<E> findById(K id);

    List<E> findAll();

    boolean delete(K id);

    E save(E entity);

    void update(E entity);
}
