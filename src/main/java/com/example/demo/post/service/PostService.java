package com.example.demo.post.service;

import com.example.demo.post.controller.dto.PostRequest;
import com.example.demo.post.controller.dto.PostResponse;
import com.example.demo.post.controller.dto.PostUpdateRequest;
import com.example.demo.post.controller.dto.PostsResponse;
import com.example.demo.post.domain.Post;
import com.example.demo.post.domain.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;

    public Long create(PostRequest postRequest) {

        Post post = postRequest.toEntity();
        return postRepository.save(post).getId();
    }

    public PostResponse getPost(long anyLong) {

        return null;
    }

    public PostsResponse getPosts() {

        return null;
    }

    public Long update(Long postId,PostUpdateRequest postUpdateRequest) {

        return null;
    }

    public void delete(long postId) {
    }
}
