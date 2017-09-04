package com.recruiting.domain;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by Martha on 5/30/2017.
 */
@Entity
@Table(name = "area")
public class Area extends AbstractTitledEntity implements Serializable {


    // region Constructors
    public Area() {
    }

    public Area(String title) {
        super(title);
    }

    // endregion

    // region Overrides
    @Override
    public String toString() {
        return "Area [" +
                "id: " + id + "\t" +
                "title: " + getTitle() +
                "]";
    }
    // endregion
}
