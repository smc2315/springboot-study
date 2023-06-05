package com.example.demo.post.dto;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostRequest {

    private Long id;
    private String title;
    private String content;
    private String writer;
}
