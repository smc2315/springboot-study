package com.example.demo.post.controller;

import com.example.demo.post.controller.dto.PostRequest;
import com.example.demo.post.controller.dto.PostResponse;
import com.example.demo.post.controller.dto.PostUpdateRequest;
import com.example.demo.post.controller.dto.PostsResponse;
import com.example.demo.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<Long> create(@RequestBody PostRequest PostRequest) {
        return ResponseEntity.ok().body(postService.create(PostRequest));
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostResponse> getPost(@PathVariable Long postId) {
        return ResponseEntity.ok().body(postService.getPost(postId));
    }

    @GetMapping
    public ResponseEntity<PostsResponse> getPosts() {
        return ResponseEntity.ok().body(postService.getPosts());
    }

    @PostMapping("/update/{postId}")
    public ResponseEntity<Long> update(@PathVariable Long postId, @RequestBody PostUpdateRequest postUpdateRequest) {
        return ResponseEntity.ok().body(postService.update(postId,postUpdateRequest));
    }
}
