package mitto.sms.hibernate.dao;

import mitto.sms.hibernate.entity.Fee;

import java.util.List;

public interface FeeDao {
    void create(Integer countryCode, String countryName, Float price);
    void create(Fee fee);
    Fee find(Integer id);
    List<Fee> findALl();
    void update(Fee user);
    void delete(Integer id);
}
