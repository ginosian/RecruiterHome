package com.recruiting.service.form.impl;

import com.recruiting.domain.*;
import com.recruiting.repository.StateRepository;
import com.recruiting.service.EmptyEntityCreationService;
import com.recruiting.service.email.EmailService;
import com.recruiting.service.entity.ExtendedUserDetailService;
import com.recruiting.service.form.AbstractFormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Martha on 5/15/2017.
 */
@Service("adminFormService")
@PropertySource("classpath:auth.properties")
public class AbstractFormServiceImpl implements AbstractFormService {

    @Autowired
    StateRepository stateRepository;

    @Autowired
    EmptyEntityCreationService emptyEntityCreationService;

    @Autowired
    ExtendedUserDetailService extendedUserDetailService;

    @Qualifier("javaEmailService")
    @Autowired
    EmailService emailService;


    @Value("${company}")
    protected String COMPANY;
    @Value("${role_company}")
    protected String ROLE_COMPANY;
    @Value("${company_locked}")
    protected String COMPANY_LOCKED;
    @Value("${role_company_locked}")
    protected String ROLE_COMPANY_LOCKED;
    @Value("${candidate}")
    protected String CANDIDATE;
    @Value("${candidate_locked}")
    protected String CANDIDATE_LOCKED;
    @Value("${role_candidate_locked}")
    protected String ROLE_CANDIDATE_LOCKED;
    @Value("${role_candidate}")
    protected String ROLE_CANDIDATE;
    @Value("${admin}")
    protected String ADMIN;
    @Value("${role_admin}")
    protected String ROLE_ADMIN;


    public List<State> getStates() {
        return stateRepository.findAll(new Sort(Sort.Direction.ASC, "title"));
    }

    protected CandidateSkill[] skillsListToArray(Collection<CandidateSkill> candidateSkills) {
        if (candidateSkills == null || candidateSkills.size() == 0) return null;
        CandidateSkill[] skillDecorator = new CandidateSkill[5];
        Iterator iterator = candidateSkills.iterator();
        int index = 4;
        while (iterator.hasNext() && index > 0) {
            CandidateSkill skill = (CandidateSkill) iterator.next();
            skillDecorator[index] = skill;
            --index;
        }
        return skillDecorator;
    }

    protected Address[] addressesListToArray(Collection<Address> addresses) {
        if (addresses == null || addresses.size() == 0) return null;
        Address[] addressDecorator = new Address[5];
        Iterator iterator = addresses.iterator();
        int index = 4;
        while (iterator.hasNext() && index > 0) {
            Address address = (Address) iterator.next();
            addressDecorator[index] = address;
            --index;
        }
        return addressDecorator;
    }

    protected VerificationToken applyForVerification(User user, String name) {
        VerificationToken verificationToken = emptyEntityCreationService.emptyVerificationToken();
        verificationToken.setUser(user);
        verificationToken = (VerificationToken) extendedUserDetailService.saveVerificationToken(verificationToken);
        emailService.sendCompanyAccountCreation(user.getUsername(), name, verificationToken.getToken());
        return verificationToken;
    }

}
