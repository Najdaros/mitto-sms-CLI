package mitto.sms.hibernate.dao;

import mitto.sms.hibernate.entity.SMS;

import java.util.List;

public interface SmsDao {
    void create(String sender, String recipient, String text);
    void create(SMS sms);
    SMS find(Long id);
    List<SMS> findALl();
    void update(SMS user);
    void delete(Long id);
}
