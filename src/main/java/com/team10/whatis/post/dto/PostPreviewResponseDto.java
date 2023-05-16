package com.team10.whatis.post.dto;

import com.team10.whatis.post.entity.Category;
import com.team10.whatis.post.entity.Post;
import lombok.Getter;
import java.time.LocalDate;
@Getter
public class PostPreviewResponseDto {
    private Long id;
    private Category category;
    private String title;
    private String thumbnail;
    private int totalAmount;
    private LocalDate deadLine;
    private int percentage;
    private String name;
    private int likeCount;
    private boolean likeStatus;


    public PostPreviewResponseDto(Post post, boolean likeStatus) {
        this.id = post.getId();
        this.category = post.getCategory();
        this.title = post.getTitle();
        this.thumbnail = post.getThumbnail();
        this.totalAmount = post.getTotalAmount();
        this.deadLine = post.getDeadLine();
        this.percentage = calcPercentage(post.getTotalAmount(), post.getTargetAmount());
        this.name = post.getMember().getUsername();
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
