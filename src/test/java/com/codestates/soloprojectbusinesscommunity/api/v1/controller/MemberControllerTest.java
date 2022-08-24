package com.codestates.soloprojectbusinesscommunity.api.v1.controller;

import com.codestates.soloprojectbusinesscommunity.api.v1.domain.Member;
import com.codestates.soloprojectbusinesscommunity.api.v1.dto.*;
import com.codestates.soloprojectbusinesscommunity.api.v1.service.MemberService;
import com.codestates.soloprojectbusinesscommunity.api.v1.utils.PageInfo;
import com.google.gson.Gson;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Arrays;
import java.util.List;

import static com.codestates.soloprojectbusinesscommunity.utils.ApiDocumentUtils.getRequestPreProcessor;
import static com.codestates.soloprojectbusinesscommunity.utils.ApiDocumentUtils.getResponsePreProcessor;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MemberController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
public class MemberControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemberService memberService;

    @Autowired
    private Gson gson;

    @Test
    public void postMemberTest() throws Exception {
        //given
        MemberRequestDto requestDto = new MemberRequestDto("김코딩", "s4goodbye", "m", "프로젝트스테이츠", "005", "001");
        String content = gson.toJson(requestDto);

        MemberResponseDto responseDto = new MemberResponseDto(1L, "김코딩", "m", "프로젝트스테이츠", "005", "001");

        //Mock
        given(memberService.createMember(Mockito.any(MemberRequestDto.class))).willReturn(responseDto);

        //when
        ResultActions actions = mockMvc.perform(
                post("/api/v1/members/")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
        );

        //then
        actions
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(requestDto.getName()))
                .andExpect(jsonPath("$.sex").value(requestDto.getSex()))
                .andExpect(jsonPath("$.companyName").value(requestDto.getCompanyName()))
                .andDo(document("post-member",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        requestFields(
                                List.of(
                                        fieldWithPath("name").type(JsonFieldType.STRING).description("이름"),
                                        fieldWithPath("password").type(JsonFieldType.STRING).description("비밀번호"),
                                        fieldWithPath("sex").type(JsonFieldType.STRING).description("성별"),
                                        fieldWithPath("companyName").type(JsonFieldType.STRING).description("회사명"),
                                        fieldWithPath("companyType").type(JsonFieldType.STRING).description("회사 업종 코드"),
                                        fieldWithPath("companyLocation").type(JsonFieldType.STRING).description("회사 지역 코드")
                                )
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("id").type(JsonFieldType.NUMBER).description("회원 식별자"),
                                        fieldWithPath("name").type(JsonFieldType.STRING).description("이름"),
                                        fieldWithPath("sex").type(JsonFieldType.STRING).description("성별"),
                                        fieldWithPath("companyName").type(JsonFieldType.STRING).description("회사명"),
                                        fieldWithPath("companyType").type(JsonFieldType.STRING).description("회사 업종 코드"),
                                        fieldWithPath("companyLocation").type(JsonFieldType.STRING).description("회사 지역 코드")
                                )
                        )
                ));
    }

    @Test
    public void putMemberTest() throws Exception {
        //given
        Long memberId = 1L;
        MemberUpdateDto updateDto = new MemberUpdateDto("", "프로젝트정윤조", "006", "002");
        String content = gson.toJson(updateDto);

        MemberResponseDto responseDto = new MemberResponseDto(1L, "김코딩", "m", "프로젝트정윤조", "006", "002");

        // Mock
        given(memberService.updateMember(Mockito.anyLong(), Mockito.any(MemberUpdateDto.class))).willReturn(responseDto);

        //when
        ResultActions actions = mockMvc.perform(
                put("/api/v1/members/{member-id}", memberId)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
        );

        //then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(responseDto.getName()))
                .andExpect(jsonPath("$.companyName").value(responseDto.getCompanyName()))
                .andExpect(jsonPath("$.companyType").value(responseDto.getCompanyType()))
                .andExpect(jsonPath("$.companyLocation").value(responseDto.getCompanyLocation()))
                .andDo(document("put-member",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        pathParameters(
                                parameterWithName("member-id").description("회원 식별자")
                        ),
                        requestFields(
                                List.of(
                                        fieldWithPath("password").type(JsonFieldType.STRING).description("비밀번호 (값이 들어오면 변경)"),
                                        fieldWithPath("companyName").type(JsonFieldType.STRING).description("회사명"),
                                        fieldWithPath("companyType").type(JsonFieldType.STRING).description("회사 업종 코드"),
                                        fieldWithPath("companyLocation").type(JsonFieldType.STRING).description("회사 지역 코드")
                                )
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("id").type(JsonFieldType.NUMBER).description("회원 식별자"),
                                        fieldWithPath("name").type(JsonFieldType.STRING).description("이름"),
                                        fieldWithPath("sex").type(JsonFieldType.STRING).description("성별"),
                                        fieldWithPath("companyName").type(JsonFieldType.STRING).description("회사명"),
                                        fieldWithPath("companyType").type(JsonFieldType.STRING).description("회사 업종 코드"),
                                        fieldWithPath("companyLocation").type(JsonFieldType.STRING).description("회사 지역 코드")
                                )
                        )
                ));


    }


    @Test
    public void getMemberTest() throws Exception {
        //given
        Long memberId = 1L;

        MemberResponseDto responseDto = new MemberResponseDto(1L, "김코딩", "m", "프로젝트스테이츠", "005", "001");

        given(memberService.findMember(Mockito.anyLong())).willReturn(responseDto);

        //when

        ResultActions actions =
                mockMvc.perform(
                        get("/api/v1/members/{member-id}", memberId)
                                .accept(MediaType.APPLICATION_JSON)
                );

        //then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(memberId))
                .andExpect(jsonPath("$.name").value(responseDto.getName()))
                .andExpect(jsonPath("$.companyName").value(responseDto.getCompanyName()))
                .andDo(document("get-member",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        pathParameters(
                                parameterWithName("member-id").description("회원 식별자")
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("id").type(JsonFieldType.NUMBER).description("회원 식별자"),
                                        fieldWithPath("name").type(JsonFieldType.STRING).description("이름"),
                                        fieldWithPath("sex").type(JsonFieldType.STRING).description("성별"),
                                        fieldWithPath("companyName").type(JsonFieldType.STRING).description("회사명"),
                                        fieldWithPath("companyType").type(JsonFieldType.STRING).description("회사 업종 코드"),
                                        fieldWithPath("companyLocation").type(JsonFieldType.STRING).description("회사 지역 코드")
                                )
                        )
                ));
    }

    @Test
    public void getMembersTest() throws Exception {
        //given
        Page<Member> memberPage = new PageImpl<>(
                List.of(new Member("김코딩", "password", "m", "프로젝트스테이츠", "005", "001"),
                        new Member("박코드", "password", "m", "프로젝트스테이츠", "001", "005"),
                        new Member("정해커", "password", "m", "프로젝트스테이츠", "001", "001"),
                        new Member("이버그", "password", "m", "프로젝트스테이츠", "005", "005"),
                        new Member("최개발", "password", "m", "프로젝트스테이츠", "003", "003")
                ),
                PageRequest.of(0, 10), 5);

        PageInfo pageInfo = new PageInfo(memberPage);

        MemberResponseDto memberResponseDto1 = new MemberResponseDto(
                1L,"김코딩", "m", "프로젝트스테이츠", "005", "001");
        MemberResponseDto memberResponseDto2 = new MemberResponseDto(
                2L, "박코드", "m", "프로젝트스테이츠", "001", "005");
        MemberResponseDto memberResponseDto3 = new MemberResponseDto(
                3L, "정해커", "m", "프로젝트스테이츠", "001", "001");
        MemberResponseDto memberResponseDto4 = new MemberResponseDto(
                4L, "이버그",  "m", "프로젝트스테이츠", "005", "005");
        MemberResponseDto memberResponseDto5 = new MemberResponseDto(
                5L, "최개발",  "m", "프로젝트스테이츠", "003", "003");

        MemberListResponseDto listResponseDto = new MemberListResponseDto(
                List.of(memberResponseDto1, memberResponseDto2, memberResponseDto3, memberResponseDto4, memberResponseDto5), pageInfo);

        //Mock
        given(memberService.findMembers(Mockito.anyString(), Mockito.anyString(), Mockito.any(PageRequest.class))).willReturn(listResponseDto);

        //when
        ResultActions actions = mockMvc.perform(
                get("/api/v1/members")
                        .param("page", "1")
                        .param("size", "10")
                        .accept(MediaType.APPLICATION_JSON)
        );

        //then
        MvcResult result =
                actions
                        .andExpect(status().isOk())
                        .andDo(document("get-members",
                                        getRequestPreProcessor(),
                                        getResponsePreProcessor(),
                                        requestParameters(
                                                List.of(
                                                        parameterWithName("page").description("Page 번호 (기본값 : 1)"),
                                                        parameterWithName("size").description("Page 크기 (기본값 : 10)")
                                                )
                                        ),
                                        responseFields(
                                                Arrays.asList(
                                                        fieldWithPath("memberList").type(JsonFieldType.ARRAY).description("회원").optional(),
                                                        fieldWithPath("memberList[].id").type(JsonFieldType.NUMBER).description("회원 식별자"),
                                                        fieldWithPath("memberList[].name").type(JsonFieldType.STRING).description("이름"),
                                                        fieldWithPath("memberList[].sex").type(JsonFieldType.STRING).description("성별"),
                                                        fieldWithPath("memberList[].companyName").type(JsonFieldType.STRING).description("회사명"),
                                                        fieldWithPath("memberList[].companyType").type(JsonFieldType.STRING).description("회사 업종 코드"),
                                                        fieldWithPath("memberList[].companyLocation").type(JsonFieldType.STRING).description("회사 지역 코드"),
                                                        fieldWithPath("pageInfo.page").type(JsonFieldType.NUMBER).description("페이지 번호"),
                                                        fieldWithPath("pageInfo.size").type(JsonFieldType.NUMBER).description("페이지 크기"),
                                                        fieldWithPath("pageInfo.totalElements").type(JsonFieldType.NUMBER).description("전체 건 수"),
                                                        fieldWithPath("pageInfo.totalPages").type(JsonFieldType.NUMBER).description("전체 페이지 수")
                                                )
                                        )
                                )
                        )
                        .andReturn();

        List list = JsonPath.parse(result.getResponse().getContentAsString()).read("$.memberList");
        assertThat(list.size(), is(5));
    }

    @Test
    public void getMembersWithConditionTest() throws Exception {
        //given
        Page<Member> memberPage = new PageImpl<>(
                List.of(new Member("김코딩", "password", "m", "프로젝트스테이츠", "005", "001")),
                PageRequest.of(0, 10), 1);

        PageInfo pageInfo = new PageInfo(memberPage);

        MemberResponseDto memberResponseDto1 = new MemberResponseDto(
                1L,"김코딩", "m", "프로젝트스테이츠", "005", "001");

        MemberListResponseDto listResponseDto = new MemberListResponseDto(List.of(memberResponseDto1), pageInfo);

        //Mock
        given(memberService.findMembers(Mockito.anyString(), Mockito.anyString(), Mockito.any(PageRequest.class))).willReturn(listResponseDto);

        //when
        ResultActions actions = mockMvc.perform(
                get("/api/v1/members")
                        .param("page", "1")
                        .param("size", "10")
                        .param("companyType", "005")
                        .param("companyLocation", "001")
                        .accept(MediaType.APPLICATION_JSON)
        );

        //then
        MvcResult result =
                actions
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.memberList[0].companyType").value(memberResponseDto1.getCompanyType()))
                        .andExpect(jsonPath("$.memberList[0].companyLocation").value(memberResponseDto1.getCompanyLocation()))
                        .andDo(document("get-members-with-condition",
                                        getRequestPreProcessor(),
                                        getResponsePreProcessor(),
                                        requestParameters(
                                                List.of(
                                                        parameterWithName("page").description("Page 번호 (기본값 : 1)"),
                                                        parameterWithName("size").description("Page 크기 (기본값 : 10)"),
                                                        parameterWithName("companyType").description("회사 업종 코드"),
                                                        parameterWithName("companyLocation").description("회사 지역 코드")
                                                )
                                        ),
                                        responseFields(
                                                Arrays.asList(
                                                        fieldWithPath("memberList").type(JsonFieldType.ARRAY).description("회원").optional(),
                                                        fieldWithPath("memberList[].id").type(JsonFieldType.NUMBER).description("회원 식별자"),
                                                        fieldWithPath("memberList[].name").type(JsonFieldType.STRING).description("이름"),
                                                        fieldWithPath("memberList[].sex").type(JsonFieldType.STRING).description("성별"),
                                                        fieldWithPath("memberList[].companyName").type(JsonFieldType.STRING).description("회사명"),
                                                        fieldWithPath("memberList[].companyType").type(JsonFieldType.STRING).description("회사 업종 코드"),
                                                        fieldWithPath("memberList[].companyLocation").type(JsonFieldType.STRING).description("회사 지역 코드"),
                                                        fieldWithPath("pageInfo.page").type(JsonFieldType.NUMBER).description("페이지 번호"),
                                                        fieldWithPath("pageInfo.size").type(JsonFieldType.NUMBER).description("페이지 크기"),
                                                        fieldWithPath("pageInfo.totalElements").type(JsonFieldType.NUMBER).description("전체 건 수"),
                                                        fieldWithPath("pageInfo.totalPages").type(JsonFieldType.NUMBER).description("전체 페이지 수")
                                                )
                                        )
                                )
                        )
                        .andReturn();

        List list = JsonPath.parse(result.getResponse().getContentAsString()).read("$.memberList");
        assertThat(list.size(), is(1));
    }

    @Test
    public void deleteMemberTest() throws Exception {
        //given
        long memberId = 1L;

        doNothing().when(memberService).deleteMember(Mockito.anyLong());

        //when
        ResultActions actions = mockMvc.perform(
                delete("/api/v1/members/{member-id}", memberId)
        );

        //then
        actions
                .andExpect(status().isNoContent())
                .andDo(document("delete-member",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        pathParameters(
                                parameterWithName("member-id").description("회원 식별자")
                        )
                ));
    }
}
