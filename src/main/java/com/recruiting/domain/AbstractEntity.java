package com.recruiting.domain;

import com.recruiting.converter.LocalDateTimeAttributeConverter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Created by Martha on 4/5/2017.
 */
@MappedSuperclass
public abstract class AbstractEntity implements Serializable {

    // region Instance Fields
    @Id
    @GenericGenerator(
            name = "sequenceGenerator",
            strategy = "enhanced-sequence",
            parameters = {
                    @org.hibernate.annotations.Parameter(
                            name = "optimizer",
                            value = "pooled-lo"
                    ),
                    @org.hibernate.annotations.Parameter(
                            name = "initial_value",
                            value = "1"
                    ),
                    @org.hibernate.annotations.Parameter(
                            name = "increment_size",
                            value = "5"
                    )
            }
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "sequenceGenerator"
    )
    @Column(name = "id")
    protected Long id;

    @NaturalId
    @Column(name = "ssn")
    private String ssn;

    @Convert(converter = LocalDateTimeAttributeConverter.class)
//    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Convert(converter = LocalDateTimeAttributeConverter.class)
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "removed", columnDefinition = "BOOLEAN DEFAULT FALSE", nullable = false)
    private boolean removed;
    // endregion

    // region Constructors
    public AbstractEntity() {
    }

    public AbstractEntity(String ssn) {
        this.ssn = ssn;
    }

    // endregion

    // region Transient methods
    @PrePersist
    protected void onPrePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
    }

    @PreUpdate
    protected void onPreUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
    // endregion

    // region Getters and Setters
    public Long getId() {
        return id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public boolean isRemoved() {
        return removed;
    }

    public void setRemoved(boolean removed) {
        this.removed = removed;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    // endregion


    // region Hashcode/equals/toString overrides

    @Override
    public int hashCode() {
        return Objects.hash( ssn);
    }

    @Override
    public boolean equals(Object o) {
        if ( this == o ) {
            return true;
        }
        if ( !( o instanceof AbstractEntity ) ) {
            return false;
        }
        AbstractEntity entity = (AbstractEntity) o;
        return Objects.equals( ssn, entity.ssn );
    }
    // endregion
}
