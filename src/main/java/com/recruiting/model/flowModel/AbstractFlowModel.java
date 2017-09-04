package com.recruiting.model.flowModel;

import com.recruiting.model.modelUtils.Model;

import java.io.Serializable;

/**
 * Created by Martha on 5/18/2017.
 */
public abstract class AbstractFlowModel extends Model implements Serializable {

    // region Instance fields
    private boolean signedInUser;
    private String verifyPassword;
    private boolean agreed;
    private String error;
    private int index;
    private String newPassword;
    private int count;
    private String initialUsername;
    // endRegion

    // region Getters and Setters

    public boolean isSignedInUser() {
        return signedInUser;
    }

    public void setSignedInUser(boolean signedInUser) {
        this.signedInUser = signedInUser;
    }

    public String getVerifyPassword() {
        return verifyPassword;
    }

    public void setVerifyPassword(String verifyPassword) {
        this.verifyPassword = verifyPassword;
    }

    public boolean isAgreed() {
        return agreed;
    }

    public void setAgreed(boolean agreed) {
        this.agreed = agreed;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getInitialUsername() {
        return initialUsername;
    }

    public void setInitialUsername(String initialUsername) {
        this.initialUsername = initialUsername;
    }

    // endregion

}
