package com.team10.whatis.post.repository;

import com.team10.whatis.post.entity.Category;
import com.team10.whatis.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findByTitleContainingOrTagsTagNameContaining(Pageable pageable, String keyword, String tagKeyword);
    Page<Post> findAllByCategory(Pageable pageable, Category category);
}
