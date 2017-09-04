package com.recruiting.domain;

import com.recruiting.converter.LocalDateTimeAttributeConverter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Created by Martha on 4/5/2017.
 */
@MappedSuperclass
public abstract class AbstractEntity implements Serializable {

    // region Instance Fields
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    protected Long id;

    @Convert(converter = LocalDateTimeAttributeConverter.class)
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

    // endregion


    // region Hashcode/equals/toString overrides
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof AbstractEntity)) return false;
        Long id = this.getId();
        return (id != null && id.equals(((AbstractEntity) obj).getId())) || super.equals(obj);
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getId() != null ? getId().hashCode() : 0);
        return result;
    }
    // endregion
}
