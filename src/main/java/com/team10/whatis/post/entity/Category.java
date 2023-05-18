package com.team10.whatis.post.entity;

import lombok.Getter;

@Getter
public enum Category {
    All("All"),
    FashionStuff("FashionStuff"),
    HomeLiving("HomeLiving"),
    TechEletrics("TechElectrics"),
    Beauty("Beauty"),
    Food("Food"),
    LeisureSports("LeisureSports");

    private String categoryName;

    Category(String category) {
        this.categoryName = category;
    }

    public static Category ofName(String categoryStr) {
        for (Category category : Category.values()) {
            if (category.getCategoryName().equals(categoryStr))
                return category;
        }
        throw new IllegalArgumentException("일치하는 카테고리가 없습니다.");
    }
}
