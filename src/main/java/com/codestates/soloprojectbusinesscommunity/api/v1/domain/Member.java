package com.codestates.soloprojectbusinesscommunity.api.v1.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@NoArgsConstructor
@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String password;
    private String sex;
    private String companyName;
    private String companyType;
    private String companyLocation;

    @Builder
    public Member(String name, String password, String sex, String companyName, String companyType, String companyLocation) {
        this.name = name;
        this.password = password;
        this.sex = sex;
        this.companyName = companyName;
        this.companyType = companyType;
        this.companyLocation = companyLocation;
    }

    public void changePassword(String password) {
        this.password = password;
    }
    public void update(String companyName, String companyType, String companyLocation) {
        this.companyName = companyName;
        this.companyType = companyType;
        this.companyLocation = companyLocation;
    }
}
