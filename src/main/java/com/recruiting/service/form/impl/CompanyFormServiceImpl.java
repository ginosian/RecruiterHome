package com.recruiting.service.form.impl;

import com.recruiting.domain.*;
import com.recruiting.model.flowModel.CompanyFormModel;
import com.recruiting.service.EmptyEntityCreationService;
import com.recruiting.service.email.EmailService;
import com.recruiting.service.entity.CompanyService;
import com.recruiting.service.entity.ExtendedUserDetailService;
import com.recruiting.service.entity.IndustryService;
import com.recruiting.service.form.CompanyFormService;
import com.recruiting.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.webflow.context.ExternalContext;

import java.util.List;

/**
 * Created by Martha on 5/6/2017.
 */
@Service("companyFormService")
public class CompanyFormServiceImpl extends AbstractFormServiceImpl implements
        CompanyFormService {

    // TODO change everything to service, no repository should be used here
    // TODO cleanup the code, make it more readable

    @Autowired
    EmptyEntityCreationService emptyEntityCreationService;

    @Autowired
    CompanyService companyService;

    @Autowired
    ExtendedUserDetailService extendedUserDetailService;

    @Autowired
    IndustryService industryService;

    @Qualifier("javaEmailService")
    @Autowired
    EmailService emailService;

    public List<Industry> getIndustries() {
        return (List<Industry>) industryService.findAll();
    }

    @Override
    public CompanyFormModel createCompany() {
        //Creates company registration model
        CompanyFormModel companyFormModel = new CompanyFormModel();

        companyFormModel.setCompany(emptyEntityCreationService.emptyCompany(COMPANY_LOCKED));
        companyFormModel.setAdditionalAddressesDecorator(new Address[5]);
        companyFormModel.setAdditionalAddressPlaceholder(emptyEntityCreationService.emptyAddress());
        companyFormModel.setAddressForError(emptyEntityCreationService.emptyAddress());

        companyFormModel.setSignedInUser(false);

        return companyFormModel;
    }

    @Override
    public CompanyFormModel prepareCompany(ExternalContext externalContext) {

        String companyUsername = (String) externalContext.getRequestParameterMap().asAttributeMap().get("companyUsername");
        Company company = (Company) extendedUserDetailService.findUserByUsername(companyUsername);
        CompanyFormModel companyFormModel = new CompanyFormModel();
        companyFormModel.setCompany(company);
        companyFormModel.setInitialUsername(companyUsername);

        companyFormModel.setAdditionalAddressesDecorator(addressesListToArray(company.getAddresses()));
        companyFormModel.setAdditionalAddressPlaceholder(emptyEntityCreationService.emptyAddress());
        companyFormModel.setAddressForError(emptyEntityCreationService.emptyAddress());
        companyFormModel.setSignedInUser(true);
        companyFormModel.setNewName(company.getName());

        return companyFormModel;
    }

    @Override
    public CompanyFormModel addAddress(CompanyFormModel company) {

        Address newAddress = company.getAdditionalAddressPlaceholder();
        if (CompanyFormModel.isEmptyAddress(newAddress)) return company;

        //Checks if additionalAddressDecorator exists in model is not creates one, if exists keep a reference to it.
        Address[] additionalAddressesDecorator;
        if (company.getAdditionalAddressesDecorator() == null) {
            additionalAddressesDecorator = new Address[5];
            company.setAdditionalAddressesDecorator(additionalAddressesDecorator);
        } else additionalAddressesDecorator = company.getAdditionalAddressesDecorator();

        // Loops through additionalAddressDecorator to catch the the first null member and assign address from placeholder to it.
        for (int i = 0; i < additionalAddressesDecorator.length; i++) {
            Address address = additionalAddressesDecorator[i];
            if (address == null) {
                additionalAddressesDecorator[i] = newAddress;
                company.setCount(company.getCount() + 1);
                // Creates new address and assigns it to placeholder for future new addresses
                company.setAdditionalAddressPlaceholder(emptyEntityCreationService.emptyAddress());
                return company;
            }
        }
        if (company.getCount() >= 5) {
            company.setAdditionalAddressPlaceholder(emptyEntityCreationService.emptyAddress());
        }
        return company;
    }

    @Override // TODO Optimize this code
    public CompanyFormModel deleteAddress(CompanyFormModel company) {
        int addressIndex = company.getIndex();
        Address[] newAdditionalAddressesList = new Address[5];
        Address[] oldAdditionalAddressesList = company.getAdditionalAddressesDecorator();
        for (int i = 0; i < oldAdditionalAddressesList.length; i++) {
            if (i == addressIndex || oldAdditionalAddressesList[i] == null) {
                company.setCount(company.getCount() - 1);
                continue;
            }
            newAdditionalAddressesList[i] = oldAdditionalAddressesList[i];
        }
        company.setAdditionalAddressesDecorator(newAdditionalAddressesList);
        return company;
    }

    @Override
    public CompanyFormModel checkAddressList(CompanyFormModel company) {
        Address[] addresses = company.getAdditionalAddressesDecorator();
        Address[] newAddresses = new Address[5];
        int index = 0;

        for (Address address : addresses) {
            if (!CompanyFormModel.isEmptyAddress(address)) {
                newAddresses[index] = address;
                ++index;
            }
        }
        company.setAdditionalAddressesDecorator(newAddresses);
        return company;
    }

    @Override
    public void saveCompany(CompanyFormModel company) {
        Company finalCompany = company.getCompany();

        Address[] addressesDecorator = company.getAdditionalAddressesDecorator();
        for (Address address : addressesDecorator) {
            if (address != null) {
                address.correctStrings();
                finalCompany.setAdditionalAddress(address);
            }
        }

        finalCompany.correctStrings();
        finalCompany.setApproved(false);
        finalCompany.setEnabled(false);
        finalCompany = (Company) extendedUserDetailService.save(finalCompany);

        VerificationToken verificationToken = applyForVerification(finalCompany, finalCompany.getName());
        emailService.sendCompanyAccountCreation(finalCompany.getUsername(), finalCompany.getName(), verificationToken.getToken());
    }

    @Override
    public void updateCompany(CompanyFormModel company) { // TODO Optimize this code
        Company finalCompany = company.getCompany();
        if (!StringUtils.isNullOrEmpty(company.getNewName())) finalCompany.setName(company.getNewName());
        if (!StringUtils.isNullOrEmpty(company.getNewPassword())) finalCompany.setPassword(company.getNewPassword());

        Address[] addressesDecorator = company.getAdditionalAddressesDecorator();
        finalCompany.setAddresses(null);
        for (Address address : addressesDecorator) {
            if (address != null) {
                address.correctStrings();
                finalCompany.setAdditionalAddress(address);
            }
        }
        finalCompany.correctStrings();
        finalCompany.setApproved(false);
        Authority authority = extendedUserDetailService.findByRole(COMPANY_LOCKED);
        finalCompany.setRole(authority);
        extendedUserDetailService.save(finalCompany);
    }

    @Override
    public boolean companyExists(String name) {
        Company company = companyService.findByName(name);
        return company != null && company.getId() != null;
    }

}
