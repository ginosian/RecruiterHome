package com.recruiting.domain;

import com.recruiting.utils.StringUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by Martha on 4/5/2017.
 */
@Entity
@Table(name = "company_staff")
//public class CompanyStaff extends User implements Serializable {
public class CompanyStaff extends AbstractEntity implements Serializable {

    // region Instance Fields
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

//    @OneToMany(fetch = FetchType.LAZY)
//    private Set<Message> receivedMessages;
    // endregion

    // region Constructors
    public CompanyStaff() {
    }

    public CompanyStaff(String name, String phone, String email) {
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    // endregion

    // region Transient Methods
//    public void setMessage(Message message) {
//        if(receivedMessages == null) receivedMessages = new HashSet<>();
//        receivedMessages.add(message);
//    }

    public void correctStrings() {
        this.name = StringUtils.correctWhiteSpaces(name);
        this.phone = StringUtils.correctWhiteSpaces(phone);
        this.email = StringUtils.correctWhiteSpaces(email);
    }

    // endregion

    // region Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


//    public Set<Message> getReceivedMessages() {
//        return receivedMessages;
//    }
//
//    public void setReceivedMessages(Set<Message> receivedMessages) {
//        this.receivedMessages = receivedMessages;
//    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // endregion

    // region Overrides
    @Override
    public String toString() {
        return "CompanyStaff [" +
                "id: " + id + "\t" +
                "name: " + name + "\t" +
                "phone: " + phone + "\t" +
                "email: " + email + "\t" +
                "]";
    }
    // endregion
}
