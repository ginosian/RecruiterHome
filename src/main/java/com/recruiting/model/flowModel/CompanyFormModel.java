package com.recruiting.model.flowModel;

import com.recruiting.domain.Address;
import com.recruiting.domain.Company;
import com.recruiting.utils.StringUtils;

import java.io.Serializable;

/**
 * Created by Martha on 5/6/2017.
 */
public class CompanyFormModel extends AbstractFlowModel implements Serializable {

    // Instance fields
    private Company company;
    private Address additionalAddressPlaceholder;
    private Address[] additionalAddressesDecorator;
    private String newName;
    private Address addressForError;
    // endregion

    // region Getters and Setters

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Address getAdditionalAddressPlaceholder() {
        return additionalAddressPlaceholder;
    }

    public void setAdditionalAddressPlaceholder(Address additionalAddressPlaceholder) {
        this.additionalAddressPlaceholder = additionalAddressPlaceholder;
    }

    public Address[] getAdditionalAddressesDecorator() {
        return additionalAddressesDecorator;
    }

    public void setAdditionalAddressesDecorator(Address[] additionalAddressesDecorator) {
        this.additionalAddressesDecorator = additionalAddressesDecorator;
    }

    public String getNewName() {
        return newName;
    }

    public void setNewName(String newName) {
        this.newName = newName;
    }

    public Address getAddressForError() {
        return addressForError;
    }

    public void setAddressForError(Address addressForError) {
        this.addressForError = addressForError;
    }

    // endregion

    public static boolean isEmptyAddress(Address address) {
        return !(address != null
                && ((address.getState() != null && address.getState().getId() != null)
                || !StringUtils.isNullOrEmpty(address.getCity())
                || !StringUtils.isNullOrEmpty(address.getAddress())
                || address.getZipCode() != null));
    }
}
