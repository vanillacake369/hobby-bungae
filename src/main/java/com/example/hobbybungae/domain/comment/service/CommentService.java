package com.example.hobbybungae.domain.comment.service;

import com.example.hobbybungae.domain.comment.dto.CommentRequestDto;
import com.example.hobbybungae.domain.comment.dto.CommentResponseDto;
import com.example.hobbybungae.domain.comment.entity.Comment;
import com.example.hobbybungae.domain.comment.exception.InvalidCommentModifier;
import com.example.hobbybungae.domain.comment.exception.NotFoundCommentException;
import com.example.hobbybungae.domain.comment.exception.UnmatchedCommentPost;
import com.example.hobbybungae.domain.comment.repository.CommentRepository;
import com.example.hobbybungae.domain.post.entity.Post;
import com.example.hobbybungae.domain.post.service.PostService;
import com.example.hobbybungae.domain.user.entity.User;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    private final PostService postService;

    public CommentResponseDto postComment(Long postId, CommentRequestDto requestDto, User user) {
        Post post = postService.getPostEntity(postId);
        Comment comment = new Comment(requestDto, user, post);
        Comment saveComment = commentRepository.save(comment);
        return new CommentResponseDto(saveComment);
    }

    public List<CommentResponseDto> getComments(Long postId) {
        Post post = postService.getPostEntity(postId);
        List<CommentResponseDto> commentResponseDtoList = commentRepository.findAllByPost(post)
                .stream().map(CommentResponseDto::new).toList();

        return commentResponseDtoList;
    }

    @Transactional
    public CommentResponseDto updateComment(Long postId, Long commentId, CommentRequestDto requestDto, User user) {
        Comment comment = getCommentEntity(commentId);
        checkPost(comment, postId);
        checkUser(comment, user.getIdName());
        comment.update(requestDto);
        return new CommentResponseDto(comment);
    }


    public void deleteComment(Long postId, Long commentId, User user) {
        Comment comment = getCommentEntity(commentId);
        checkPost(comment, postId);
        checkUser(comment, user.getIdName());
        commentRepository.delete(comment);
    }


    public Comment getCommentEntity(Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new NotFoundCommentException("comment's id", commentId.toString())
        );
        return comment;
    }

    public void checkPost(Comment comment, Long postId) {
        if (!comment.getPost().getId().equals(postId)) {
            throw new UnmatchedCommentPost("comment's postId", postId.toString());
        }
    }

    public void checkUser(Comment comment, String idName) {
        if (!comment.getUser().getIdName().equals(idName)) {
            throw new InvalidCommentModifier("comment's modifier", idName);
        }
    }
}
