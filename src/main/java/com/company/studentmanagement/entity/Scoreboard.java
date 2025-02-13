package com.company.studentmanagement.entity;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.JmixEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;

import java.util.UUID;

@JmixEntity
@Table(name = "SCOREBOARD", indexes = {
        @Index(name = "IDX_SCOREBOARD_STUDENT", columnList = "STUDENT_ID"),
        @Index(name = "IDX_SCOREBOARD_MAJOR", columnList = "MAJOR_ID")
})
@Entity
public class Scoreboard {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;
    @Column(name = "VERSION", nullable = false)
    @Version
    private Integer version;
    @Column(name = "MARK")
    @Min(value = 0,message = "Mark cannot be negative")
    private Double mark = 0.0;
    @JoinColumn(name = "STUDENT_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Student student;
    @JoinColumn(name = "MAJOR_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Major major;

    public Major getMajor() {
        return major;
    }

    public void setMajor(Major major) {
        this.major = major;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Double getMark() {
        return mark;
    }

    public void setMark(Double mark) {
        this.mark = mark;
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