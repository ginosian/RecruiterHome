package com.recruiting.service.impl;

import com.recruiting.domain.*;
import com.recruiting.service.EmptyEntityCreationService;
import com.recruiting.service.entity.ExtendedUserDetailService;
import com.recruiting.utils.BusKeyGen;
import com.recruiting.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Martha on 5/19/2017.
 */
@Service
public class EmptyEntityCreationServiceImpl implements EmptyEntityCreationService {

    @Autowired
    ExtendedUserDetailService extendedUserDetailService;

    @Override
    public Address emptyAddress() {
        Address mainAddress = new Address();
        mainAddress.setSsn(BusKeyGen.nextKey());
        State state = new State(BusKeyGen.nextKey());
        mainAddress.setState(state);
        return mainAddress;
    }

    @Override
    public List<Authority> authorities(String role) {
        Authority roleFromDB = extendedUserDetailService.findByRole(role);
        List<Authority> authorities = new ArrayList<>();
        authorities.add(roleFromDB);
        return authorities;
    }

    @Override
    public Company emptyCompany(String role) {
        //Creates Company entity
        Company company = new Company();
        company.setSsn(BusKeyGen.nextKey());

        //Grants company with authorities and permissions
        company.setGrantedAuthorities(authorities(role));
        company.setAccountNonExpired(true);
        company.setCredentialsNonExpired(true);
        company.setEnabled(true);
        company.setAccountNonLocked(true);
        company.setMainContact(emptyCompanyStaff());
        company.setAddress(emptyAddress());
        company.setIndustry(emptyIndustry());

        List<Address> additionalAddresses = new ArrayList<>();
        company.setAddresses(additionalAddresses);

        return company;
    }

    @Override
    public Industry emptyIndustry() {
        return new Industry(BusKeyGen.nextKey());
    }

    @Override
    public CompanyStaff emptyCompanyStaff() {
        CompanyStaff companyStaff =  new CompanyStaff(BusKeyGen.nextKey());
        return companyStaff;
    }


    @Override
    public Candidate emptyCandidate(String role) {
        //Creates Company entity
        Candidate candidate = new Candidate();
        candidate.setSsn(BusKeyGen.nextKey());

        //Grants company with authorities and permissions
        candidate.setGrantedAuthorities(authorities(role));
        candidate.setAccountNonExpired(true);
        candidate.setCredentialsNonExpired(true);
        candidate.setEnabled(true);
        candidate.setAccountNonLocked(true);
        candidate.setAddress(emptyAddress());
        candidate.setResume(emptyFile());

        List<Certifications> certifications = new ArrayList<>();
        candidate.setCertifications(certifications);

        List<CandidateSkill> candidateSkills = new ArrayList<>();
        candidate.setSkills(candidateSkills);

        return candidate;
    }

    @Override
    public CandidateSkill emptyCandidateSkill() {
        CandidateSkill candidateSkill = new CandidateSkill(BusKeyGen.nextKey());
        Skill skill = new Skill(BusKeyGen.nextKey());
        candidateSkill.setSkill(skill);
        return candidateSkill;
    }

    @Override
    public Certifications emptyAccountingCertifications() {
        return   new Certifications(BusKeyGen.nextKey());
    }

    @Override
    public File emptyFile() {
        return new File(BusKeyGen.nextKey());
    }

    @Override
    public Interview emptyInterview() {
        Interview interview = new Interview(BusKeyGen.nextKey());
        interview.setRejected(false);
        interview.setAccepted(false);
        interview.setCompanyMessage(emptyMessage());
        interview.setCandidateResponse(emptyMessage());
        return interview;
    }

    @Override
    public Message emptyMessage() {
        Message message = new Message();
        message.setSsn(BusKeyGen.nextKey());
        message.setSeen(false);
        return message;
    }

    @Override
    public Conversation emptyConversation() {
        return new Conversation(BusKeyGen.nextKey());
    }

    @Override
    public VerificationToken emptyVerificationToken() {
        VerificationToken verificationToken = new VerificationToken(BusKeyGen.nextKey());
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expirationDate = now.plusMinutes(Constants.VERIFICATION_TOKEN_EXPIRATION_MINUTES);
        verificationToken.setExpiryDate(expirationDate);
        verificationToken.setToken(BusKeyGen.nextKey());

        return verificationToken;
    }

    @Override
    public PasswordResetToken emptyPasswordResetToken() {
        PasswordResetToken passwordResetToken = new PasswordResetToken(BusKeyGen.nextKey());
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expirationDate = now.plusMinutes(Constants.PASSWORD_RESET_TOKEN_EXPIRATION_MINUTES);
        passwordResetToken.setExpiryDate(expirationDate);
        passwordResetToken.setToken(BusKeyGen.nextKey());
        return passwordResetToken;
    }

    @Override
    public Administrator emptyAdmin() {
        //Creates Admin entity
        Administrator administrator = new Administrator();
        administrator.setSsn(BusKeyGen.nextKey());
        //Grants admin with authorities and permissions
        administrator.setGrantedAuthorities(authorities("ADMIN"));
        administrator.setAccountNonExpired(true);
        administrator.setCredentialsNonExpired(true);
        administrator.setEnabled(true);
        administrator.setAccountNonLocked(true);

        return administrator;
    }
}
