package com.codestates.soloprojectbusinesscommunity.api.v1.service;

import com.codestates.soloprojectbusinesscommunity.api.v1.domain.Member;
import com.codestates.soloprojectbusinesscommunity.api.v1.dto.MemberResponseDto;
import com.codestates.soloprojectbusinesscommunity.api.v1.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberResponseDto findById(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new RuntimeException("존재하지 않는 회원입니다."));
        return new MemberResponseDto(member);
    }

//    public List<Member> findMembers() {
//        return
//    }
}
