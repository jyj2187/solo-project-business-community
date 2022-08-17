package com.codestates.soloprojectbusinesscommunity.api.v1.dto;

import com.codestates.soloprojectbusinesscommunity.api.v1.domain.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class MemberResponseDto {
    private Long id;
    private String name;
    private String sex;
    private String companyName;
    private String companyType;
    private String companyLocation;

    public MemberResponseDto(Long id, String name, String sex, String companyName, String companyType, String companyLocation) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.companyName = companyName;
        this.companyType = companyType;
        this.companyLocation = companyLocation;
    }

    public MemberResponseDto(Member entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.sex = entity.getSex();
        this.companyName = entity.getCompanyName();
        this.companyType = entity.getCompanyType();
        this.companyLocation = entity.getCompanyLocation();
    }
}
