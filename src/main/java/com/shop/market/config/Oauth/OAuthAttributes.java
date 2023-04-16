package com.shop.market.config.Oauth;

import com.shop.market.dto.userD;
import com.shop.market.enums.role;
import java.util.Map;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class OAuthAttributes {
    private String registrationId;
    private Map<String,Object> attributes;
    private String nameAttributeKey;
    private String username;
    private String email;
    private String picture;

    public static OAuthAttributes of(String registrationId,
            String userNameAttributeName,
            Map<String,Object> attributes){
        return ofGoogle(userNameAttributeName,registrationId,attributes);
    }
    private static OAuthAttributes ofGoogle(String userNameAttributeName,
            String registrationId,
            Map<String, Object> attributes){
        return OAuthAttributes.builder()
                .username((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .picture((String) attributes.get("picture"))
                .attributes(attributes)
                .registrationId(registrationId)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    public userD toUser(){
        return userD.builder()
                .username(username)
                .password(registrationId + username)
                .email(email)
                .picture(picture)
                .provider(registrationId)
                .role(role.USER.getRole())
                .build();
    }
}
