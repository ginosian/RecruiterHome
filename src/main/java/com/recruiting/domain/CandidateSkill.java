package com.recruiting.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by Martha on 4/5/2017.
 */
@Entity
@Table(name = "candidate_skill")
public class CandidateSkill extends AbstractEntity implements Serializable {

    // region Instance Fields
    @OneToOne
    private Skill skill;

    @Column(name = "duration", nullable = false)
    private Integer experienceDuration;
    // endregion

    // region Constructors
    public CandidateSkill() {
    }

    public CandidateSkill(Skill skill, Integer experienceDuration) {
        this.skill = skill;
        this.experienceDuration = experienceDuration;
    }
    // endregion

    // region Transient methods
    public void correctStrings() {
        skill.correctStrings();
    }
    // endregion

    // region Getters and Setters
    public Skill getSkill() {
        return skill;
    }

    public void setSkill(Skill skill) {
        this.skill = skill;
    }

    public Integer getExperienceDuration() {
        return experienceDuration;
    }

    public void setExperienceDuration(Integer experienceDuration) {
        this.experienceDuration = experienceDuration;
    }

    // endregion

    // region Overrides
    @Override
    public String toString() {
        return "CandidateSkill [" +
                "id: " + id + "\t" +
                "skill: " + skill + "\t" +
                "experienceDuration: " + experienceDuration +
                "]";
    }
    // endregion
}
