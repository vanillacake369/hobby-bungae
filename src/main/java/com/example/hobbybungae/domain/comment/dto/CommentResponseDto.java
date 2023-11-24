package com.example.hobbybungae.domain.comment.dto;

import com.example.hobbybungae.domain.comment.entity.Comment;
import lombok.Getter;

@Getter
public class CommentResponseDto {
    private Long commentId;

    private String text;

    private String idName;

    public CommentResponseDto(Comment comment) {
        this.commentId = comment.getCommmentId();
        this.text = comment.getText();
        this.idName = comment.getUser().getIdName();
    }
}
