package com.codestates.soloprojectbusinesscommunity.api.v1.dto;

import com.codestates.soloprojectbusinesscommunity.api.v1.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
public class MemberListResponseDto {
    private List<MemberResponseDto> memberList;
    private PageInfo pageInfo;

    public MemberListResponseDto(List<Member> memberList, Page page) {
        this.memberList = memberList.stream().map(MemberResponseDto::new).collect(Collectors.toList());
        this.pageInfo = new PageInfo(page);
    }
}
