package com.recruiting.domain;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by Martha on 4/5/2017.
 */
@Entity
@Table(name = "skill")
public class Skill extends AbstractTitledEntity implements Serializable {

    // region Constructors
    public Skill() {
    }

    public Skill(String title) {
        super(title);
    }
    // endregion

    // region Overrides
    @Override
    public String toString() {
        return "Skill [" +
                "id: " + id + "\t" +
                "title: " + getTitle() +
                "]";
    }
    // endregion
}
