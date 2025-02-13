package com.company.studentmanagement.entity;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
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
    @NotNull
    @JoinColumn(name = "IN_CLASS_ID", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
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
}