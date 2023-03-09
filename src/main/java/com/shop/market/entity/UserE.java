package com.shop.market.entity;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserE extends BaseTimeE{
    private Long id;

    private String username;

    private String password;

    private String email;

    private String role;

    private LocalDateTime createdate;

    private LocalDateTime updatedate;
}
