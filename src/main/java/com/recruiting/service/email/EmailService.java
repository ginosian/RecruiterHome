package com.recruiting.service.email;

/**
 * Created by Martha on 6/25/2017.
 */
public interface EmailService {

    void sendCandidateAccountCreation(String email, String name, String key);

    void sendCandidatePasswordChange(String email, String name, String key);

    void sendCompanyAccountCreation(String email, String name, String key);

    void sendCompanyPasswordChange(String email, String name, String key);

    void sendCandidateInvitation(String email, String name, String body);

    void sendUserPasswordChange(String email, String name, String body);

    void sendUserReceiveMessage(String email, String name, String body);
}
