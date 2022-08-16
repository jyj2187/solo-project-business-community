package com.codestates.soloprojectbusinesscommunity.api.v1.controller;

import com.codestates.soloprojectbusinesscommunity.api.v1.dto.MemberResponseDto;
import com.codestates.soloprojectbusinesscommunity.api.v1.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
public class MemberController {
    private final MemberService memberService;

    //C

    //R
    @GetMapping("/{memberId}")
    public MemberResponseDto findMember(@PathVariable Long memberId) {
        return memberService.findById(memberId);
    }


    //U

    //D

}
