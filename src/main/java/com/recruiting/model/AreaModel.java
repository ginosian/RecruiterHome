package com.recruiting.model;

import com.recruiting.domain.Area;

import java.io.Serializable;

/**
 * Created by Martha on 5/30/2017.
 */
public class AreaModel implements Serializable {

    private Area area;
    private boolean active;

    public AreaModel(Area area, boolean active) {
        this.area = area;
        this.active = active;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
