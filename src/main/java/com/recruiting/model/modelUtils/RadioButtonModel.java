package com.recruiting.model.modelUtils;

import java.io.Serializable;

/**
 * Created by Martha on 6/25/2017.
 */
public class RadioButtonModel<T> extends Model implements Serializable {

    private T model;
    private boolean active;

    public RadioButtonModel() {
    }

    public RadioButtonModel(T model, boolean active) {
        this.model = model;
        this.active = active;
    }

    public T getModel() {
        return model;
    }

    public void setModel(T model) {
        this.model = model;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
