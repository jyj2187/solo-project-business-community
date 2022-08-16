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
    private String company_name;
    private String company_type;
    private String company_location;

//    @Builder
    public Member(String name, String password, String sex, String company_name, String company_type, String company_location) {
        this.name = name;
        this.password = password;
        this.sex = sex;
        this.company_name = company_name;
        this.company_type = company_type;
        this.company_location = company_location;
    }
}
