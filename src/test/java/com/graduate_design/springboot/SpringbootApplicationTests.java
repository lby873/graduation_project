package com.graduate_design.springboot;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
class SpringbootApplicationTests {

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Test
    void contextLoads() {
        System.out.println("admin : "+encoder.encode("admin"));



//        BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
//        // 加密所需的salt值，在虚拟机配置更安全-Djasypt.encryptor.password="123456"
//        textEncryptor.setPassword("123456");
//        // 要加密的数据
//        String pw = stringEncryptor.encrypt("user1");    // 空字符串也有加密字符串，每次加密结果不一样
//        String pw1 = stringEncryptor.decrypt(pw);
//        System.out.println("----------------------");
//        System.out.println("pw1: "+pw1);


    }


}
