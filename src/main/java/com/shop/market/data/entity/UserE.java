package com.shop.market.data.entity;

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
}
