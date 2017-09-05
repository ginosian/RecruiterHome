package com.recruiting.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Martha on 5/18/2017.
 */
@Entity
@Table(name = "state")
public class State extends AbstractTitledEntity implements Serializable, Titled {
    // region Instance Fields
    @OneToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.ALL},
            orphanRemoval = true)
    private List<Area> areas;
    // endregion

    // region Constructors

    public State(String ssn) {
        super(ssn);
    }

    public State(String ssn, String title) {
        super(ssn, title);
    }
    // endregion

    // region Transient Methods
    public void setArea(Area area) {
        if (areas == null) areas = new ArrayList<>();
        areas.add(area);
    }
    // endregion

    // region Getters and Setters
    public List<Area> getAreas() {
        return areas;
    }

    public void setAreas(List<Area> areas) {
        this.areas = areas;
    }

    // endregion

    // region Overrides
    @Override
    public String toString() {
        return "State [" +
                "id: " + id + "\t" +
                "title: " + getTitle() +
                "]";
    }
    // endregion
}
