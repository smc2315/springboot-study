package com.example.demo.member.controller;

import com.example.demo.ApiDocument;
import com.example.demo.member.controller.dto.MemberRequest;
import com.example.demo.member.controller.dto.MemberResponse;
import com.example.demo.member.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.BDDMockito.willThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MemberController.class)
public class MemberControllerTest extends ApiDocument {

    private static final String SUCCESS_MESSAGE = "success";
    private static final String FAIL_MESSAGE = "fail";

    private MemberRequest memberRequest;
    private MemberResponse successResponse;
    private MemberResponse failResponse;

    @MockBean
    private MemberService memberService;

    @BeforeEach
    void setUp() {
        memberRequest = MemberRequest.builder()
                .name("최용태")
                .age(27)
                .build();
        successResponse = MemberResponse.builder()
                .message(SUCCESS_MESSAGE)
                .build();
        failResponse = MemberResponse.builder()
                .message(FAIL_MESSAGE)
                .build();
    }
    @Test
    public void create_member_success() throws Exception{
        //given
        willReturn(successResponse).given(memberService).create(any(MemberRequest.class));
        //when
        ResultActions resultActions = mockMvc.perform(post("/api/v1/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(memberRequest)));
        //then
        resultActions.andExpect(status().isOk())
                .andExpect(content().json(toJson(successResponse)))
                .andDo(print())
                .andDo(toDocument("create-member-success"));
     }

     @Test
     public void create_member_fail() throws Exception{
         //given
         willThrow(new IllegalArgumentException(FAIL_MESSAGE)).given(memberService).create(any(MemberRequest.class));
         //when
         ResultActions resultActions = mockMvc.perform(post("/api/v1/members")
                 .contentType(MediaType.APPLICATION_JSON)
                 .content(toJson(memberRequest)));
         //then
         resultActions.andExpect(status().isBadRequest())
                 .andExpect(content().json(toJson(failResponse)))
                 .andDo(print())
                 .andDo(toDocument("create-member-fail"));
      }
}
