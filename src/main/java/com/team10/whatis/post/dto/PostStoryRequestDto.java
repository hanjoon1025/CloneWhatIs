package com.team10.whatis.post.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostStoryRequestDto {
    @Size(max = 100, message = "{summary}")
    private String summary;

    @Size(max = 1000, message = "{storyBoard}")
    private String storyBoard;
}
