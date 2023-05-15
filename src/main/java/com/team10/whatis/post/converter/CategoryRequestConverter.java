package com.team10.whatis.post.converter;

import com.team10.whatis.post.entity.Category;
import org.springframework.core.convert.converter.Converter;

public class CategoryRequestConverter implements Converter<String, Category> {

    @Override
    public Category convert(String categoryName) {
        return Category.ofName(categoryName);
    }
}
