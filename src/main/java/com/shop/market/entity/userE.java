package com.shop.market.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Getter
@Table(name="user_table")
public class userE extends BaseTimeE{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 30,unique = true, nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(length = 50,unique = true)
    private String email;
    @Column(length = 15)
    private String provider;
    @Column(length = 15,nullable = false)
    private String role;
    private String picture;
    @Column(length = 15)
    private String nickname;

}
