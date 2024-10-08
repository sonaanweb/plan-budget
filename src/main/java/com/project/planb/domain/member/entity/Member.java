package com.project.planb.domain.member.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    // 회원 아이디
    @Column(nullable = false, unique = true)
    private String account;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private Boolean notificationEnabled;

    // 객체 생성
    @Builder
    public Member(String account, String password, Boolean notificationEnabled){
        this.account = account;
        this.password = password;
        this.notificationEnabled = notificationEnabled != null ? notificationEnabled : false;
    }

    // 저장 & 연관 객체 메소드
    public Member(Long id, String account, String password, Boolean notificationEnabled){
        this.id = id;
        this.account = account;
        this.password = password;
        this.notificationEnabled = notificationEnabled != null ? notificationEnabled : false;
    }
}
