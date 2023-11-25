package com.example.hobbybungae.domain.user.entity;

import com.example.hobbybungae.domain.comment.entity.Comment;
import com.example.hobbybungae.domain.common.TimeStamp;
import com.example.hobbybungae.domain.post.entity.Post;
import com.example.hobbybungae.domain.userProfile.entity.UserHobby;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class User extends TimeStamp {

    @OneToMany(targetEntity = Post.class, mappedBy = "user")
    List<Post> posts;

    @OneToMany(targetEntity = Comment.class, mappedBy = "user")
    List<Comment> comments;

    @OneToMany(mappedBy = "user")
    private List<UserHobby> userHobbyList = new ArrayList<>();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String idName;
    @Column(nullable = false)
    private String name;

    @Column
    private String Introduction;

    @Column(nullable = false)
    private String password;

    @Builder
    public User(Long id, String idName, String name, String introduction, String password, List<UserHobby> hobbyList) {
        this.id = id;
        this.idName = idName;
        this.name = name;
        this.Introduction = introduction;
        this.password = password;
        this.userHobbyList = hobbyList;
    }
}
