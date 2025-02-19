package com.company.studentmanagement.entity;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import jakarta.persistence.*;

import java.util.Objects;
import java.util.UUID;

@JmixEntity
@Table(name = "LECTURER_CLASS", indexes = {
        @Index(name = "IDX_LECTURER_CLASS_LECTURER", columnList = "LECTURER_ID"),
        @Index(name = "IDX_LECTURER_CLASS_KLASS", columnList = "KLASS_ID")
})
@Entity
public class LecturerClass {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;
    @Column(name = "VERSION", nullable = false)
    @Version
    private Integer version;
    @JoinColumn(name = "LECTURER_ID")
    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
    private Lecturer lecturer;
    @JoinColumn(name = "KLASS_ID")
    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
    private Klass klass;

    public Klass getKlass() {
        return klass;
    }

    public void setKlass(Klass klass) {
        this.klass = klass;
    }

    public Lecturer getLecturer() {
        return lecturer;
    }

    public void setLecturer(Lecturer lecturer) {
        this.lecturer = lecturer;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LecturerClass that = (LecturerClass) o;
        return Objects.equals(lecturer.getId(), that.lecturer.getId())
                && Objects.equals(klass.getId(), that.klass.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getVersion(), getLecturer(), getKlass());
    }
}