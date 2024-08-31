package com.yangyang;


import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.Date;

@SpringBootTest()
public class DateTest {

    @Test
    public void test() {
        Date date = new Date();
        System.out.println(date);
    }
}
