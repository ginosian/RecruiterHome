package com.recruiting.domain;

import com.recruiting.utils.StringUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Martha on 4/5/2017.
 */
@Entity
@Table(name = "company")
public class Company extends User implements Serializable {

    // region Instance Fields
    @OneToOne(fetch = FetchType.EAGER)
    private Industry industry;

    @Column(name = "phone")
    private String phone;

    @OneToOne(cascade = {CascadeType.ALL}, orphanRemoval = true)
    private
    CompanyStaff mainContact;

    @OneToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.ALL},
            orphanRemoval = true)
    private List<Address> addresses;

//    @OneToMany(fetch = FetchType.LAZY,
//            cascade = {CascadeType.ALL},
//            orphanRemoval = true)
//    private Set<CompanyStaff> stuff;
    // endregion

    // region Constructors
    public Company() {
    }

    public Company(String username,
            String password,
            List<Authority> grantedAuthorities,
            Boolean isEnabled,
            String name,
            Industry industry,
            String phone,
            Address address,
            CompanyStaff mainContact,
            List<Address> addresses) {
        super(username, password, isEnabled, grantedAuthorities, address);
        super.setName(name);
        this.industry = industry;
        this.phone = phone;
        this.mainContact = mainContact;
        this.addresses = addresses;
    }

// endregion

    // region Transient Methods
    public void setAdditionalAddress(Address address) {
        if (addresses == null) addresses = new ArrayList<>();
        addresses.add(address);
    }

    public void correctStrings() {
        super.correctStrings();
        this.mainContact.correctStrings();
        super.setName(StringUtils.correctWhiteSpaces(super.getName()));
        this.phone = StringUtils.correctWhiteSpaces(phone);
        this.industry.correctStrings();
    }
    // endregion

    // region Getters and Setters

    public Industry getIndustry() {
        return industry;
    }

    public void setIndustry(Industry industry) {
        this.industry = industry;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    //    public Set<CompanyStaff> getStuff() {
//        return stuff;
//    }
//
//    public void setStuff( Set<CompanyStaff> stuff ) {
//        this.stuff = stuff;
//    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public CompanyStaff getMainContact() {
        return mainContact;
    }

    public void setMainContact(CompanyStaff mainContact) {
        this.mainContact = mainContact;
    }


    // endregion

    // region Overrides
    @Override
    public String toString() {
        return "Company [" +
                "id: " + id + "\t" +
                "name: " + super.getName() + "\t" +
                "phone: " + phone + "]";
    }
    // endregion
}
