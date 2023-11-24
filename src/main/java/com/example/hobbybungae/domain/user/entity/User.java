package com.example.hobbybungae.domain.user.entity;

import com.example.hobbybungae.domain.comment.entity.Comment;
import com.example.hobbybungae.domain.common.TimeStamp;
import com.example.hobbybungae.domain.post.entity.Post;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class User extends TimeStamp {
    //    @OneToMany(mappedBy = "user")
//    private List<UserHobby> userHobbyList = new ArrayList<>();
    @OneToMany(targetEntity = Post.class, mappedBy = "user")
    List<Post> posts;

    @OneToMany(targetEntity = Comment.class, mappedBy = "user")
    List<Comment> comments;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String idName;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String password;
    @Column
    private String introduction;

    @Builder
    public User(Long id, String idName, String name, String password) {
        this.id = id;
        this.idName = idName;
        this.name = name;
        this.password = password;
    }
}
