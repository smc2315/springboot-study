package com.example.demo.post.controller.dto;

import com.example.demo.post.domain.Post;
import lombok.*;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostRequest {

    private String title;
    private String content;
    private String writer;

    public Post toEntity() {

        return Post.builder()
                .title(title)
                .content(content)
                .writer(writer)
                .build();
    }
}
