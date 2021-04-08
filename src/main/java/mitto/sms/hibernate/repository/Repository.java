package mitto.sms.hibernate.repository;

import java.util.List;


/**
 * Objects implements Repository interface allow us to do common operations – CRUD, and sorting
 * @param <T> generic type
 */
public interface Repository<T> {

    /**
     * create entity operation
     * @param data data to be persisted
     * @return true if data persist successfully
     */
    boolean create(T data);

    /**
     * find entity by id
     * @param id id of entity we are searching
     * @return fount entity, null otherwise
     */
    T find(Integer id);

    /**
     * select all entities, no criteria
     * @return all entities
     */
    List<T> findAll();
}
