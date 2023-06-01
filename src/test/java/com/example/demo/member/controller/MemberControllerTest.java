package com.example.demo.member.controller;

import com.example.demo.ApiDocument;
import com.example.demo.member.dto.MemberRequest;
import com.example.demo.member.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.willReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MemberController.class)
public class MemberControllerTest extends ApiDocument {

    private static final String SUCCESS_MESSAGE = "success";
    private static final String FAIL_MESSAGE = "fail";

    private MemberRequest memberRequest;

    @MockBean
    private MemberService memberService;

    @BeforeEach
    void setUp() {
        memberRequest = MemberRequest.builder()
                .name("최용태")
                .age(27)
                .build();
    }
    @Test
    public void create_member_success() throws Exception{
        //given
        willReturn(1L).given(memberService).create(any(MemberRequest.class));
        //when
        ResultActions resultActions = mockMvc.perform(post("/api/v1/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(memberRequest)));
        //then
        resultActions.andExpect(status().isOk())
                .andExpect(content().string("1"))
                .andDo(print())
                .andDo(toDocument("create-member-success"));
     }
}
