package com.recruiting.domain;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by Martha on 5/18/2017.
 */
@Entity
@Table(name = "industry")
public class Industry extends AbstractTitledEntity implements Serializable {


    // region Constructors
    public Industry() {
    }

    public Industry(String ssn) {
        super(ssn);
    }

    public Industry(String ssn, String title) {
        super(ssn, title);
    }
    // endregion

    //region Overrides
    @Override
    public String toString() {
        return "Industry [" +
                "id: " + id + "\t" +
                "title: " + getTitle() +
                "]";
    }
    // endregion
}
