package mitto.sms.hibernate.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Object/relational SMS mapping
 */
@javax.persistence.Entity
@Data
@ToString
@Table(name = "sms")
public class SMS implements Entity {
    @ToString.Exclude
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "country_code_id", referencedColumnName = "country_code_id")
    private CountryFee countryFee;

    @Column(name = "sender")
    private String sender;

    @ToString.Exclude
    @Column(name = "success")
    private Boolean success;

    @Column(name = "recipient")
    private String recipient;

    @Column(name = "text")
    private String text;

    public SMS() {
    }

    public SMS(String sender, String recipient, String text) {
        this.sender = sender;
        this.recipient = recipient;
        this.text = text;
    }
}
