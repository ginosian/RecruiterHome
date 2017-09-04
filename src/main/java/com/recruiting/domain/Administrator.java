package com.recruiting.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;

/**
 * Created by Martha on 7/5/2017.
 */
@Entity
@Table(name = "administrator")
public class Administrator extends User implements Serializable {

    // region Instance Fields
    @Column(name = "full_name")
    private String name;
    // endregion

    // region Constructors

    public Administrator() {
    }

    public Administrator(String username,
            String password,
            Boolean enabled,
            List<Authority> grantedAuthorities, Address address, String name) {
        super(username, password, enabled, grantedAuthorities, address);
        this.name = name;
    }

    // endregion

    // region Getters and Setters

    public String getFullName() {
        return name;
    }

    public void setFullName(String name) {
        this.name = name;
    }

    // endregion

    // region Overrides
    @Override
    public String toString() {
        return "Candidate [" +
                "id: " + id + "\t" +
                "name: " + name + "\t" +
                "]";
    }

    // endregion
}
