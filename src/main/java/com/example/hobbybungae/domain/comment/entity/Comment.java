package com.example.hobbybungae.domain.comment.entity;

import com.example.hobbybungae.domain.comment.dto.CommentRequestDto;
import com.example.hobbybungae.domain.post.entity.Post;
import com.example.hobbybungae.domain.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Table(name = "comment")
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String text;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    public Comment(CommentRequestDto requestDto, User user, Post post) {
        this.text = requestDto.getText();
        this.user = user;
        this.post = post;
    }

    public void update(CommentRequestDto requestDto) {
        this.text = requestDto.getText();
    }
}
