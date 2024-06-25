package com.thegather.api.domain.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "USERS")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(nullable = false, length = 255, name = "NAME")
    private String name;

    @Column(nullable = false, length = 255, name = "EMAIL")
    private String email;

    @Column(nullable = false, length = 255, name = "PASSWORD")
    private String password;

    @Column(length = 255, name = "ADDRESS")
    private String address;

    @Column(nullable = false, length = 20, name = "PHONE")
    private String phone;

    @Column(nullable = false, length = 8, name = "CEP")
    private String CEP;

    @Column(nullable = false, length = 11, name = "CPF")
    private String CPF;

    @Column(nullable = false, name = "OFFICE")
    private int office;

    @Column(name = "COMPANY_ID")
    private int company_id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getCEP() {
        return CEP;
    }

    public void setCEP(String CEP) {
        this.CEP = CEP;
    }

    public String getCPF() {
        return CPF;
    }

    public void setCPF(String CPF) {
        this.CPF = CPF;
    }

    public int getOffice() {
        return office;
    }

    public void setOffice(int office) {
        this.office = office;
    }

    public int getCompany_id() {
        return company_id;
    }

    public void setCompany_id(int company_id) {
        this.company_id = company_id;
    }
    public User(Long id, String name, String email, String password, String address, String phone, String CEP, String CPF, int office, int company_id) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.address = address;
        this.phone = phone;
        this.CEP = CEP;
        this.CPF = CPF;
        this.office = office;
        this.company_id = company_id;
    }

}
