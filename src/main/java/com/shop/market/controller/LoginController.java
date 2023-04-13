package com.shop.market.controller;

import com.shop.market.dto.loginD;
import com.shop.market.dto.userD;
import com.shop.market.service.UserService;
import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
@RequestMapping("loginProcess/")
public class LoginController {

    private UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("findUserAll")
    public String findUserAll(@RequestBody loginD login){
        log.info("Controller findUserAll");
        userD user = userService.findUser(login);
        log.info(user.getUsername());
        log.info(user.getPassword());
        return "redirect:/login/login";
    }

    @PostMapping("findUser")
    public userD findUser(@RequestBody Map<String,String> emAndPv){
        log.info("Controller findUser");
        userD user = userService.findByEmailProvider(emAndPv);
        return user;

    }

    @PostMapping("login")
    public String login(@RequestBody loginD login) {
        log.info("Controller login");
        return "redirect:/login/login";
    }

    @PostMapping("register")
    public String Register(userD user) {
        log.info("Controller register");
        log.info(user.getUsername());
        log.info(user.getPassword());
        log.info(user.getEmail());
        userService.register(user);
        return "redirect:/login/login";
    }

    @DeleteMapping("unregister")
    public String unRegister(@RequestBody loginD login){
        log.info("Controller unregister");
        userService.unregister(login.getUsername());
        return "redirect:/login/login";
    }

    @PostMapping("users")
    @ResponseBody
    public ResponseEntity<List<userD>> userList(){
        log.info("Controller find user All");
        List<userD> userList = userService.findUserAll();

        return ResponseEntity.status(HttpStatus.OK).body(userList);
    }

    @PutMapping("update")
    @ResponseBody
    public userD updateUser(userD user){
        log.info("LoginController update");
        userService.updateUser(user);

        return user;
    }
}
