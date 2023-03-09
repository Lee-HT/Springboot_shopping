package com.shop.market.mybatisTest;

import com.shop.market.dto.UserD;
import com.shop.market.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest
@Slf4j
public class UserSaveTest {

    @MockBean
    UserRepository userRepository;

    @Test
    @Transactional
    @Rollback
    public void userSaveTest(){
        log.info("save user 실행");
        userRepository.saveUser(UserD.builder()
                .username("username").password("password").email("email@email").build());
    }


}
