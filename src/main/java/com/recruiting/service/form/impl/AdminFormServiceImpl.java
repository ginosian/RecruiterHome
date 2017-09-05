package com.recruiting.service.form.impl;

import com.recruiting.domain.Administrator;
import com.recruiting.domain.VerificationToken;
import com.recruiting.model.flowModel.AdminFormModel;
import com.recruiting.service.EmptyEntityCreationService;
import com.recruiting.service.email.EmailService;
import com.recruiting.service.entity.ExtendedUserDetailService;
import com.recruiting.service.form.AdminFormService;
import com.recruiting.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.webflow.context.ExternalContext;

/**
 * Created by Martha on 7/6/2017.
 */
public class AdminFormServiceImpl extends AbstractFormServiceImpl implements AdminFormService {

    @Autowired
    EmptyEntityCreationService emptyEntityCreationService;

    @Autowired
    ExtendedUserDetailService extendedUserDetailService;

    @Qualifier("javaEmailService")
    @Autowired
    EmailService emailService;

    @Override
    public AdminFormModel createAdmin() {
        AdminFormModel adminFormModel = new AdminFormModel();
        adminFormModel.setAdmin(emptyEntityCreationService.emptyAdmin());
        adminFormModel.setSignedInUser(false);
        return adminFormModel;
    }

    @Override
    public AdminFormModel prepareAdmin(ExternalContext externalContext) {

        String adminUsername = (String) externalContext.getRequestParameterMap().asAttributeMap().get("adminUsername");
        Administrator administrator = (Administrator) extendedUserDetailService.findUserByUsername(adminUsername);
        AdminFormModel adminFormModel = new AdminFormModel();
        adminFormModel.setAdmin(administrator);
        adminFormModel.setInitialUsername(adminUsername);
        adminFormModel.setSignedInUser(true);
        return adminFormModel;
    }

    @Override
    public void saveAdmin(AdminFormModel admin) {
        Administrator finalAdmin = admin.getAdmin();

        finalAdmin.correctStrings();
        finalAdmin.setApproved(true);
        finalAdmin.setEnabled(false);
        finalAdmin = (Administrator) extendedUserDetailService.save(finalAdmin);

        VerificationToken verificationToken = applyForVerification(finalAdmin, finalAdmin.getName());
        emailService.sendCompanyAccountCreation(finalAdmin.getUsername(), finalAdmin.getUsername(), verificationToken.getToken());
    }

    @Override
    public void updateAdmin(AdminFormModel admin) {
        Administrator finalAdmin = admin.getAdmin();
        if (!StringUtils.isNullOrEmpty(admin.getNewName())) finalAdmin.setName(admin.getNewName());
        if (!StringUtils.isNullOrEmpty(admin.getNewPassword())) finalAdmin.setPassword(admin.getNewPassword());
        finalAdmin.correctStrings();
        extendedUserDetailService.save(finalAdmin);
    }

}
