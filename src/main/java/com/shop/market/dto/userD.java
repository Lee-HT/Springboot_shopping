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
    private String picture;
    @NotBlank
    private String provider;
    @NotBlank
    private String role;
    private String nickname;
    @NotBlank
    private LocalDateTime createDate;
    @NotBlank
    private LocalDateTime updateDate;

    public userD update(String email,String picture){
        this.email = email;
        this.picture = picture;
        return this;
    }

    public String getRoleKey(){
        return this.role;
    }

}
