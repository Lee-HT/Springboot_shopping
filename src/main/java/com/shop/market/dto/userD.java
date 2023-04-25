package com.shop.market.dto;

import com.shop.market.enums.Role;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class userD {

    private Long id;
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @NotBlank
    private String email;
    private String picture;
    private String provider;
    private String role;
    private String nickname;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    public userD update(String username,String picture){
        this.username = username;
        this.picture = picture;
        return this;
    }

    public userD updateRole(String role){
        this.role = role;
        return this;
    }

}
