package com.example.demo.post.controller;

import com.example.demo.ApiDocument;
import com.example.demo.global.error.ErrorResponse;
import com.example.demo.global.error.exception.ErrorCode;
import com.example.demo.post.controller.dto.PostRequest;
import com.example.demo.post.service.PostService;
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

@WebMvcTest(PostController.class)
class PostControllerTest extends ApiDocument {

    private static final String SUCCESS_MESSAGE = "success";
    private static final String FAIL_MESSAGE = "fail";

    private PostRequest postRequest;
    private ErrorResponse failResponse;

    @MockBean
    private PostService postService;

    @BeforeEach
    void setUp() {
        postRequest = PostRequest.builder()
                .title("테스트")
                .content("테스트내용")
                .writer("최용태")
                .build();
        failResponse = ErrorResponse.of(ErrorCode.INTERNAL_SERVER_ERROR);
    }
    @Test
    public void create_post_success() throws Exception{
        //given
        willReturn(1L).given(postService).create(any(PostRequest.class));
        //when
        ResultActions resultActions = mockMvc.perform(post("/api/v1/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(postRequest)));
        //then
        resultActions.andExpect(status().isOk())
                .andExpect(content().json(toJson(1)))
                .andDo(print())
                .andDo(toDocument("create-post-success"));
    }

    @Test
    public void create_post_fail() throws Exception{
        //given
        willThrow(new IllegalArgumentException(FAIL_MESSAGE)).given(postService).create(any(PostRequest.class));
        //when
        ResultActions resultActions = mockMvc.perform(post("/api/v1/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(postRequest)));
        //then
        resultActions.andExpect(status().isBadRequest())
                .andExpect(content().json(toJson(failResponse)))
                .andDo(print())
                .andDo(toDocument("create-post-fail"));
    }

}