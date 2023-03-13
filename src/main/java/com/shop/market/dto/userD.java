package com.shop.market.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class userD {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @NotBlank
    private String email;

}
