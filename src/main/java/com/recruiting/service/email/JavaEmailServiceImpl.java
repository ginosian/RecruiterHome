package com.recruiting.service.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Martha on 9/4/2017.
 */
@Service("javaEmailService")
public class JavaEmailServiceImpl extends AbstractEmailService implements EmailService{

    @Autowired
    JavaMailSender javaMailSender;


    @Override
    public void sendCandidateAccountCreation(String email, String name, String key) {
        String accountCreationHtml = fileReader("email/candidate_account_creation.html");
        String str = accountCreationHtml.replace("%s1", name);
        send(email, null, "Account created", str, true);
    }

    @Override
    public void sendCandidatePasswordChange(String email, String name, String key) {

        List<String> emails = new ArrayList<>();
        emails.add(email);
        String passwordChangeHtml = fileReader("email/candidate_password_change.html");
        String str = passwordChangeHtml.replace("%s1", name);
        str = str.replace("%s2", "Under construction");
        send(email, null, "Password change request", str, true);
    }

    @Override
    public void sendCompanyAccountCreation(String email, String name, String key) {
        String accountCreationHtml = fileReader("email/company_account_creation.html");
        String str = accountCreationHtml.replace("%s1", name);
        str = str.replace("%s2", "Under construction");
        send(email, null, "Account Creation Request", str, true);
    }

    @Override
    public void sendCompanyPasswordChange(String email, String name, String key) {
        List<String> emails = new ArrayList<>();
        emails.add(email);
        String passwordChangeHtml = fileReader("email/company_password_change.html");
        String str = passwordChangeHtml.replace("%s1", name);
        str = str.replace("%s2", "Under construction");
        send(email, null, "Password change request", str, true);
    }


    @Override
    public void sendCandidateInvitation(String email, String name, String body) {
        List<String> emails = new ArrayList<>();
        emails.add(email);
        String invitationHtml = fileReader("email/candidate_invitation.html");
        String str = invitationHtml.replace("%s1", name);
        send(email, null, "Finhire: Invitation for interview", str, true);
    }

    @Override
    public void sendUserReceiveMessage(String email, String name, String body) {
        List<String> emails = new ArrayList<>();
        emails.add(email);
        String invitationHtml = fileReader("email/new-message.html");
        String str = invitationHtml.replace("%s1", name);
        send(email, null, "Finhire: New message.", str, true);
    }

    @Override
    public void sendUserPasswordChange(String email, String name, String key) {
        List<String> emails = new ArrayList<>();
        emails.add(email);
        String passwordChangeHtml = fileReader("email/password_change.html");
        String str = passwordChangeHtml.replace("%s1", name);
        str = str.replace("%s2", "Under construction");
        send(email, null, "Password change request", str, true);
    }


    @Async
    public Boolean send(String to, String cc, String subject, String body, boolean isHtml) {

        try {

            final MimeMessage mimeMessage = this.javaMailSender.createMimeMessage();
            final MimeMessageHelper mail = new MimeMessageHelper(mimeMessage);

            mail.setTo(to);
            if (cc != null) {
                mail.setCc(cc);
            }
            mail.setFrom("marta.ginosian@gmail.com");
            mail.setSubject(subject);
            mail.setText(body, isHtml);
            javaMailSender.send(mimeMessage);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
