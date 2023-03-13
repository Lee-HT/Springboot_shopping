package com.shop.market.mybatisTest;

import com.shop.market.dto.userD;
import com.shop.market.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest
@Slf4j
public class UserSaveTest {

    @MockBean
    UserMapper userRepository;

    @Test
    @Transactional
    @Rollback
    public void userSaveTest(){
        log.info("save user 실행");
        userRepository.saveUser(userD.builder()
                .username("username").password("password").email("email@email").build());
    }




}
