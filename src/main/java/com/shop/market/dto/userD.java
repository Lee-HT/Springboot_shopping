package com.shop.market.dto;

import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
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
    private Long id;
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @NotBlank
    private String email;
    @NotBlank
    private String provider;
    @NotBlank
    private String role;
    @NotBlank
    private LocalDateTime createDate;
    @NotBlank
    private LocalDateTime updateDate;

    public void update(String email,String provider){
        this.email = email;
        this.provider = provider;
    }

}
