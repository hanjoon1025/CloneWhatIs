package com.team10.whatis.post.entity;

import com.team10.whatis.member.entity.Member;
import com.team10.whatis.post.dto.PostInfoRequestDto;
import com.team10.whatis.post.dto.PostRequestDto;
import com.team10.whatis.post.dto.PostStoryRequestDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@DynamicInsert
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Enumerated(value = EnumType.STRING)
    private Category category;

    private String title;

    //썸네일 이미지
    @ColumnDefault(value = "'https://song-github-actions-s3-bucket.s3.ap-northeast-2.amazonaws.com/defaultImage.jpg'")
    private String thumbnail;

    //목표 금액
    private int targetAmount;

    //펀딩 금액
    private int price;

    //모인 금액
    private int totalAmount;

    //프로젝트 이미지
    @ColumnDefault(value = "'https://song-github-actions-s3-bucket.s3.ap-northeast-2.amazonaws.com/defaultImage.jpg'")
    private String projectImage;

    //마감기한
    private LocalDate deadLine;

    //요약
    private String summary;

    //스토리보드
    @Column(length = 1000)
    private String storyBoard;

    //검색 태그
    @OneToMany(mappedBy = "post", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<PostTag> tags = new ArrayList<>();

    //좋아요 개수
    @Column(nullable = false)
    @ColumnDefault("0")
    private int likeCount;

    public Post(PostRequestDto postRequestDto, Member member) {
        this.category = postRequestDto.getCategory();
        this.member = member;
        this.totalAmount = 0;
    }

    public void updatePostInfo(PostInfoRequestDto postInfoRequestDto) {
        this.title = postInfoRequestDto.getTitle();
        this.targetAmount = postInfoRequestDto.getTargetAmount();
        this.price = postInfoRequestDto.getPrice();
        this.deadLine = postInfoRequestDto.getDeadLine();
    }

    public void updatePostStory(PostStoryRequestDto postStoryRequestDto) {
        this.summary = postStoryRequestDto.getSummary();
        this.storyBoard = postStoryRequestDto.getStoryBoard();
    }

    public void saveTags(Tag tag) {
        PostTag postTag = new PostTag(tag);
        postTag.setPost(this);
        tags.add(postTag);
    }

    public void updateTotalAmount() {
        this.totalAmount += price;
    }

    public void updateLike (Boolean likeOrDislike){
        this.likeCount = Boolean.TRUE.equals(likeOrDislike) ? this.likeCount + 1 : this.likeCount - 1;
    }
}
