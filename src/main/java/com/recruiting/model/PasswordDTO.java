package com.recruiting.model;

import java.io.Serializable;

/**
 * Created by Martha on 6/28/2017.
 */
public class PasswordDTO implements Serializable {

    private String newPassword;
    private String newPasswordRepeat;

    public PasswordDTO() {
    }

    public PasswordDTO(String newPassword, String newPasswordRepeat) {
        this.newPassword = newPassword;
        this.newPasswordRepeat = newPasswordRepeat;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getNewPasswordRepeat() {
        return newPasswordRepeat;
    }

    public void setNewPasswordRepeat(String newPasswordRepeat) {
        this.newPasswordRepeat = newPasswordRepeat;
    }
}
