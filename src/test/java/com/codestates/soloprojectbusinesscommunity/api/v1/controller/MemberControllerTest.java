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
        MemberRequestDto requestDto = new MemberRequestDto("?????????", "s4goodbye", "m", "????????????????????????", "005", "001");
        String content = gson.toJson(requestDto);

        MemberResponseDto responseDto = new MemberResponseDto(1L, "?????????", "m", "????????????????????????", "005", "001");

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
                                        fieldWithPath("name").type(JsonFieldType.STRING).description("??????"),
                                        fieldWithPath("password").type(JsonFieldType.STRING).description("????????????"),
                                        fieldWithPath("sex").type(JsonFieldType.STRING).description("??????"),
                                        fieldWithPath("companyName").type(JsonFieldType.STRING).description("?????????"),
                                        fieldWithPath("companyType").type(JsonFieldType.STRING).description("?????? ?????? ??????"),
                                        fieldWithPath("companyLocation").type(JsonFieldType.STRING).description("?????? ?????? ??????")
                                )
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("id").type(JsonFieldType.NUMBER).description("?????? ?????????"),
                                        fieldWithPath("name").type(JsonFieldType.STRING).description("??????"),
                                        fieldWithPath("sex").type(JsonFieldType.STRING).description("??????"),
                                        fieldWithPath("companyName").type(JsonFieldType.STRING).description("?????????"),
                                        fieldWithPath("companyType").type(JsonFieldType.STRING).description("?????? ?????? ??????"),
                                        fieldWithPath("companyLocation").type(JsonFieldType.STRING).description("?????? ?????? ??????")
                                )
                        )
                ));
    }

    @Test
    public void putMemberTest() throws Exception {
        //given
        Long memberId = 1L;
        MemberUpdateDto updateDto = new MemberUpdateDto("", "?????????????????????", "006", "002");
        String content = gson.toJson(updateDto);

        MemberResponseDto responseDto = new MemberResponseDto(1L, "?????????", "m", "?????????????????????", "006", "002");

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
                                parameterWithName("member-id").description("?????? ?????????")
                        ),
                        requestFields(
                                List.of(
                                        fieldWithPath("password").type(JsonFieldType.STRING).description("???????????? (?????? ???????????? ??????)"),
                                        fieldWithPath("companyName").type(JsonFieldType.STRING).description("?????????"),
                                        fieldWithPath("companyType").type(JsonFieldType.STRING).description("?????? ?????? ??????"),
                                        fieldWithPath("companyLocation").type(JsonFieldType.STRING).description("?????? ?????? ??????")
                                )
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("id").type(JsonFieldType.NUMBER).description("?????? ?????????"),
                                        fieldWithPath("name").type(JsonFieldType.STRING).description("??????"),
                                        fieldWithPath("sex").type(JsonFieldType.STRING).description("??????"),
                                        fieldWithPath("companyName").type(JsonFieldType.STRING).description("?????????"),
                                        fieldWithPath("companyType").type(JsonFieldType.STRING).description("?????? ?????? ??????"),
                                        fieldWithPath("companyLocation").type(JsonFieldType.STRING).description("?????? ?????? ??????")
                                )
                        )
                ));


    }


    @Test
    public void getMemberTest() throws Exception {
        //given
        Long memberId = 1L;

        MemberResponseDto responseDto = new MemberResponseDto(1L, "?????????", "m", "????????????????????????", "005", "001");

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
                                parameterWithName("member-id").description("?????? ?????????")
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("id").type(JsonFieldType.NUMBER).description("?????? ?????????"),
                                        fieldWithPath("name").type(JsonFieldType.STRING).description("??????"),
                                        fieldWithPath("sex").type(JsonFieldType.STRING).description("??????"),
                                        fieldWithPath("companyName").type(JsonFieldType.STRING).description("?????????"),
                                        fieldWithPath("companyType").type(JsonFieldType.STRING).description("?????? ?????? ??????"),
                                        fieldWithPath("companyLocation").type(JsonFieldType.STRING).description("?????? ?????? ??????")
                                )
                        )
                ));
    }

    @Test
    public void getMembersTest() throws Exception {
        //given
        Page<Member> memberPage = new PageImpl<>(
                List.of(new Member("?????????", "password", "m", "????????????????????????", "005", "001"),
                        new Member("?????????", "password", "m", "????????????????????????", "001", "005"),
                        new Member("?????????", "password", "m", "????????????????????????", "001", "001"),
                        new Member("?????????", "password", "m", "????????????????????????", "005", "005"),
                        new Member("?????????", "password", "m", "????????????????????????", "003", "003")
                ),
                PageRequest.of(0, 10), 5);

        PageInfo pageInfo = new PageInfo(memberPage);

        MemberResponseDto memberResponseDto1 = new MemberResponseDto(
                1L,"?????????", "m", "????????????????????????", "005", "001");
        MemberResponseDto memberResponseDto2 = new MemberResponseDto(
                2L, "?????????", "m", "????????????????????????", "001", "005");
        MemberResponseDto memberResponseDto3 = new MemberResponseDto(
                3L, "?????????", "m", "????????????????????????", "001", "001");
        MemberResponseDto memberResponseDto4 = new MemberResponseDto(
                4L, "?????????",  "m", "????????????????????????", "005", "005");
        MemberResponseDto memberResponseDto5 = new MemberResponseDto(
                5L, "?????????",  "m", "????????????????????????", "003", "003");

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
                                                        parameterWithName("page").description("Page ?????? (????????? : 1)"),
                                                        parameterWithName("size").description("Page ?????? (????????? : 10)")
                                                )
                                        ),
                                        responseFields(
                                                Arrays.asList(
                                                        fieldWithPath("memberList").type(JsonFieldType.ARRAY).description("??????").optional(),
                                                        fieldWithPath("memberList[].id").type(JsonFieldType.NUMBER).description("?????? ?????????"),
                                                        fieldWithPath("memberList[].name").type(JsonFieldType.STRING).description("??????"),
                                                        fieldWithPath("memberList[].sex").type(JsonFieldType.STRING).description("??????"),
                                                        fieldWithPath("memberList[].companyName").type(JsonFieldType.STRING).description("?????????"),
                                                        fieldWithPath("memberList[].companyType").type(JsonFieldType.STRING).description("?????? ?????? ??????"),
                                                        fieldWithPath("memberList[].companyLocation").type(JsonFieldType.STRING).description("?????? ?????? ??????"),
                                                        fieldWithPath("pageInfo.page").type(JsonFieldType.NUMBER).description("????????? ??????"),
                                                        fieldWithPath("pageInfo.size").type(JsonFieldType.NUMBER).description("????????? ??????"),
                                                        fieldWithPath("pageInfo.totalElements").type(JsonFieldType.NUMBER).description("?????? ??? ???"),
                                                        fieldWithPath("pageInfo.totalPages").type(JsonFieldType.NUMBER).description("?????? ????????? ???")
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
                List.of(new Member("?????????", "password", "m", "????????????????????????", "005", "001")),
                PageRequest.of(0, 10), 1);

        PageInfo pageInfo = new PageInfo(memberPage);

        MemberResponseDto memberResponseDto1 = new MemberResponseDto(
                1L,"?????????", "m", "????????????????????????", "005", "001");

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
                                                        parameterWithName("page").description("Page ?????? (????????? : 1)"),
                                                        parameterWithName("size").description("Page ?????? (????????? : 10)"),
                                                        parameterWithName("companyType").description("?????? ?????? ??????"),
                                                        parameterWithName("companyLocation").description("?????? ?????? ??????")
                                                )
                                        ),
                                        responseFields(
                                                Arrays.asList(
                                                        fieldWithPath("memberList").type(JsonFieldType.ARRAY).description("??????").optional(),
                                                        fieldWithPath("memberList[].id").type(JsonFieldType.NUMBER).description("?????? ?????????"),
                                                        fieldWithPath("memberList[].name").type(JsonFieldType.STRING).description("??????"),
                                                        fieldWithPath("memberList[].sex").type(JsonFieldType.STRING).description("??????"),
                                                        fieldWithPath("memberList[].companyName").type(JsonFieldType.STRING).description("?????????"),
                                                        fieldWithPath("memberList[].companyType").type(JsonFieldType.STRING).description("?????? ?????? ??????"),
                                                        fieldWithPath("memberList[].companyLocation").type(JsonFieldType.STRING).description("?????? ?????? ??????"),
                                                        fieldWithPath("pageInfo.page").type(JsonFieldType.NUMBER).description("????????? ??????"),
                                                        fieldWithPath("pageInfo.size").type(JsonFieldType.NUMBER).description("????????? ??????"),
                                                        fieldWithPath("pageInfo.totalElements").type(JsonFieldType.NUMBER).description("?????? ??? ???"),
                                                        fieldWithPath("pageInfo.totalPages").type(JsonFieldType.NUMBER).description("?????? ????????? ???")
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
                                parameterWithName("member-id").description("?????? ?????????")
                        )
                ));
    }
}
