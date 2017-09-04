package com.recruiting.model;

import com.recruiting.domain.Certifications;
import com.recruiting.model.modelUtils.Model;

import java.io.Serializable;

/**
 * Created by Martha on 9/2/2017.
 */
public class CertificationsModel extends Model implements Serializable {
    private Certifications certificationsModel; // TODO Change name to certifications
    private boolean active = false;

    public CertificationsModel() {
    }

    public CertificationsModel(Certifications certificationsModel, boolean active) {
        this.certificationsModel = certificationsModel;
        this.active = active;
    }

    public Certifications getCertificationsModel() {
        return certificationsModel;
    }

    public void setCertificationsModel(Certifications certificationsModel) {
        this.certificationsModel = certificationsModel;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
