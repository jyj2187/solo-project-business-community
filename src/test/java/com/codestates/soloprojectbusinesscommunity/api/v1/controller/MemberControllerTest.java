package com.codestates.soloprojectbusinesscommunity.api.v1.controller;

import com.codestates.soloprojectbusinesscommunity.api.v1.dto.MemberRequestDto;
import com.codestates.soloprojectbusinesscommunity.api.v1.dto.MemberResponseDto;
import com.codestates.soloprojectbusinesscommunity.api.v1.dto.MemberUpdateDto;
import com.codestates.soloprojectbusinesscommunity.api.v1.service.MemberService;
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

        MemberResponseDto responseDto1 = new MemberResponseDto(
                1L, "김코딩", "m", "프로젝트스테이츠", "005", "001");
        MemberResponseDto responseDto2 = new MemberResponseDto(
                2L, "박코드", "m", "프로젝트스테이츠", "001", "005");
        MemberResponseDto responseDto3 = new MemberResponseDto(
                3L, "정해커", "m", "프로젝트스테이츠", "001", "001");
        MemberResponseDto responseDto4 = new MemberResponseDto(
                4L, "이버그", "m", "프로젝트스테이츠", "005", "005");
        MemberResponseDto responseDto5 = new MemberResponseDto(
                5L, "최개발", "m", "프로젝트스테이츠", "003", "003");

        Page<MemberResponseDto> responseDtoPage = new PageImpl<>(
                List.of(responseDto1, responseDto2, responseDto3, responseDto4, responseDto5));

        //Mock
        given(memberService.findMembers(Mockito.anyString(), Mockito.anyString(), Mockito.any(PageRequest.class))).willReturn(responseDtoPage);

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
                                                        fieldWithPath("content").type(JsonFieldType.ARRAY).description("회원").optional(),
                                                        fieldWithPath("content[].id").type(JsonFieldType.NUMBER).description("회원 식별자"),
                                                        fieldWithPath("content[].name").type(JsonFieldType.STRING).description("이름"),
                                                        fieldWithPath("content[].sex").type(JsonFieldType.STRING).description("성별"),
                                                        fieldWithPath("content[].companyName").type(JsonFieldType.STRING).description("회사명"),
                                                        fieldWithPath("content[].companyType").type(JsonFieldType.STRING).description("회사 업종 코드"),
                                                        fieldWithPath("content[].companyLocation").type(JsonFieldType.STRING).description("회사 지역 코드"),
                                                        fieldWithPath("number").type(JsonFieldType.NUMBER).description("페이지 번호 - 1 ('0'이 첫번째 페이지)"),
                                                        fieldWithPath("size").type(JsonFieldType.NUMBER).description("페이지 크기"),
                                                        fieldWithPath("numberOfElements").type(JsonFieldType.NUMBER).description("페이지 내 건 수"),
                                                        fieldWithPath("totalElements").type(JsonFieldType.NUMBER).description("전체 건 수"),
                                                        fieldWithPath("totalPages").type(JsonFieldType.NUMBER).description("전체 페이지 수"),
                                                        fieldWithPath("pageable").ignored(),
                                                        fieldWithPath("last").ignored(),
                                                        fieldWithPath("sort.*").ignored(),
                                                        fieldWithPath("first").ignored(),
                                                        fieldWithPath("empty").ignored()
                                                )
                                        )
                                )
                        )
                        .andReturn();

        List list = JsonPath.parse(result.getResponse().getContentAsString()).read("$.content");
        assertThat(list.size(), is(5));
    }

    @Test
    public void getMembersWithConditionTest() throws Exception {
        //given
        MemberResponseDto responseDto1 = new MemberResponseDto(
                1L, "김코딩", "m", "프로젝트스테이츠", "005", "001");
        MemberResponseDto responseDto2 = new MemberResponseDto(
                2L, "박코드", "m", "프로젝트스테이츠", "001", "005");
        MemberResponseDto responseDto3 = new MemberResponseDto(
                3L, "정해커", "m", "프로젝트스테이츠", "001", "001");
        MemberResponseDto responseDto4 = new MemberResponseDto(
                4L, "이버그", "m", "프로젝트스테이츠", "005", "005");
        MemberResponseDto responseDto5 = new MemberResponseDto(
                5L, "최개발", "m", "프로젝트스테이츠", "003", "003");

        Page<MemberResponseDto> responseDtoPage = new PageImpl<>(
                List.of(responseDto1));

        //Mock
        given(memberService.findMembers(Mockito.anyString(), Mockito.anyString(), Mockito.any(PageRequest.class))).willReturn(responseDtoPage);

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
                        .andExpect(jsonPath("$.content[0].companyType").value(responseDto1.getCompanyType()))
                        .andExpect(jsonPath("$.content[0].companyLocation").value(responseDto1.getCompanyLocation()))
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
                                                        fieldWithPath("content").type(JsonFieldType.ARRAY).description("회원").optional(),
                                                        fieldWithPath("content[].id").type(JsonFieldType.NUMBER).description("회원 식별자"),
                                                        fieldWithPath("content[].name").type(JsonFieldType.STRING).description("이름"),
                                                        fieldWithPath("content[].sex").type(JsonFieldType.STRING).description("성별"),
                                                        fieldWithPath("content[].companyName").type(JsonFieldType.STRING).description("회사명"),
                                                        fieldWithPath("content[].companyType").type(JsonFieldType.STRING).description("회사 업종 코드"),
                                                        fieldWithPath("content[].companyLocation").type(JsonFieldType.STRING).description("회사 지역 코드"),
                                                        fieldWithPath("number").type(JsonFieldType.NUMBER).description("페이지 번호 - 1 ('0'이 첫번째 페이지)"),
                                                        fieldWithPath("size").type(JsonFieldType.NUMBER).description("페이지 크기"),
                                                        fieldWithPath("numberOfElements").type(JsonFieldType.NUMBER).description("페이지 내 건 수"),
                                                        fieldWithPath("totalElements").type(JsonFieldType.NUMBER).description("전체 건 수"),
                                                        fieldWithPath("totalPages").type(JsonFieldType.NUMBER).description("전체 페이지 수"),
                                                        fieldWithPath("pageable").ignored(),
                                                        fieldWithPath("last").ignored(),
                                                        fieldWithPath("sort.*").ignored(),
                                                        fieldWithPath("first").ignored(),
                                                        fieldWithPath("empty").ignored()
                                                )
                                        )
                                )
                        )
                        .andReturn();

        List list = JsonPath.parse(result.getResponse().getContentAsString()).read("$.content");
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
