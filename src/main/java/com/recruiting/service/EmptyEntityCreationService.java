package com.recruiting.service;

import com.recruiting.domain.*;

import java.util.List;

/**
 * Created by Martha on 5/19/2017.
 */
public interface EmptyEntityCreationService {
    Address emptyAddress();

    Company emptyCompany(String role);

    Candidate emptyCandidate(String role);

    Industry emptyIndustry();

    CompanyStaff emptyCompanyStaff();

    List<Authority> authorities(String role);

    CandidateSkill emptyCandidateSkill();

    Certifications emptyAccountingCertifications();

    Interview emptyInterview();

    File emptyFile();

    Message emptyMessage();

    Conversation emptyConversation();

    VerificationToken emptyVerificationToken();

    PasswordResetToken emptyPasswordResetToken();

    Administrator emptyAdmin();


}
