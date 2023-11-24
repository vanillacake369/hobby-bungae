package com.example.hobbybungae.domain.comment.repository;


import com.example.hobbybungae.domain.comment.entity.Comment;
import com.example.hobbybungae.domain.post.entity.PostEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository  extends JpaRepository<Comment, Long> {

    List<Comment> findAllByPostEntity(PostEntity postEntity);
}
