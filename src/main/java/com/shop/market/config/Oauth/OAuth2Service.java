package com.shop.market.config.Oauth;

import com.shop.market.dto.SessionUser;
import com.shop.market.dto.userD;
import com.shop.market.repository.UserMapper;
import jakarta.servlet.http.HttpSession;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OAuth2Service implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final UserMapper userMapper;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("loadUser start");
        OAuth2UserService delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest
                .getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName();
        Map<String,Object> attributes = oAuth2User.getAttributes();

        log.info("userNameAttributeName : " + userNameAttributeName);
        log.info("Attribute : " + oAuth2User.getAttributes().toString());

        OAuthAttributes oAuthAttributes = OAuthAttributes
                .of(registrationId,userNameAttributeName,attributes);

        userD user = saveOrUpdate(oAuthAttributes);
        // 직렬화를 위한 SessionUser 생성
        httpSession.setAttribute("user", new SessionUser(user));

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(user.getRole())),
                attributes, userNameAttributeName
        );
    }

    // DB에 유저 정보가 없으면 새 정보 insert 있다면 기존 정보 update
    private userD saveOrUpdate(OAuthAttributes attributes){
        String email = attributes.getEmail();
        log.info(email.toString());
        userD user = userMapper.findByEmail(email);
        if (user != null) {
            log.info("user != null");
            log.info("before : "+ user.getUsername());
            user.update(attributes.getUsername(), attributes.getPicture());
            log.info(String.format("id : %d , username : %s",user.getId(),user.getUsername()));
            userMapper.updateUser(user);
        }else {
            log.info("user == null");
            user = attributes.toUser();
            log.info(user.getUsername());
            userMapper.saveUser(user);
        }

        log.info(user.toString());
        return user;
    }


}
