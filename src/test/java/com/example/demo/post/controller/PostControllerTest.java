package com.example.demo.post.controller;

import com.example.demo.ApiDocument;
import com.example.demo.global.error.ErrorResponse;
import com.example.demo.global.error.exception.ErrorCode;
import com.example.demo.global.error.exception.NotFoundException;
import com.example.demo.post.controller.dto.PostRequest;
import com.example.demo.post.controller.dto.PostResponse;
import com.example.demo.post.controller.dto.PostUpdateRequest;
import com.example.demo.post.controller.dto.PostsResponse;
import com.example.demo.post.domain.Post;
import com.example.demo.post.service.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PostController.class)
class PostControllerTest extends ApiDocument {

    private static final String SUCCESS_MESSAGE = "success";
    private static final String FAIL_MESSAGE = "fail";

    private PostRequest postRequest;
    private PostResponse postResponse;
    private PostUpdateRequest postUpdateRequest;
    private PostsResponse postResponses;
    private ErrorResponse failResponse;

    @MockBean
    private PostService postService;

    @BeforeEach
    void setUp() {
        Post post = Post.builder()
                .title("테스트")
                .content("테스트내용")
                .writer("최용태")
                .build();
        postRequest = PostRequest.builder()
                .title("테스트")
                .content("테스트내용")
                .writer("최용태")
                .build();
        postUpdateRequest = PostUpdateRequest.builder()
                .title("테스트2")
                .content("테스트내용2")
                .build();
        postResponse = PostResponse.builder()
                .post(post)
                .build();
        List<PostResponse> postResponseList = LongStream.rangeClosed(1, 5)
                .mapToObj(n -> PostResponse.builder()
                        .post(Post.builder()
                                .title("테스트" + n)
                                .content("테스트내용" + n)
                                .writer("최용태" + n)
                                .build())
                        .build())
                .collect(Collectors.toList());
        postResponses = PostsResponse.builder()
                .postResponses(postResponseList)
                .build();
        failResponse = ErrorResponse.of(ErrorCode.INTERNAL_SERVER_ERROR);
    }

    @Test
    public void create_post_success() throws Exception {
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
    public void create_post_fail() throws Exception {
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

    @Test
    public void get_post_success() throws Exception {
        //given
        willReturn(postResponse).given(postService).getPost(anyLong());
        //when
        ResultActions resultActions = mockMvc.perform(get("/api/v1/posts/1"));
        //then
        resultActions.andExpect(status().isOk())
                .andExpect(content().json(toJson(postResponse)))
                .andDo(print())
                .andDo(toDocument("get-post-success"));
    }

    @Test
    public void get_post_fail() throws Exception {
        //given
        willThrow(new IllegalArgumentException(FAIL_MESSAGE)).given(postService).getPost(anyLong());
        //when
        ResultActions resultActions = mockMvc.perform(get("/api/v1/posts/1"));
        //then
        resultActions.andExpect(status().isBadRequest())
                .andExpect(content().json(toJson(failResponse)))
                .andDo(print())
                .andDo(toDocument("get-post-fail"));
    }

    @Test
    public void get_posts_success() throws Exception {
        //given
        willReturn(postResponses).given(postService).getPosts();
        //when
        ResultActions resultActions = mockMvc.perform(get("/api/v1/posts/"));
        //then
        resultActions.andExpect(status().isOk())
                .andExpect(content().json(toJson(postResponses)))
                .andDo(print())
                .andDo(toDocument("get-posts-success"));
    }

    @Test
    public void get_posts_fail() throws Exception {
        //given
        willThrow(new IllegalArgumentException(FAIL_MESSAGE)).given(postService).getPosts();
        //when
        ResultActions resultActions = mockMvc.perform(get("/api/v1/posts/"));

        //then
        resultActions.andExpect(status().isBadRequest())
                .andExpect(content().json(toJson(failResponse)))
                .andDo(print())
                .andDo(toDocument("get-posts-fail"));
    }

    @Test
    public void update_post_success() throws Exception {
        //given
        willReturn(1L).given(postService).update(anyLong(), any(PostUpdateRequest.class));
        //when
        ResultActions resultActions = mockMvc.perform(post("/api/v1/posts/update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(postUpdateRequest)));
        //then
        resultActions.andExpect(status().isOk())
                .andExpect(content().json(toJson(1)))
                .andDo(print())
                .andDo(toDocument("update-post-success"));
    }

    @Test
    public void update_post_fail() throws Exception {
        //given
        willThrow(new IllegalArgumentException(FAIL_MESSAGE)).given(postService).update(anyLong(), any(PostUpdateRequest.class));
        //when
        ResultActions resultActions = mockMvc.perform(post("/api/v1/posts/update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(postUpdateRequest)));
        //then
        resultActions.andExpect(status().isBadRequest())
                .andExpect(content().json(toJson(failResponse)))
                .andDo(print())
                .andDo(toDocument("update-post-fail"));
    }

    @Test
    public void delete_post_success() throws Exception {
        //given
        willDoNothing().given(postService).delete(anyLong());
        //when
        ResultActions resultActions = mockMvc.perform(delete("/api/v1/posts/1"));
        //then
        resultActions.andExpect(status().isOk())
                .andDo(print())
                .andDo(toDocument("delete-post-success"));
    }

    @Test
    public void delete_post_fail() throws Exception {
        // given
        willThrow(new NotFoundException(ErrorCode.ENTITY_NOT_FOUND.getMessage())).given(postService).delete(anyLong());
        // when
        ResultActions resultActions = mockMvc.perform(delete("/api/v1/posts/1"));
        // then
        resultActions.andExpect(status().isBadRequest())
                .andExpect(content().json(toJson(failResponse)))
                .andDo(print())
                .andDo(toDocument("delete-post-fail"));
    }
}
