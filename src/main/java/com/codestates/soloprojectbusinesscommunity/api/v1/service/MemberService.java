package com.codestates.soloprojectbusinesscommunity.api.v1.service;

import com.codestates.soloprojectbusinesscommunity.api.v1.domain.Member;
import com.codestates.soloprojectbusinesscommunity.api.v1.dto.MemberListResponseDto;
import com.codestates.soloprojectbusinesscommunity.api.v1.dto.MemberRequestDto;
import com.codestates.soloprojectbusinesscommunity.api.v1.dto.MemberResponseDto;
import com.codestates.soloprojectbusinesscommunity.api.v1.dto.MemberUpdateDto;
import com.codestates.soloprojectbusinesscommunity.api.v1.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberResponseDto createMember(MemberRequestDto memberRequestDto) {
        Member savedMember = memberRepository.save(memberRequestDto.toEntity());
        return new MemberResponseDto(savedMember);
    }

    public MemberResponseDto findMember(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new RuntimeException("존재하지 않는 회원입니다."));
        return new MemberResponseDto(member);
    }

    public MemberListResponseDto findMembers(String companyType, String companyLocation, PageRequest pageRequest) {
        Page<Member> memberPage;

        if (!companyLocation.isEmpty() && !companyType.isEmpty()) {
            memberPage = memberRepository
                    .findByCompanyTypeAndCompanyLocation(companyType, companyLocation, pageRequest);
        } else if (!companyLocation.isEmpty()) {
            memberPage = memberRepository
                    .findByCompanyLocation(companyLocation, pageRequest);
        } else if (!companyType.isEmpty()) {
            memberPage = memberRepository
                    .findByCompanyType(companyType, pageRequest);
        }
        else {
            memberPage = memberRepository.findAll(pageRequest);
        }

        List<Member> memberList = memberPage.getContent();
        return new MemberListResponseDto(memberList, memberPage);
    }


    public MemberResponseDto updateMember(Long memberId, MemberUpdateDto memberUpdateDto) {
        String company_name = memberUpdateDto.getCompanyName();
        String company_type = memberUpdateDto.getCompanyType();
        String company_location = memberUpdateDto.getCompanyLocation();

        Member member = memberRepository.findById(memberId).orElseThrow(() -> new RuntimeException("존재하지 않는 회원입니다."));
        member.update(company_name, company_type, company_location);

        if (!memberUpdateDto.getPassword().isEmpty() && !memberUpdateDto.getPassword().equals(member.getPassword())) {
            member.changePassword(memberUpdateDto.getPassword());
        }

        Member updatedMember = memberRepository.save(member);
        return new MemberResponseDto(updatedMember);
    }

    public void deleteMember(Long memberId) {
        memberRepository.deleteById(memberId);
    }
}
