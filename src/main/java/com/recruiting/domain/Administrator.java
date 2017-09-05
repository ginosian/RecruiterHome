package com.recruiting.domain;

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

    // endregion

    // region Constructors

    public Administrator() {
    }

    public Administrator(String username,
            String password,
            Boolean enabled,
            List<Authority> grantedAuthorities, Address address, String name, String ssn) {
        super(username, password, enabled, grantedAuthorities, address, ssn);
        super.setName(name);
    }

    // endregion

    // region Getters and Setters
    // endregion

    // region Overrides
    @Override
    public String toString() {
        return "Candidate [" +
                "id: " + id + "\t" +
                "name: " + super.getName() + "\t" +
                "]";
    }

    // endregion
}
