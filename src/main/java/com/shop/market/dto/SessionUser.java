package com.shop.market.dto;

import java.io.Serializable;
import lombok.Getter;
import lombok.ToString;

@Getter
public class SessionUser implements Serializable {
    private String username;
    private String email;
    private String picture;
    private String role;

    public SessionUser(userD user){
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.picture = user.getPicture();
        this.role = user.getRole();
    }

}
