package mitto.sms.hibernate.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Fee")
public class Fee {

    @Id
    @Column(name = "country_code")
    private Integer countryCode;

    @Column(name = "country_name")
    private String countryName;

    @Column(name = "price", scale = 3)
    private Float price;

    public Fee() {

    }

    public Fee(Integer countryCode, String countryName, Float price) {
        this.countryCode = countryCode;
        this.countryName = countryName;
        this.price = price;
    }


    public Integer getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(Integer countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Fee [countryCode=" + countryCode + ", countryName=" + countryName + ", price=" + price + "]";
    }
}
