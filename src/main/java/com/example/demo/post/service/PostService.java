package com.example.demo.post.service;

import com.example.demo.global.error.exception.ErrorCode;
import com.example.demo.global.error.exception.NotFoundException;
import com.example.demo.post.controller.dto.PostRequest;
import com.example.demo.post.controller.dto.PostResponse;
import com.example.demo.post.controller.dto.PostUpdateRequest;
import com.example.demo.post.controller.dto.PostsResponse;
import com.example.demo.post.domain.Post;
import com.example.demo.post.domain.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;

    public Long create(PostRequest postRequest) {
        Post post = postRequest.toEntity();
        return postRepository.save(post).getId();
    }

    public PostResponse getPost(long postId) {
        Post findPost = postRepository.findById(postId).orElseThrow(() -> new NotFoundException(ErrorCode.ENTITY_NOT_FOUND.getMessage()));
        return PostResponse.builder()
                .id(findPost.getId())
                .title(findPost.getTitle())
                .content(findPost.getContent())
                .writer(findPost.getWriter())
                .build();
    }

    public PostsResponse getPosts() {
        List<Post> posts = postRepository.findAll();
        return PostsResponse.builder()
                .postResponses(posts.stream()
                        .map(post -> PostResponse.builder()
                                .id(post.getId())
                                .title(post.getTitle())
                                .content(post.getContent())
                                .writer(post.getWriter())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }

    public Long update(Long postId,PostUpdateRequest postUpdateRequest) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new NotFoundException(ErrorCode.ENTITY_NOT_FOUND.getMessage()));
        post.update(postUpdateRequest.getTitle(), postUpdateRequest.getContent());
        return postRepository.save(post).getId();
    }

    public void delete(long postId) {
        postRepository.deleteById(postId);
    }
}
