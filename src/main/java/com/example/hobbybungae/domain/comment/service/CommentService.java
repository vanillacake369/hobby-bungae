package com.example.hobbybungae.domain.comment.service;

import com.example.hobbybungae.domain.comment.dto.CommentRequestDto;
import com.example.hobbybungae.domain.comment.dto.CommentResponseDto;
import com.example.hobbybungae.domain.comment.entity.Comment;
import com.example.hobbybungae.domain.comment.exception.InvalidCommentModifier;
import com.example.hobbybungae.domain.comment.exception.NotFoundCommentException;
import com.example.hobbybungae.domain.comment.exception.UnmatchedCommentPost;
import com.example.hobbybungae.domain.comment.repository.CommentRepository;
import com.example.hobbybungae.domain.user.entity.User;
import com.example.hobbybungae.domain.post.entity.PostEntity;
import com.example.hobbybungae.domain.post.service.PostService;
import jakarta.persistence.EntityNotFoundException;
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
        PostEntity postEntity = postService.getPostEntity(postId);
        Comment comment = new Comment(requestDto, user, postEntity);
        Comment saveComment = commentRepository.save(comment);
        return new CommentResponseDto(saveComment);
    }

    public List<CommentResponseDto> getComments(Long postId) {
        PostEntity postEntity = postService.getPostEntity(postId);
        List<CommentResponseDto> commentResponseDtoList = commentRepository.findAllByPostEntity(postEntity)
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


    public Comment getCommentEntity(Long commentId){
        Comment comment = commentRepository.findById(commentId).orElseThrow(
            () -> new NotFoundCommentException("comment_id", Long.toString(commentId), "댓글 id를 확인해 주세요.")
        );
        return comment;
    }

    public void checkPost(Comment comment, Long postId){
        if(!comment.getPostEntity().getId().equals(postId)){
            throw new UnmatchedCommentPost("comment_id", Long.toString(comment.getCommmentId()), "해당 게시글의 댓글인지 확인해 주세요.");
        }
    }

    public void checkUser(Comment comment, String idName){
        if(!comment.getUser().getIdName().equals(idName)){
            throw new InvalidCommentModifier("id_name", idName, "댓글 작성자를 확인해 주세요.");
        }
    }
}
