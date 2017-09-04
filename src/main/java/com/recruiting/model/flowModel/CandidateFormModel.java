package com.recruiting.model.flowModel;

import com.recruiting.domain.Area;
import com.recruiting.domain.Candidate;
import com.recruiting.domain.CandidateSkill;
import com.recruiting.model.AreaModel;
import com.recruiting.model.CertificationsModel;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;

/**
 * Created by Martha on 5/15/2017.
 */
public class CandidateFormModel extends AbstractFlowModel implements Serializable {

    // region Instance fields
    private Candidate candidate;
    private CandidateSkill[] skillsDecorator;
    private CertificationsModel[] certificationsDecorator;
    private CandidateSkill skillPlaceholder;
    private CandidateSkill errorSkillPlaceholder;
    private AreaModel[] areaDecorator;
    private String location;
    // endregion

    // region Getters and Setters

    public Candidate getCandidate() {
        return candidate;
    }

    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
    }

    public CandidateSkill[] getSkillsDecorator() {
        return skillsDecorator;
    }

    public void setSkillsDecorator(CandidateSkill[] skillsDecorator) {
        this.skillsDecorator = skillsDecorator;
    }

    public CertificationsModel[] getCertificationsDecorator() {
        return certificationsDecorator;
    }

    public void setCertificationsDecorator(CertificationsModel[] certificationsDecorator) {
        this.certificationsDecorator = certificationsDecorator;
    }

    public CandidateSkill getSkillPlaceholder() {
        return skillPlaceholder;
    }

    public void setSkillPlaceholder(CandidateSkill skillPlaceholder) {
        this.skillPlaceholder = skillPlaceholder;
    }

    public CandidateSkill getErrorSkillPlaceholder() {
        return errorSkillPlaceholder;
    }

    public void setErrorSkillPlaceholder(CandidateSkill errorSkillPlaceholder) {
        this.errorSkillPlaceholder = errorSkillPlaceholder;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public AreaModel[] getAreaDecorator() {
        return areaDecorator;
    }

    public void setAreaDecorator(AreaModel[] areaDecorator) {
        this.areaDecorator = areaDecorator;
    }

    public void setAreaDecoratorFromCollection(Collection<Area> areas) {
        if (this.areaDecorator == null) this.areaDecorator = new AreaModel[areas.size()];
        Iterator iterator = areas.iterator();
        for (int i = 0; i < areaDecorator.length; i++) {
            areaDecorator[i] = new AreaModel((Area) iterator.next(), false);
        }
    }

    public boolean noAreaSelected() {
        if (areaDecorator == null || areaDecorator.length == 0) return true;
        for (AreaModel areaModel : areaDecorator) {
            if (areaModel.isActive()) return false;
        }
        return true;
    }

    public boolean noSkillsInDecorator() {
        int index = 0;
        for (CandidateSkill skill : skillsDecorator) {
            if (skill != null) ++index;
        }
        return index == 0;
    }

    // endregion

    public static boolean isEmptyCandidateSkill(CandidateSkill skill) {
        return !(skill != null
                && (skill.getSkill() != null && skill.getSkill().getId() != null));
    }

}
