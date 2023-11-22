package com.example.hobbybungae.profile;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Table(name = "User_Hobby")
@Entity
public class User_Hobby {
    // 기본키 매핑
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "User_Hobby")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hobby_id")
    private Hobby_id hobby_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id")
    private Profile_id profile_id;

    @CreatedDate
    private LocalDate created;

    public void setParent(ProfileEntity profileEntity) {
        this.profileEntity = profileEntity;
        this.id = new id(profileEntity.getProfile_Id(), hobby_id.getHobby_Id());
    }
}
