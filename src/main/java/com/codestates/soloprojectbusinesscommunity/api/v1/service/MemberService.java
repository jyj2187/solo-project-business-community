package com.codestates.soloprojectbusinesscommunity.api.v1.service;

import com.codestates.soloprojectbusinesscommunity.api.v1.domain.Member;
import com.codestates.soloprojectbusinesscommunity.api.v1.dto.MemberRequestDto;
import com.codestates.soloprojectbusinesscommunity.api.v1.dto.MemberResponseDto;
import com.codestates.soloprojectbusinesscommunity.api.v1.dto.MemberUpdateDto;
import com.codestates.soloprojectbusinesscommunity.api.v1.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

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

//    public List<MemberResponseDto> findMembers(int page, int size) {
//        page--;
//        return memberRepository.findAll().stream()
//                .map(MemberResponseDto::new)
//                .collect(Collectors.toList());
//    }

    public Page<MemberResponseDto> findMembers(String companyType, String companyLocation, PageRequest pageRequest) {
        List<MemberResponseDto> memberResponseDtoList;

        if (!companyLocation.isEmpty() && !companyType.isEmpty()) {
            memberResponseDtoList = memberRepository
                    .findByCompanyTypeAndCompanyLocation(companyType, companyLocation).stream()
                    .map(MemberResponseDto::new)
                    .collect(Collectors.toList());

        } else if (!companyLocation.isEmpty()) {
            memberResponseDtoList = memberRepository
                    .findByCompanyLocation(companyLocation).stream()
                    .map(MemberResponseDto::new)
                    .collect(Collectors.toList());

        } else if (!companyType.isEmpty()) {
            memberResponseDtoList = memberRepository
                    .findByCompanyType(companyType).stream()
                    .map(MemberResponseDto::new)
                    .collect(Collectors.toList());

        } else {
            memberResponseDtoList = memberRepository.findAll(pageRequest).stream()
                    .map(MemberResponseDto::new)
                    .collect(Collectors.toList());
        }

        long total = memberRepository.count();
        return new PageImpl<>(memberResponseDtoList, pageRequest, total);
    }


    public MemberResponseDto updateMember(Long memberId, @Valid MemberUpdateDto memberUpdateDto) {
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
