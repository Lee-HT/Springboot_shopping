package com.shop.market.dto;

import java.io.Serializable;
import lombok.Getter;

@Getter
public class SessionUser implements Serializable {
    private String username;
    private String email;
    private String picture;

    public SessionUser(userD user){
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.picture = user.getPicture();

    }

}
