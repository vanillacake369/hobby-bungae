package com.example.hobbybungae.domain.comment.entity;

import com.example.hobbybungae.domain.comment.dto.CommentRequestDto;
import com.example.hobbybungae.domain.user.entity.User;
import com.example.hobbybungae.domain.post.entity.PostEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commmentId;

    @Column(nullable = false)
    private String text;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name="post_id")
    private PostEntity postEntity;

    public Comment(CommentRequestDto requestDto, User user, PostEntity postEntity) {
        this.text = requestDto.getText();
        this.user = user;
        this.postEntity = postEntity;
    }

    public void update(CommentRequestDto requestDto) {
        this.text = requestDto.getText();
    }
}
