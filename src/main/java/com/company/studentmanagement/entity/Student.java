package com.company.studentmanagement.entity;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@JmixEntity
@Table(name = "STUDENT", indexes = {
        @Index(name = "IDX_STUDENT_IN_CLASS", columnList = "IN_CLASS_ID")
})
@Entity
public class Student {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;
    @Column(name = "VERSION", nullable = false)
    @Version
    private Integer version;
    @Column(name = "CODE")
    private String code;
    @InstanceName
    @Column(name = "NAME", nullable = false)
    @NotNull
    private String name;
    @Column(name = "DOB", nullable = false)
    @NotNull
    private LocalDate dob;
    @Column(name = "PHONE", nullable = false)
    @NotNull
    private String phone;
    @Column(name = "ADDRESS")
    private String address;

    @JoinColumn(name = "IN_CLASS_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Klass inClass;

    public Klass getInClass() {
        return inClass;
    }

    public void setInClass(Klass inClass) {
        this.inClass = inClass;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

        Student student = (Student) o;
        return Objects.equals(getId(), student.getId()) && Objects.equals(getVersion(), student.getVersion()) && Objects.equals(getCode(), student.getCode()) && Objects.equals(getName(), student.getName()) && Objects.equals(getDob(), student.getDob()) && Objects.equals(getPhone(), student.getPhone()) && Objects.equals(getAddress(), student.getAddress()) && Objects.equals(getInClass(), student.getInClass());
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(getId());
        result = 31 * result + Objects.hashCode(getVersion());
        result = 31 * result + Objects.hashCode(getCode());
        result = 31 * result + Objects.hashCode(getName());
        result = 31 * result + Objects.hashCode(getDob());
        result = 31 * result + Objects.hashCode(getPhone());
        result = 31 * result + Objects.hashCode(getAddress());
        result = 31 * result + Objects.hashCode(getInClass());
        return result;
    }
}