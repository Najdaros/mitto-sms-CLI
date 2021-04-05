package mitto.sms.hibernate.repository;

import java.util.List;

public interface Repository<T> {
    boolean create(T data);
    T find(Integer id);
    List<T> findAll();
}
