package com.recruiting.model.modelUtils;

import java.io.Serializable;

/**
 * Created by Martha on 6/11/2017.
 */
public abstract class Model<T> implements Serializable {

    private T object;

    public Model() {
    }

    public Model(T object) {
        this.object = object;
    }

    public T getObject() {
        return object;
    }

    public void setObject(T object) {
        this.object = object;
    }
}
