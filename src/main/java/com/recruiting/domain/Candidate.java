package com.recruiting.domain;

import com.recruiting.listener.CandidateListener;
import com.recruiting.utils.StringUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Martha on 4/5/2017.
 */
@Entity
@Table(name = "candidate")
@EntityListeners(CandidateListener.class)
public class Candidate extends User implements Serializable {

    // region Instance Fields
    @Column(name = "birth_date")
    private Date birthDate;

    @Column(name = "starting_date")
    private Date startingDate;

    @Column(name = "time_period")
    private String timePeriod;

    @Column(name = "hourly_rate")
    private Integer hourlyRate;

    @OneToOne(cascade = {CascadeType.ALL},
            orphanRemoval = true)
    private File resume;

    @OneToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.ALL},
            orphanRemoval = true)
    private List<CandidateSkill> skills;

    @ManyToMany
    private List<Certifications> certifications;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Message> receivedMessages;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Area> areas;


//    @Generated( value = GenerationTime.ALWAYS )
//    @Column(columnDefinition =
//            "AS CONCAT(" +
//                    "    COALESCE(firstName, ''), " +
//                    "    COALESCE(' ' + lastName, ''), " +
//                    ")")
//    private String fullName;
    // endregion

    // region Constructors
    public Candidate() {
    }

    public Candidate(String username,
            String password,
            List<Authority> grantedAuthorities,
            Boolean isEnabled,
            String name,
            Date birthDate,
            String timePeriod,
            Integer hourlyRate,
            File resume,
            Address address,
            List<CandidateSkill> skills,
            List<Certifications> certifications,
            List<Area> areas,
            List<Message> receivedMessages,
            Date startingDate) {
        super(username, password, isEnabled, grantedAuthorities, address);
        super.setName(name);
        this.birthDate = birthDate;
        this.timePeriod = timePeriod;
        this.hourlyRate = hourlyRate;
        this.resume = resume;
        this.skills = skills;
        this.certifications = certifications;
        this.areas = areas;
        this.receivedMessages = receivedMessages;
        this.startingDate = startingDate;
    }

// endregion

    // region Transient Methods
    public void setskill(CandidateSkill skill) {
        if (skills == null) skills = new ArrayList<>();
        skills.add(skill);
    }

    public void setCertifications(Certifications certifications) {
        if (this.certifications == null) this.certifications = new ArrayList<>();
        this.certifications.add(certifications);
    }

    public void setArea(Area area) {
        if (this.areas == null) this.areas = new ArrayList<>();
        this.areas.add(area);
    }

    public void correctStrings() {
        super.correctStrings();
        this.resume.correctStrings();
        super.setName(StringUtils.correctWhiteSpaces(super.getName()));
        this.timePeriod = StringUtils.correctWhiteSpaces(timePeriod);
    }

    // endregion

    // region Getters and Setters
    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getTimePeriod() {
        return timePeriod;
    }

    public void setTimePeriod(String timePeriod) {
        this.timePeriod = timePeriod;
    }

    public Integer getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(Integer hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public void setResume(File resume) {
        this.resume = resume;
    }

    public File getResume() {
        return resume;
    }

    public Date getStartingDate() {
        return startingDate;
    }

    public void setStartingDate(Date startingDate) {
        this.startingDate = startingDate;
    }

    public List<CandidateSkill> getSkills() {
        return skills;
    }

    public void setSkills(List<CandidateSkill> skills) {
        this.skills = skills;
    }

    public List<Certifications> getCertifications() {
        return certifications;
    }

    public void setCertifications(List<Certifications> certifications) {
        this.certifications = certifications;
    }

    public List<Message> getReceivedMessages() {
        return receivedMessages;
    }

    public void setReceivedMessages(List<Message> receivedMessages) {
        this.receivedMessages = receivedMessages;
    }

    public List<Area> getAreas() {
        return areas;
    }

    public void setAreas(List<Area> areas) {
        this.areas = areas;
    }

    // endregion

    // region Overrides
    @Override
    public String toString() {
        return "Candidate [" +
                "id: " + id + "\t" +
                "name: " + super.getName() + "\t" +
                "timePeriod: " + timePeriod + "\t" +
                "]";
    }

    // endregion

}
