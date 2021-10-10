package com.phoenixhell.newsecurity;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

//@SpringBootTest
class NewSecurityApplicationTests {

    @Test
    void contextLoads() {
        String password = BCrypt.hashpw("123", BCrypt.gensalt());
        System.out.println(password);
    }

}
