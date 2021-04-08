package mitto.sms.hibernate.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.Column;

import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * Object/relational CountryFee mapping
 */
@javax.persistence.Entity
@Data
@ToString
@Table(name = "country_fee")
public class CountryFee implements Entity {

    @Id
    @Column(name = "country_code_id")
    private Integer countryCode;

    @Column(name = "country_name")
    private String countryName;

    @Column(name = "price", scale = 3)
    private BigDecimal price;

    public CountryFee() {
    }

    public CountryFee(Integer countryCode, String countryName, BigDecimal price) {
        this.countryCode = countryCode;
        this.countryName = countryName;
        this.price = price;
    }

}
