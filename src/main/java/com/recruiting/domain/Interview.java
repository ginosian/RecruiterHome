package com.recruiting.domain;

import com.recruiting.utils.StringUtils;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Created by Martha on 4/5/2017.
 */
@Entity
@Table(name = "interview")
public class Interview extends AbstractEntity implements Serializable { //TODO  remove messages and correct Arrange interview logic accordingly

    // region Instance Fields
    @Column(name = "rejected")
    private Boolean rejected;

    @Column(name = "accepted")
    private Boolean accepted;

    @Column(name = "interview_date")
    @DateTimeFormat(pattern = "yyyy/MM/dd hh:mm a")
    private LocalDateTime interviewDate;

    @Column(name = "interview_date_optional_one")
    @DateTimeFormat(pattern = "yyyy/MM/dd hh:mm a")
    private LocalDateTime interviewDateOptional1;

    @Column(name = "interview_date_optional_two")
    @DateTimeFormat(pattern = "yyyy/MM/dd hh:mm a")
    private LocalDateTime interviewDateOptional2;

    @Column(name = "location")
    private String location;

    @OneToOne
    private Message companyMessage;

    @OneToOne
    private Message candidateResponse;

    // endregion

    // region Constructors
    public Interview() {
    }

    public Interview(String ssn) {
        super(ssn);
    }

    public Interview(Boolean rejected,
            LocalDateTime interviewDate,
            LocalDateTime interviewDateOptional1,
            LocalDateTime interviewDateOptional2,
            String ssn) {
        super(ssn);
        this.rejected = rejected;
        this.interviewDate = interviewDate;
        this.interviewDateOptional1 = interviewDateOptional1;
        this.interviewDateOptional2 = interviewDateOptional2;
    }
    // endregion

    // region Transient methods
    public void correctStrings() {
        this.location = StringUtils.correctWhiteSpaces(location);
        companyMessage.correctStrings();
        candidateResponse.correctStrings();
    }

    public boolean getExpired() {
        if (!accepted && !rejected) {
            if ((interviewDate != null && interviewDate.isAfter(LocalDateTime.now()))
                    || (interviewDateOptional1 != null && interviewDateOptional1.isAfter(LocalDateTime.now()))
                    || (interviewDateOptional2 != null && interviewDateOptional2.isAfter(LocalDateTime.now())))
                return false;
        } else if (rejected) {
            return true;
        }
        return false;
    }
    // endregion

    // region Getters and Setters

    public Boolean getRejected() {
        return rejected;
    }

    public void setRejected(Boolean rejected) {
        this.rejected = rejected;
    }

    public LocalDateTime getInterviewDate() {
        return interviewDate;
    }

    public void setInterviewDate(LocalDateTime interviewDate) {
        this.interviewDate = interviewDate;
    }

    public LocalDateTime getInterviewDateOptional1() {
        return interviewDateOptional1;
    }

    public void setInterviewDateOptional1(LocalDateTime interviewDateOptional1) {
        this.interviewDateOptional1 = interviewDateOptional1;
    }

    public LocalDateTime getInterviewDateOptional2() {
        return interviewDateOptional2;
    }

    public void setInterviewDateOptional2(LocalDateTime interviewDateOptional2) {
        this.interviewDateOptional2 = interviewDateOptional2;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Message getCompanyMessage() {
        return companyMessage;
    }

    public void setCompanyMessage(Message companyMessage) {
        this.companyMessage = companyMessage;
    }

    public Message getCandidateResponse() {
        return candidateResponse;
    }

    public void setCandidateResponse(Message candidateResponse) {
        this.candidateResponse = candidateResponse;
    }

    public Boolean getAccepted() {
        return accepted;
    }

    public void setAccepted(Boolean accepted) {
        this.accepted = accepted;
    }

    // endregion

    // region Overrides
    @Override
    public String toString() {
        return "Message [" +
                "id: " + id + "\t" +
                "]";
    }
    // endregion

}
