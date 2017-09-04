package com.recruiting.model;

import com.recruiting.domain.Conversation;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Created by Martha on 6/27/2017.
 */
public class CandidateInterviewModel implements Serializable {

    private Conversation conversation;
    private LocalDateTime interviewDate;
    private String accepted;
    private String rejected;
    private String interviewStatus;

    public CandidateInterviewModel() {
    }

    public CandidateInterviewModel(
            Conversation conversation,
            LocalDateTime interviewDate,
            String accepted,
            String rejected, String interviewStatus) {
        this.conversation = conversation;
        this.interviewDate = interviewDate;
        this.accepted = accepted;
        this.rejected = rejected;
        this.interviewStatus = interviewStatus;
    }

    public Conversation getConversation() {
        return conversation;
    }

    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }

    public LocalDateTime getInterviewDate() {
        return interviewDate;
    }

    public void setInterviewDate(LocalDateTime interviewDate) {
        this.interviewDate = interviewDate;
    }

    public String getAccepted() {
        return accepted;
    }

    public void setAccepted(String accepted) {
        this.accepted = accepted;
    }

    public String getRejected() {
        return rejected;
    }

    public void setRejected(String rejected) {
        this.rejected = rejected;
    }

    public String getInterviewStatus() {
        return interviewStatus;
    }

    public void setInterviewStatus(String interviewStatus) {
        this.interviewStatus = interviewStatus;
    }
}
