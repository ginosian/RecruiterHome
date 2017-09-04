package com.recruiting.model.modelUtils;

import com.recruiting.domain.AbstractTitledEntity;

import java.io.Serializable;

/**
 * Created by Martha on 6/11/2017.
 */
public class CheckboxListModel<T extends AbstractTitledEntity> extends Model implements Serializable {

    private T model;
    private boolean active;

    public CheckboxListModel() {
    }

    public CheckboxListModel(T model, boolean active) {
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
