package com.recruiting.service.form;

import com.recruiting.model.flowModel.AdminFormModel;
import org.springframework.webflow.context.ExternalContext;

/**
 * Created by Martha on 7/6/2017.
 */
public interface AdminFormService {

    AdminFormModel createAdmin();

    AdminFormModel prepareAdmin(ExternalContext externalContext);

    void saveAdmin(AdminFormModel admin);

    void updateAdmin(AdminFormModel admin);
}
