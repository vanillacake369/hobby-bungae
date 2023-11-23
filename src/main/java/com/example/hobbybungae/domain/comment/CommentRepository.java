package com.example.hobbybungae.domain.comment;

import com.example.hobbybungae.domain.post.PostEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository  extends JpaRepository<Comment, Long> {

    List<Comment> findAllByPostEntity(PostEntity postEntity);
}
