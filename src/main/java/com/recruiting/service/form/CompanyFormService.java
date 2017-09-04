package com.recruiting.service.form;

import com.recruiting.domain.Industry;
import com.recruiting.model.flowModel.CompanyFormModel;
import org.springframework.webflow.context.ExternalContext;

import java.util.List;

/**
 * Created by Martha on 5/6/2017.
 */
public interface CompanyFormService extends AbstractFormService {

    List<Industry> getIndustries();

    CompanyFormModel createCompany();

    CompanyFormModel prepareCompany(ExternalContext externalContext);

    CompanyFormModel addAddress(CompanyFormModel company);

    CompanyFormModel deleteAddress(CompanyFormModel company);

    void saveCompany(CompanyFormModel company);

    void updateCompany(CompanyFormModel company);

    boolean companyExists(String name);

    CompanyFormModel checkAddressList(CompanyFormModel company);
}
