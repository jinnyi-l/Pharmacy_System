package com.yjx.smtp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class SendMailController {

    @Autowired
    SendMailService sendMailService;

    /**
     * 例子
     *
     * @param toEmail 接收验证码的邮箱地址
     * @return res
     */
    @GetMapping("/emailHtml/{toEmail}")
    public String sendHtml(@PathVariable("toEmail") String toEmail) {
        try {
            sendMailService.sendAdminLoginEmailCode(toEmail);
            log.info("发送成功-->收件人：" + toEmail);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "发送成功";
    }


}
