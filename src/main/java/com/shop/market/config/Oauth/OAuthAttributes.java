package com.shop.market.config.Oauth;

import com.shop.market.dto.userD;
import com.shop.market.enums.Role;
import java.util.Map;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;

@Builder
@Getter
public class OAuthAttributes {
    private String registrationId;
    private String attributeKey;
    private String username;
    private String nickname;
    private String email;
    private String picture;

    public static OAuthAttributes of(String registrationId,
            String userNameAttributeName,
            Map<String,Object> attributes){
        if (registrationId.equals("naver")){
            return ofNaver("id",registrationId,attributes);
        } else if (registrationId.equals("google")) {
            return ofGoogle(userNameAttributeName,registrationId,attributes);
        }else{
            throw new OAuth2AuthenticationException("허용 되지 않는 인증");
        }
    }
    private static OAuthAttributes ofGoogle(String userNameAttributeName,
            String registrationId,
            Map<String, Object> attributes){
        return OAuthAttributes.builder()
                .username((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .picture((String) attributes.get("picture"))
                .attributeKey((String) attributes.get(userNameAttributeName))
                .registrationId(registrationId)
                .build();
    }

    private static OAuthAttributes ofNaver(String userNameAttributeName,
            String registrationId,
            Map<String, Object> attributes){
        Map<String,Object> response = (Map<String,Object>) attributes.get("response");
        return OAuthAttributes.builder()
                .username((String) response.get("nickname"))
                .email((String) response.get("email"))
                .picture((String) response.get("profile_image"))
                .attributeKey((String) response.get(userNameAttributeName))
                .registrationId(registrationId)
                .build();
    }

    public userD toUser(){
        return userD.builder()
                .username(username)
                .password(registrationId + "_" + attributeKey)
                .email(email)
                .picture(picture)
                .provider(registrationId)
                .role(Role.USER.getValue())
                .build();
    }
}
