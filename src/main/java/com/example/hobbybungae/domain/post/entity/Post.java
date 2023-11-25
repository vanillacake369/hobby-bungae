package com.example.hobbybungae.domain.post.entity;


import com.example.hobbybungae.domain.comment.entity.Comment;
import com.example.hobbybungae.domain.common.TimeStamp;
import com.example.hobbybungae.domain.hobby.entity.Hobby;
import com.example.hobbybungae.domain.post.dto.PostRequestDto;
import com.example.hobbybungae.domain.state.entity.State;
import com.example.hobbybungae.domain.user.entity.User;
import com.example.hobbybungae.domain.userProfile.entity.UserHobby;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Table(name = "post")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Post extends TimeStamp {
    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

    @ManyToOne
    @JoinColumn(name = "state_id")
    State state;

    @OneToMany(mappedBy = "post")
    private List<PostHobby> postHobbyList = new ArrayList<>();

//    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
//    List<Comment> comments;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 20)
    private String title;
    @Column(nullable = false, length = 500)
    private String contents;

    public Post(PostRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContent();
        this.state = requestDto.getState();
//        this.hobby = requestDto.getHobby();
    }

    public void update(PostRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContent();
        this.state = requestDto.getState();
//        this.hobby = requestDto.getHobby();
    }
}
