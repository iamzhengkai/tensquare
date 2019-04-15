package com.tensquare.sms.listener;

import com.aliyuncs.exceptions.ClientException;
import com.tensquare.sms.util.SmsUtil;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;
@Component
@RabbitListener(queues = "sms")
public class SmsListener {
    @Autowired
    private SmsUtil smsUtil;
    @Value("${aliyun.sms.template_code}")
    private String templateCode;
    @Value("${aliyun.sms.sign_name}")
    private String signName;

    @RabbitHandler
    public void handler(Map<String,Object> message){
        String mobile = message.get("mobile").toString();
        System.out.println(mobile);
        String code = message.get("code").toString();
        System.out.println(code);

        try {
            smsUtil.sendSms(mobile,templateCode,signName,"{\"code\":\"" + code + "\"}");
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }
}
