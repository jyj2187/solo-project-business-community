package com.codestates.soloprojectbusinesscommunity.api.v1.controller;

import com.codestates.soloprojectbusinesscommunity.api.v1.dto.MemberListResponseDto;
import com.codestates.soloprojectbusinesscommunity.api.v1.dto.MemberRequestDto;
import com.codestates.soloprojectbusinesscommunity.api.v1.dto.MemberResponseDto;
import com.codestates.soloprojectbusinesscommunity.api.v1.dto.MemberUpdateDto;
import com.codestates.soloprojectbusinesscommunity.api.v1.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
public class MemberController {
    private final MemberService memberService;

    //C
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MemberResponseDto postMember(@RequestBody @Valid MemberRequestDto memberRequestDto) {
        return memberService.createMember(memberRequestDto);
    }

    //R
    @GetMapping("/{memberId}")
    public MemberResponseDto getMember(@PathVariable Long memberId) {
        return memberService.findMember(memberId);
    }

    @GetMapping
    public MemberListResponseDto getMembers(@RequestParam(value = "page", defaultValue = "1") int page,
                                            @RequestParam(value = "size", defaultValue = "10") int size,
                                            @RequestParam(value = "companyType", defaultValue = "") String companyType,
                                            @RequestParam(value = "companyLocation", defaultValue = "") String companyLocation) {
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        return memberService.findMembers(companyType, companyLocation, pageRequest);
    }


    //U
    @PutMapping("/{memberId}")
    public MemberResponseDto putMember(@PathVariable Long memberId,
                                       @RequestBody @Valid MemberUpdateDto memberUpdateDto) {
        return memberService.updateMember(memberId, memberUpdateDto);
    }

    //D
    @DeleteMapping("/{memberId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMember(@PathVariable Long memberId) {
        memberService.deleteMember(memberId);
    }

}
