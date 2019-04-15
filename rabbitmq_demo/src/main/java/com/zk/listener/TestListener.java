package com.zk.listener;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

@RabbitListener(queues = "tensquare")
public class TestListener {
    @RabbitHandler
    public void myHandler(String msg){
        System.out.println("message received: " + msg);
    }
}
