package com.team10.whatis.post.dto;

import com.team10.whatis.post.entity.Post;
import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
public class PostResponseDto {
    private Long id;
    private String title;
    private String thumbnail;
    private int targetAmount;
    private int totalAmount;
    private LocalDate deadLine;
    private int percentage;
    private String name;
    private List<String> tags = new ArrayList<>();

    //TODO likeStatus 추가 필요(추가시 주석 제거)

    public PostResponseDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.thumbnail = post.getThumbnail();
        this.targetAmount = post.getTargetAmount();
        this.totalAmount = post.getTotalAmount();
        this.deadLine = post.getDeadLine();
        this.percentage = calcPercentage(post.getTotalAmount(), post.getTargetAmount());
        this.name = post.getMember().getUsername();
        post.getTags().forEach(tag -> this.tags.add(tag.getTag().getName()));
    }

    private int calcPercentage(int totalAmount, int targetAmount) {
        return (int) Math.round(((double) totalAmount / targetAmount * 100));
    }
}
