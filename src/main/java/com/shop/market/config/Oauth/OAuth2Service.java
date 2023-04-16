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

        log.info("Attribute get user :" + oAuth2User.getAttributes().get("name"));

        OAuthAttributes attributes = OAuthAttributes
                .of(registrationId,userNameAttributeName,oAuth2User.getAttributes());

        userD user = saveOrUpdate(attributes);
        httpSession.setAttribute("user", new SessionUser(user));

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey()
        );



    }

    private userD saveOrUpdate(OAuthAttributes attributes){
        log.info("userSaveOrUpdate");
        Map<String,String> emAndPv = new HashMap<>();
        emAndPv.put("email",attributes.getEmail());
        emAndPv.put("provider", attributes.getRegistrationId());
        log.info(emAndPv.toString());
        userD user = userMapper.findByEmailAndProvider(emAndPv);
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

        return user;
    }


}
