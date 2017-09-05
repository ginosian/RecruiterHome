package com.recruiting.domain;

import com.recruiting.utils.StringUtils;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * Created by Martha on 6/12/2017.
 */
@MappedSuperclass
public class AbstractTitledEntity extends AbstractEntity implements Serializable, Titled {

    // region Instance Fields
    @Column(name = "title")
    private String title;
    // endregion

    // region ctor
    public AbstractTitledEntity() {
    }

    public AbstractTitledEntity(String ssn) {
        super(ssn);
    }

    public AbstractTitledEntity(String ssn, String title) {
        super(ssn);
        this.title = title;
    }
    // endregion

    // region Transient methods
    public void correctStrings() {
        this.title = StringUtils.correctWhiteSpaces(title);
    }
    // endregion

    // region Getters and Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    // endregion
}
