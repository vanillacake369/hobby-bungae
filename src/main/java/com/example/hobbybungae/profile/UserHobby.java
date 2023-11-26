package com.example.hobbybungae.profile;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Table(name = "user_Hobby")
@Entity
public class UserHobby {
    // 기본키 매핑
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_Hobby")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hobby_id")
    private Hobby hobby;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id")
    private Profile profile;

    public void setParent(ProfileEntity profileEntity) {
        this.profileEntity = profileEntity;
        this.id = new id(profileEntity.getProfile_Id(), hobby_id.getHobby_Id());
    }
}
