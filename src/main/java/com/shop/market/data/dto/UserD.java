package com.shop.market.data.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserD {
    private String username;

    private String password;

    private String email;

}
