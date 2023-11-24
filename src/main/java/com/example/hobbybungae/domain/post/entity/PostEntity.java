package com.example.hobbybungae.domain.post.entity;


import com.example.hobbybungae.domain.post.dto.PostRequestDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "post")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostEntity extends TimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 20)
    private String title;
    @Column(nullable = false, length = 500)
    private String contents;
    @Column(nullable = false)
    private String state;
    @Column(nullable = false)
    private String hobby;

    public PostEntity(PostRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContent();
        this.state = requestDto.getState();
        this.hobby = requestDto.getHobby();
    }

    public void update(PostRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContent();
        this.state = requestDto.getState();
        this.hobby = requestDto.getHobby();


    }
}
