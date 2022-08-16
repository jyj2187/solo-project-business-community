package com.codestates.soloprojectbusinesscommunity.api.v1.controller;

import com.codestates.soloprojectbusinesscommunity.api.v1.dto.MemberResponseDto;
import com.codestates.soloprojectbusinesscommunity.api.v1.service.MemberService;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static com.codestates.soloprojectbusinesscommunity.utils.ApiDocumentUtils.getRequestPreProcessor;
import static com.codestates.soloprojectbusinesscommunity.utils.ApiDocumentUtils.getResponsePreProcessor;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;

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
    public void findMemberTest() throws Exception {
        //given
        Long memberId = 1L;

        MemberResponseDto responseDto = new MemberResponseDto(1L, "김코딩", "m", "프로젝트스테이츠", "005", "001");

        given(memberService.findById(Mockito.anyLong())).willReturn(responseDto);

        //when

        ResultActions actions =
                mockMvc.perform(
                        get("/api/v1/members/{memberId}", memberId)
                                .accept(MediaType.APPLICATION_JSON)
                );

        //then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(memberId))
                .andExpect(jsonPath("$.name").value(responseDto.getName()))
                .andExpect(jsonPath("$.company_name").value(responseDto.getCompany_name()))
                .andDo(document("get-member",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        pathParameters(
                                parameterWithName("memberId").description("회원 식별자")
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("id").type(JsonFieldType.NUMBER).description("회원 식별자"),
                                        fieldWithPath("name").type(JsonFieldType.STRING).description("이름"),
                                        fieldWithPath("sex").type(JsonFieldType.STRING).description("성별"),
                                        fieldWithPath("company_name").type(JsonFieldType.STRING).description("회사 이름"),
                                        fieldWithPath("company_type").type(JsonFieldType.STRING).description("회사 업종"),
                                        fieldWithPath("company_location").type(JsonFieldType.STRING).description("회사 지역")
                                )
                        )
                ));


    }
}
