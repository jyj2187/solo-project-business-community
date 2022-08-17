package com.codestates.soloprojectbusinesscommunity.api.v1.dto;

import com.codestates.soloprojectbusinesscommunity.api.v1.domain.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class MemberRequestDto {
    private String name;
    private String password;
    private String sex;
    private String companyName;
    private String companyType;
    private String companyLocation;

    @Builder
    public MemberRequestDto(String name, String password, String sex, String companyName, String companyType, String companyLocation) {
        this.name = name;
        this.password = password;
        this.sex = sex;
        this.companyName = companyName;
        this.companyType = companyType;
        this.companyLocation = companyLocation;
    }

    public Member toEntity() {
        return Member.builder()
                .name(name)
                .password(password)
                .sex(sex)
                .companyName(companyName)
                .companyType(companyType)
                .companyLocation(companyLocation)
                .build();
    }
}
