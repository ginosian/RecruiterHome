package com.recruiting.service.email;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.InstanceProfileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceAsync;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceAsyncClient;
import com.amazonaws.services.simpleemail.model.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sghazaryan on 2/4/16.
 */
@Service("awsEmailService")
public class AWSEmailServiceImpl extends AbstractEmailService implements EmailService {

    @Value("${aws.key}")
    private String key;

    @Value("${aws.secret}")
    private String secret;

    @Value("${aws.ses.returnPath}")
    private String returnPath;

    @Value("#{'${aws.ses.replyToAddressessses}'.split(',')}")
    private List<String> replyAddresses;

    @Value("${aws.ses.source}")
    private String source;

    @Value("${aws.ses.invitation}")
    private String invitation;

    @Value("${candidate.account.creation.uri}")
    private String candidateAccountCreationUri;

    @Value("${candidate.password.change.uri}")
    private String candidatePasswordChangeUri;

    @Value("${company.account.creation.uri}")
    private String companyAccountCreationUri;

    @Value("${company.password.change.uri}")
    private String companyPasswordChangeUri;

    @Value("${password.change.uri}")
    private String passwordChangeUri;


    private AmazonSimpleEmailServiceAsync ses;

    @Override
    public void sendCandidateAccountCreation(String email, String name, String key) {

        String accountCreationHtml = fileReader("email/candidate_account_creation.html");
        String str = accountCreationHtml.replace("%s1", name);
        str = str.replace("%s2", candidateAccountCreationUri + key);

        List<String> emails = new ArrayList<>();
        emails.add(email);
        sendEmail(source, emails, "Account Creation Request", str, true, false);
    }

    @Override
    public void sendCandidatePasswordChange(String email, String name, String key) {

        List<String> emails = new ArrayList<>();
        emails.add(email);
        String passwordChangeHtml = fileReader("email/candidate_password_change.html");
        String str = passwordChangeHtml.replace("%s1", name);
        str = str.replace("%s2", candidatePasswordChangeUri + key);

        sendEmail(source, emails, "Password change request", str, true, true);
    }

    @Override
    public void sendCompanyAccountCreation(String email, String name, String key) {
        String accountCreationHtml = fileReader("email/company_account_creation.html");
        String str = accountCreationHtml.replace("%s1", name);
        str = str.replace("%s2", companyAccountCreationUri + key);

        List<String> emails = new ArrayList<>();
        emails.add(email);
        sendEmail(source, emails, "Account Creation Request", str, true, false);
    }

    @Override
    public void sendCompanyPasswordChange(String email, String name, String key) {
        List<String> emails = new ArrayList<>();
        emails.add(email);
        String passwordChangeHtml = fileReader("email/company_password_change.html");
        String str = passwordChangeHtml.replace("%s1", name);
        str = str.replace("%s2", companyPasswordChangeUri + key);

        sendEmail(source, emails, "Password change request", str, true, true);
    }


    @Override
    public void sendCandidateInvitation(String email, String name, String body) {
        List<String> emails = new ArrayList<>();
        emails.add(email);
        String invitationHtml = fileReader("email/candidate_invitation.html");
        String str = invitationHtml.replace("%s1", name);

        sendEmail(source, emails, "Finhire: Invitation for interview", str, true, true);
    }

    @Override
    public void sendUserReceiveMessage(String email, String name, String body) {
        List<String> emails = new ArrayList<>();
        emails.add(email);
        String invitationHtml = fileReader("email/new-message.html");
        String str = invitationHtml.replace("%s1", name);

        sendEmail(source, emails, "Finhire: New message.", str, true, true);
    }

    public void sendEmail(String source, List<String> toAddresses, String subject,
            String content, boolean isHtml, boolean async) throws AmazonClientException {
        SendEmailRequest request = new SendEmailRequest();
        Destination destination = new Destination(toAddresses);

        Body body = new Body();
        if (isHtml) {
            body.setHtml(new Content(content));
        } else {
            body.setText(new Content(content));
        }
        request.setSource(source);
        request.setReturnPath(returnPath);
        request.setReplyToAddresses(replyAddresses);
        request.setDestination(destination);
        request.setMessage(new Message(new Content(subject), body));
        if (async) {
            ses().sendEmailAsync(request);
        } else {
            ses().sendEmail(request);
        }
    }

    @Override
    public void sendUserPasswordChange(String email, String name, String key) {
        List<String> emails = new ArrayList<>();
        emails.add(email);
        String passwordChangeHtml = fileReader("email/password_change.html");
        String str = passwordChangeHtml.replace("%s1", name);
        str = str.replace("%s2", passwordChangeUri + key);

        sendEmail(source, emails, "Password change request", str, true, true);
    }

    private AmazonSimpleEmailServiceAsync ses() {
        if (ses == null) {
            if (key.equals("IAM")) {
                InstanceProfileCredentialsProvider provider =
                        new InstanceProfileCredentialsProvider();
                ses = new AmazonSimpleEmailServiceAsyncClient(provider);
            } else {
                ses = new AmazonSimpleEmailServiceAsyncClient(
                        new BasicAWSCredentials(this.key, this.secret)
                );

            }
            try {
                ses.setRegion(Region.getRegion(Regions.US_EAST_1));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return ses;
    }

}
