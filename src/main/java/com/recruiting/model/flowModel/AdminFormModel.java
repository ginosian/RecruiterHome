package com.recruiting.model.flowModel;

import com.recruiting.domain.Administrator;

import java.io.Serializable;

/**
 * Created by Martha on 7/6/2017.
 */
public class AdminFormModel extends AbstractFlowModel implements Serializable {

    private Administrator admin;
    private String newName;

    public AdminFormModel() {
    }

    public AdminFormModel(Administrator admin, String newName) {
        this.admin = admin;
        this.newName = newName;
    }

    public Administrator getAdmin() {
        return admin;
    }

    public void setAdmin(Administrator admin) {
        this.admin = admin;
    }

    public String getNewName() {
        return newName;
    }

    public void setNewName(String newName) {
        this.newName = newName;
    }
}
