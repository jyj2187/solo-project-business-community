package com.codestates.soloprojectbusinesscommunity.api.v1.dto;

import com.codestates.soloprojectbusinesscommunity.api.v1.domain.Member;
import lombok.Getter;

@Getter
public class MemberResponseDto {
    private Long id;
    private String name;
    private String sex;
    private String company_name;
    private String company_type;
    private String company_location;

    public MemberResponseDto(Long id, String name, String sex, String company_name, String company_type, String company_location) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.company_name = company_name;
        this.company_type = company_type;
        this.company_location = company_location;
    }

    public MemberResponseDto(Member entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.sex = entity.getSex();
        this.company_name = entity.getCompany_name();
        this.company_type = entity.getCompany_type();
        this.company_location = entity.getCompany_location();
    }
}
