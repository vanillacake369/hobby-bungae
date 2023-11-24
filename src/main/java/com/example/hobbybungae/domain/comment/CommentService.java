package com.example.hobbybungae.domain.comment;

import com.example.hobbybungae.domain.user.entity.User;
import com.example.hobbybungae.entity.PostEntity;
import com.example.hobbybungae.service.PostService;
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
            () -> new EntityNotFoundException("해당 댓글을 찾을 수 없습니다.")
        );
        return comment;
    }

    public void checkPost(Comment comment, Long postId){
        if(!comment.getPostEntity().getId().equals(postId)){
            throw new MisMatchedCommentException("해당 글의 댓글이 아닙니다.");
        }
    }

    public void checkUser(Comment comment, String idName){
        if(!comment.getUser().getIdName().equals(idName)){
            throw new MisMatchedCommentException("작성자만 수정/삭제할 수 있습니다.");
        }
    }
}
