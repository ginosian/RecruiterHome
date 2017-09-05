package com.recruiting.domain;

import com.recruiting.utils.StringUtils;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Martha on 4/5/2017.
 */
@Entity
@Table(name = "address")
public class Address extends AbstractEntity implements Serializable {

    // region Instance Fields
    @Column(name = "address",
            nullable = false)
    private String address;

    @Column(name = "city",
            nullable = false)
    private String city;

    @OneToOne(fetch = FetchType.EAGER)
    private State state;

    @Column(name = "zip_code",
            nullable = false)
    private Integer zipCode;
    // endregion

    // region Constructors
    public Address() {
    }

    public Address(String ssn) {
        super(ssn);
    }

    public Address(String address, String city, State state, Integer zipCode, String ssn) {
        super(ssn);
        this.address = address;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
    }
    // endregion

    // region Transient methods
    public void correctStrings() {
        this.address = StringUtils.correctWhiteSpaces(address);
        this.city = StringUtils.correctWhiteSpaces(city);
        this.state.correctStrings();
    }
    // endregion

    // region Getters and Setters
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }


    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Integer getZipCode() {
        return zipCode;
    }

    public void setZipCode(Integer zipCode) {
        this.zipCode = zipCode;
    }

    // endregion

    // region Overrides
    @Override
    public String toString() {
        return "Address [" +
                "id: " + id + "\t" +
                "address: " + address + "\t" +
                "city: " + city + "\t" +
                "zipCode: " + zipCode + "]";
    }
}
