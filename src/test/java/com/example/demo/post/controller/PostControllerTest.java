package com.example.demo.post.controller;

import com.example.demo.ApiDocument;
import com.example.demo.member.dto.MemberRequest;
import com.example.demo.member.dto.MemberResponse;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(PostController.class)
class PostControllerTest extends ApiDocument {

    private static final String SUCCESS_MESSAGE = "success";
    private static final String FAIL_MESSAGE = "fail";

    private PostRequest postRequest;
    private PostResponse successResponse;
    private PostResponse failResponse;

}