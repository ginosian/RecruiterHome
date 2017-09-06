package com.recruiting.domain;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by Martha on 4/5/2017.
 */
@Entity
@Table(name = "certifications")
public class Certifications extends AbstractTitledEntity implements Serializable {

    // region Constructors
    public Certifications() {
    }

    public Certifications(String ssn) {
        super(ssn);
    }

    public Certifications(String ssn, String title) {
        super(ssn, title);
    }
    // endregion


    // region Overrides
    @Override
    public String toString() {
        return "Certifications [" +
                "id: " + id + "\t" +
                "title: " + getTitle() +
                "]";
    }
    // endregion
}
