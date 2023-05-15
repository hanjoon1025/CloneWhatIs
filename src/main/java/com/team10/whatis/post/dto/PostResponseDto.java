package com.team10.whatis.post.dto;

import com.team10.whatis.post.entity.Category;
import com.team10.whatis.post.entity.Post;
import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
public class PostResponseDto {
    private Long id;
    private Category category;
    private String title;
    private String thumbnail;
    private int targetAmount;
    private int totalAmount;
    private LocalDate deadLine;
    private int percentage;
    private String name;
    private List<String> tags = new ArrayList<>();
    private int likeCount;
    private boolean likeStatus;
   

    public PostResponseDto(Post post, boolean likeStatus) {
        this.id = post.getId();
        this.category = post.getCategory();
        this.title = post.getTitle();
        this.thumbnail = post.getThumbnail();
        this.targetAmount = post.getTargetAmount();
        this.totalAmount = post.getTotalAmount();
        this.deadLine = post.getDeadLine();
        this.percentage = calcPercentage(post.getTotalAmount(), post.getTargetAmount());
        this.name = post.getMember().getUsername();
        post.getTags().forEach(tag -> this.tags.add(tag.getTag().getName()));
        this.likeCount = post.getLikeCount();
        this.likeStatus = likeStatus;
    }

    private int calcPercentage(int totalAmount, int targetAmount) {
        return (int) Math.round(((double) totalAmount / targetAmount * 100));
    }

    public void setLikeStatus(boolean likeStatus) {
        this.likeStatus = likeStatus;
    }
}
