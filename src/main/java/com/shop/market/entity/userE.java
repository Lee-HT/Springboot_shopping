package com.shop.market.entity;

import com.shop.market.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Getter
@Table(name="user_table")
public class userE extends BaseTimeE{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 50,unique = true, nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(unique = true)
    private String email;
    private String picture;
    @Column(length = 30)
    private String provider;
    @Column(nullable = false)
    private String role;
    @Column(length = 15)
    private String nickname;

}
