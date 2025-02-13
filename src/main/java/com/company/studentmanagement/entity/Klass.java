package com.company.studentmanagement.entity;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

@JmixEntity
@Table(name = "KLASS", indexes = {
        @Index(name = "IDX_CLASS_SCHOOL", columnList = "SCHOOL_ID")
})
@Entity
public class Klass {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;
    @Column(name = "VERSION", nullable = false)
    @Version
    private Integer version;
    @NotNull
    @InstanceName
    @Column(name = "NAME", nullable = false)
    private String name;
    @Column(name = "TOTAL_STUDENT")
    private Integer totalStudent=0;
    @NotNull
    @JoinColumn(name = "SCHOOL_ID", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Department school;

    public void setTotalStudent(Integer totalStudent) {
        this.totalStudent = totalStudent;
    }

    public Department getSchool() {
        return school;
    }

    public void setSchool(Department school) {
        this.school = school;
    }

    public Integer getTotalStudent() {
        return totalStudent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}