package com.yjx;

import org.jasypt.encryption.StringEncryptor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.UUID;

@SpringBootTest
class PharmacyApplicationTests {

    @Autowired
    private StringEncryptor stringEncryptor;

    @Test
    void test() {
        String root = stringEncryptor.encrypt("root");
        String pwd = stringEncryptor.encrypt("123456");
        System.out.println("root = " + root);
        System.out.println("pwd = " + pwd);
    }

    @Test
    void test1() {
        Calendar calendar = Calendar.getInstance();
        System.out.println("calendar.getTime() = " + calendar.getTime());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String time = dateFormat.format(calendar.getTime());
        System.out.println("time = " + time);
        String uuid = UUID.randomUUID().toString();
        System.out.println("uuid = " + uuid);
        String suffix = uuid.substring(0, 5);
        System.out.println("suffix = " + suffix);
        String orderId = "Y" + time;
        System.out.println("订单号：" + orderId + suffix);
    }
}
