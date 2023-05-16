package com.team10.whatis.post.dto;

import com.team10.whatis.post.entity.Category;
import lombok.Getter;

import java.beans.ConstructorProperties;

@Getter
public class PostRequestDto {
    private Category category;

    @ConstructorProperties({"category"})
    public PostRequestDto(Category category) {
        this.category = category;
    }
}
