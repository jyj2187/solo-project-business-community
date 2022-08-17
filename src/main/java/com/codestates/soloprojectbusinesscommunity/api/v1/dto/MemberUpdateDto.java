package com.codestates.soloprojectbusinesscommunity.api.v1.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class MemberUpdateDto {
    private String password;
    private String companyName;
    private String companyType;
    private String companyLocation;

    public MemberUpdateDto(String password, String companyName, String companyType, String companyLocation) {
        this.password = password;
        this.companyName = companyName;
        this.companyType = companyType;
        this.companyLocation = companyLocation;
    }
}
