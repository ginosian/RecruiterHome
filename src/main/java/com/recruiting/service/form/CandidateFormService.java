package com.recruiting.service.form;

import com.recruiting.domain.Certifications;
import com.recruiting.domain.Skill;
import com.recruiting.model.flowModel.CandidateFormModel;
import com.recruiting.model.CertificationsModel;
import com.recruiting.model.modelUtils.StringItemModel;
import org.springframework.webflow.context.ExternalContext;
import org.springframework.webflow.execution.RequestContext;

import java.util.List;

/**
 * Created by Martha on 5/15/2017.
 */
public interface CandidateFormService extends AbstractFormService {

    List<StringItemModel> getTimePeriods();

    List<Skill> getSkillsList();

    CertificationsModel[] getCertificationsArray(List<Certifications> certifications);

    CandidateFormModel createCandidate();

    CandidateFormModel prepareCandidate(ExternalContext externalContext);

    CandidateFormModel addSkill(CandidateFormModel candidateFormModel);

    CandidateFormModel deleteSkill(CandidateFormModel candidateFormModel);

    CandidateFormModel uploadResume(CandidateFormModel candidateFormModel, RequestContext requestContext);

    CandidateFormModel downloadResume(CandidateFormModel candidateFormModel, RequestContext requestContext);

    CandidateFormModel saveCandidate(CandidateFormModel candidateFormModel);

    CandidateFormModel updateCandidate(CandidateFormModel candidateFormModel);

    CandidateFormModel updateArea(CandidateFormModel candidateFormModel);
}
