package com.company.studentmanagement.entity;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.Composition;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@JmixEntity
@Table(name = "LECTURER", indexes = {
        @Index(name = "IDX_LECTURER_MAJOR", columnList = "MAJOR_ID")
})
@Entity
public class Lecturer {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;
    @Column(name = "VERSION", nullable = false)
    @Version
    private Integer version;
    @InstanceName
    @Column(name = "NAME", nullable = false)
    @NotNull
    private String name;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @NotNull
    @JoinColumn(name = "MAJOR_ID", nullable = false)
    private Major major;
    @Column(name = "DOB", nullable = false)
    @NotNull
    private LocalDate dob;
    @NotNull
    @Column(name = "PHONE", nullable = false)
    private String phone;
    @Column(name = "ADDRESS")
    private String address;
    @Composition
    @OneToMany(mappedBy = "lecturer")
    private List<LecturerClass> lecturerClasses;

    public List<LecturerClass> getLecturerClasses() {
        return lecturerClasses;
    }

    public void setLecturerClasses(List<LecturerClass> lecturerClasses) {
        this.lecturerClasses = lecturerClasses;
    }

    public Major getMajor() {
        return major;
    }

    public void setMajor(Major major) {
        this.major = major;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Lecturer lecturer = (Lecturer) o;
        return Objects.equals(getId(), lecturer.getId())
                && Objects.equals(getName(), lecturer.getName())
                && Objects.equals(getMajor(), lecturer.getMajor())
                && Objects.equals(getDob(), lecturer.getDob())
                && Objects.equals(getPhone(), lecturer.getPhone())
                && Objects.equals(getAddress(), lecturer.getAddress());
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(getName());
        result = 31 * result + Objects.hashCode(getMajor());
        result = 31 * result + Objects.hashCode(getDob());
        result = 31 * result + Objects.hashCode(getPhone());
        result = 31 * result + Objects.hashCode(getAddress());
        return result;
    }
}