package com.recruiting.domain;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Martha on 6/19/2017.
 */
@Entity
@Table(name = "conversation")
public class Conversation extends AbstractEntity implements Serializable {

    // region Instance Fields
    @Column(name = "removed_by_company", columnDefinition = "BOOLEAN DEFAULT FALSE", nullable = false)
    private boolean removedByCompany;

    @Column(name = "removed_by_candidate", columnDefinition = "BOOLEAN DEFAULT FALSE", nullable = false)
    private boolean removedByCandidate;

    @OneToOne
    private Company company;

    @OneToOne
    private Candidate candidate;


//    @Where( clause = "user_type = 'CANDIDATE'")
//    @OneToOne
//    private User candidate;
//
//    @Where( clause = "user_type = 'COMPANY'")
//    @OneToOne
//    private User candidate;


    @OneToOne(cascade = CascadeType.ALL)
    private Interview interview;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Message lastMessage;

    // endregion

    // region Constructors

    public Conversation(String ssn) {
        super(ssn);
    }

    public Conversation(Company company,
            Candidate candidate,
            Interview interview, String ssn) {
        super(ssn);
        this.company = company;
        this.candidate = candidate;
        this.interview = interview;
    }

    public Conversation(Company company, Candidate candidate, String ssn) {
        super(ssn);
        this.company = company;
        this.candidate = candidate;
    }

    // endregion

    // region Transient methods
    public void correctStrings() {
        interview.correctStrings();
    }
    // endregion

    // region Getters and Setters

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Candidate getCandidate() {
        return candidate;
    }

    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
    }

    public Interview getInterview() {
        return interview;
    }

    public void setInterview(Interview interview) {
        this.interview = interview;
    }

    public Message getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(Message lastMessage) {
        this.lastMessage = lastMessage;
    }

// endregion

    // region Overrides
    @Override
    public String toString() {
        return "Conversation [" +
                "id: " + id + "\t" +
                "]";
    }
    // endregion

}
