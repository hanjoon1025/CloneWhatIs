package com.team10.whatis.post.repository;

import com.team10.whatis.post.entity.Category;
import com.team10.whatis.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findByTitleContainingOrTagsTagNameContaining(Pageable pageable, String keyword, String tagKeyword);
 
    @Query("select p from Post p where p.member.id = :memberId") // MemberId와 일치하는 Post들만 가져오기
    List<Post> findAllByMemberId(@Param("memberId") Long memberId);
    /*
        FundPost에서 memberId를 가지고 있는
        레코드들을 가지고 온 다음 , post.id 와
        FundPost의 post.id가 일치하는것들만
        가져오는 쿼리
     */
    @Query("select p from Post p where p.id in (select f.post.id from FundPost f where f.member.id=:memberId)")
    List<Post> findAllByMemberIdAndIsFunding(@Param("memberId") Long memberId);

    Page<Post> findAllByCategory(Pageable pageable, Category category);
}
