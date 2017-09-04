package com.recruiting.service.form.impl;

import com.recruiting.domain.*;
import com.recruiting.model.AreaModel;
import com.recruiting.model.CertificationsModel;
import com.recruiting.model.flowModel.CandidateFormModel;
import com.recruiting.model.modelUtils.StringItemModel;
import com.recruiting.service.EmptyEntityCreationService;
import com.recruiting.service.email.EmailService;
import com.recruiting.service.entity.ExtendedUserDetailService;
import com.recruiting.service.entity.SkillService;
import com.recruiting.service.entity.CertificationsService;
import com.recruiting.service.form.CandidateFormService;
import com.recruiting.utils.ConstantLabels;
import com.recruiting.utils.StringUtils;
import org.apache.catalina.core.ApplicationPart;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;
import org.springframework.webflow.context.ExternalContext;
import org.springframework.webflow.context.servlet.ServletExternalContext;
import org.springframework.webflow.execution.RequestContext;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Martha on 5/15/2017.
 */
@Service("candidateFormService")
public class CandidateFormServiceImpl extends AbstractFormServiceImpl implements
        CandidateFormService {

    // TODO change everything to service, no repository should be used here
    // TODO cleanup the code, make it more readable

    @Autowired
    ExtendedUserDetailService userDetailService;

    @Qualifier("javaEmailService")
    @Autowired
    EmailService emailService;

    @Autowired
    EmptyEntityCreationService emptyEntityCreationService;

    @Autowired
    SkillService skillService;

    @Autowired
    CertificationsService certificationsService;

    @Override
    public List<StringItemModel> getTimePeriods() {
        return ConstantLabels.TIME_PERIODS_LIST;
    }

    @Override
    public List<Skill> getSkillsList() {
        return (List<Skill>) skillService.findAll();
    }

    @Override // TODO move this to a new entity-model converter object
    public CertificationsModel[] getCertificationsArray(List<Certifications> certifications) {
        CertificationsModel[] certificationsModels = new CertificationsModel[certifications.size()];
        for (int i = 0; i < certifications.size(); i++) {
            certificationsModels[i] = new CertificationsModel(certifications.get(i), false);
        }

        return certificationsModels;
    }


    @Override
    public CandidateFormModel createCandidate() {
        //Creates company registration model
        CandidateFormModel candidateFormModel = new CandidateFormModel();

        candidateFormModel.setCandidate(emptyEntityCreationService.emptyCandidate(CANDIDATE_LOCKED));

        candidateFormModel.setSkillPlaceholder(emptyEntityCreationService.emptyCandidateSkill());

        CandidateSkill[] skillsDecorator = new CandidateSkill[5];
        candidateFormModel.setSkillsDecorator(skillsDecorator);

        CertificationsModel[] certificationsDecorator = getCertificationsArray((List) certificationsService.findAll());
        candidateFormModel.setCertificationsDecorator(certificationsDecorator);

        candidateFormModel.getCandidate().getResume().setFileName("No file selected");

        candidateFormModel.setErrorSkillPlaceholder(emptyEntityCreationService.emptyCandidateSkill());
        candidateFormModel.setSignedInUser(false);
        return candidateFormModel;
    }

    @Override
    public CandidateFormModel prepareCandidate(ExternalContext externalContext) {
        String candidateUsername = (String) externalContext.getRequestParameterMap().asAttributeMap().get("candidateUsername");
        Candidate candidate = (Candidate) userDetailService.findUserByUsername(candidateUsername);
        CandidateFormModel candidateFormModel = new CandidateFormModel();

        candidateFormModel.setSkillPlaceholder(emptyEntityCreationService.emptyCandidateSkill());

        candidateFormModel.setSkillsDecorator(skillsListToArray(candidate.getSkills()));

        List<Certifications> certifications = (List) certificationsService.findAll();
        List<Certifications> consCertifications = candidate.getCertifications();
        CertificationsModel[] certificationsDecorator = new CertificationsModel[certifications.size()];
        for (int i = 0; i < certifications.size(); i++) {
            Certifications soft = certifications.get(i);
            if (consCertifications.contains(soft)) {
                certificationsDecorator[i] = new CertificationsModel(soft, true);
            } else certificationsDecorator[i] = new CertificationsModel(soft, false);
        }
        candidateFormModel.setCertificationsDecorator(certificationsDecorator);


        List<Area> areas = candidate.getAddress().getState().getAreas();
        Iterator iterator = areas.iterator();
        List<Area> consAreas = candidate.getAreas();
        AreaModel[] areaDecorator = new AreaModel[areas.size()];
        for (int i = 0; i < areaDecorator.length; i++) {
            Area area = (Area) iterator.next();
            if (consAreas.contains(area)) {
                areaDecorator[i] = new AreaModel(area, true);
            } else areaDecorator[i] = new AreaModel(area, false);
        }

        candidateFormModel.setAreaDecorator(areaDecorator);

        if (candidate.getResume() == null) {
            candidate.setResume(emptyEntityCreationService.emptyFile());
            candidate.getResume().setFileName("No file selected");
        }

        candidateFormModel.setCandidate(candidate);
        candidateFormModel.setErrorSkillPlaceholder(emptyEntityCreationService.emptyCandidateSkill());
        candidateFormModel.setSignedInUser(true);
        candidateFormModel.setInitialUsername(candidateUsername);

        return candidateFormModel;
    }

    @Override
    public CandidateFormModel addSkill(CandidateFormModel model) {
        CandidateSkill newSkill = model.getSkillPlaceholder();
        if (CandidateFormModel.isEmptyCandidateSkill(newSkill)) return model;

        CandidateSkill[] skillsDecorator;
        if (model.getSkillsDecorator() == null) {
            skillsDecorator = new CandidateSkill[5];
            model.setSkillsDecorator(skillsDecorator);
        } else skillsDecorator = model.getSkillsDecorator();


        for (int i = 0; i < skillsDecorator.length; i++) {
            CandidateSkill skill = skillsDecorator[i];
            if (skill == null) {
                skillsDecorator[i] = newSkill;
                model.setCount(model.getCount() + 1);
                model.setSkillPlaceholder(emptyEntityCreationService.emptyCandidateSkill());
                return model;
            }
        }
        if (model.getCount() >= 5) {
            model.setSkillPlaceholder(emptyEntityCreationService.emptyCandidateSkill());
        }
        return model;
    }

    @Override
    public CandidateFormModel deleteSkill(CandidateFormModel model) {
        int skillIndex = model.getIndex();
        CandidateSkill[] newCandidateSkillsList = new CandidateSkill[5];
        CandidateSkill[] oldCandidateSkillsList = model.getSkillsDecorator();
        for (int i = 0; i < oldCandidateSkillsList.length; i++) {
            if (i == skillIndex || oldCandidateSkillsList[i] == null) {
                model.setCount(model.getCount() - 1);
                continue;
            }
            newCandidateSkillsList[i] = oldCandidateSkillsList[i];
        }
        model.setSkillsDecorator(newCandidateSkillsList);
        return model;
    }


    @Override
    public CandidateFormModel updateArea(CandidateFormModel candidateFormModel) {
        State state = candidateFormModel.getCandidate().getAddress().getState();
        if (state == null || state.getAreas() == null) {
            candidateFormModel.setAreaDecorator(null);
            return candidateFormModel;
        }
        candidateFormModel.setAreaDecoratorFromCollection(candidateFormModel.getCandidate().getAddress().getState().getAreas());
        return candidateFormModel;
    }

    @Override
    public CandidateFormModel saveCandidate(CandidateFormModel model) {

        Candidate finalCandidate = model.getCandidate();

        CandidateSkill[] skillsDecorator = model.getSkillsDecorator();
        List<CandidateSkill> candidateSkills = finalCandidate.getSkills();
        for (int i = 0; i < model.getSkillsDecorator().length; i++) {
            CandidateSkill skill = skillsDecorator[i];
            if (skill != null) {
                candidateSkills.add(skill);
            }
        }

        CertificationsModel[] certificationsDecorator = model.getCertificationsDecorator();
        for (CertificationsModel certificationsModel : certificationsDecorator) {
            Certifications certifications = certificationsModel.getCertificationsModel();
            if (certifications != null && certificationsModel.isActive()) {
                finalCandidate.setCertifications(certifications);
            }
        }

        AreaModel[] areaDecorator = model.getAreaDecorator();
        for (AreaModel areaModel : areaDecorator) {
            Area area = areaModel.getArea();
            if (area != null && areaModel.isActive()) {
                finalCandidate.setArea(area);
            }
        }

        finalCandidate.setApproved(false);
        finalCandidate.setEnabled(false);
        finalCandidate = (Candidate) userDetailService.save(finalCandidate);

        VerificationToken verificationToken = applyForVerification(finalCandidate, finalCandidate.getName());
        emailService.sendCompanyAccountCreation(finalCandidate.getUsername(), finalCandidate.getName(), verificationToken.getToken());
        return model;
    }

    @Override // TODO remove code duplication
    public CandidateFormModel updateCandidate(CandidateFormModel model) {
        Candidate finalCandidate = model.getCandidate();

        if (!StringUtils.isNullOrEmpty(model.getNewPassword())) finalCandidate.setPassword(model.getNewPassword());

        CandidateSkill[] skillsDecorator = model.getSkillsDecorator();
        finalCandidate.setSkills(null);
        for (int i = 0; i < model.getSkillsDecorator().length; i++) {
            CandidateSkill skill = skillsDecorator[i];
            if (skill != null) {
                skill.correctStrings();
                finalCandidate.setskill(skill);
            }
        }

        CertificationsModel[] certificationsDecorator = model.getCertificationsDecorator();
        finalCandidate.setCertifications(new ArrayList<>());
        for (CertificationsModel certificationsModel : certificationsDecorator) {
            Certifications certifications = certificationsModel.getCertificationsModel();
            if (certifications != null && certificationsModel.isActive()) {
                certifications.correctStrings();
                finalCandidate.setCertifications(certifications);
            }
        }

        finalCandidate.correctStrings();
        finalCandidate.setApproved(false);
        Authority authority = extendedUserDetailService.findByRole(CANDIDATE_LOCKED);
        finalCandidate.setRole(authority);
        userDetailService.save(finalCandidate);
        return model;
    }

    @Override
    public CandidateFormModel uploadResume(CandidateFormModel candidateFormModel,
            RequestContext requestContext) {

        final ServletExternalContext context = (ServletExternalContext) requestContext.getExternalContext();
        final MultipartHttpServletRequest multipartRequest = (DefaultMultipartHttpServletRequest) context.getNativeRequest();
        try {
            ApplicationPart applicationPart = (ApplicationPart) ((DefaultMultipartHttpServletRequest) multipartRequest).getRequest().getPart("resumePath");
            File file = candidateFormModel.getCandidate().getResume();
            file.setFile(IOUtils.toByteArray(applicationPart.getInputStream()));
            file.setFileContent(applicationPart.getContentType());
            file.setFileName(applicationPart.getSubmittedFileName());
            file.setFileSize(applicationPart.getSize());

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        }
        return candidateFormModel;
    }

    @Override
    public CandidateFormModel downloadResume(CandidateFormModel candidateFormModel,
            RequestContext requestContext) {
        return candidateFormModel;
    }
}
