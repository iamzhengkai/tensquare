package com.test;

import com.zk.ProductApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProductApplication.class)
public class RabbitmqTest {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void testProduct(){
        rabbitTemplate.convertAndSend("tensquare","direct message");
        System.out.println("message send");
    }
}
