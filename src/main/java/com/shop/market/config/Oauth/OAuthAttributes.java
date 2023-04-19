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
    private String attributeKey;
    private String username;
    private String nickname;
    private String email;
    private String picture;

    public static OAuthAttributes of(String registrationId,
            String userNameAttributeName,
            Map<String,Object> attributes){
        if ("naver".equals(registrationId)){
            return ofNaver("id",registrationId,attributes);
        }
        return ofGoogle(userNameAttributeName,registrationId,attributes);
    }
    private static OAuthAttributes ofGoogle(String userNameAttributeName,
            String registrationId,
            Map<String, Object> attributes){
        return OAuthAttributes.builder()
                .username((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .picture((String) attributes.get("picture"))
                .attributeKey((String) attributes.get(userNameAttributeName))
                .attributes(attributes)
                .registrationId(registrationId)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    private static OAuthAttributes ofNaver(String usernameAttributeName,
            String registrationId,
            Map<String, Object> attributes){
        Map<String,Object> response = (Map<String,Object>) attributes.get("response");
        return OAuthAttributes.builder()
                .username((String) response.get("nickname"))
                .email((String) response.get("email"))
                .picture((String) response.get("profile_image"))
                .attributeKey((String) response.get(usernameAttributeName))
                .attributes(response)
                .registrationId(registrationId)
                .nameAttributeKey(usernameAttributeName)
                .build();
    }

    public userD toUser(){
        return userD.builder()
                .username(username)
                .password(registrationId + "_" + attributeKey)
                .email(email)
                .picture(picture)
                .provider(registrationId)
                .role(role.USER.getRole())
                .build();
    }
}
