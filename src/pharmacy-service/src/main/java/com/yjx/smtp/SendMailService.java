package com.yjx.smtp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Slf4j
@Service
public class SendMailService {

    @Autowired
    JavaMailSenderImpl mailSender;

    /**
     * 发送html格式的邮箱信息
     */
    public String sendAdminLoginEmailCode(String email) throws Exception {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = simpleDateFormat.format(date);

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
        messageHelper.setSubject("药店后台登录--邮箱安全验证");
        messageHelper.setFrom("2964302621@qq.com");
        messageHelper.setTo(email);
        String verificationCode = UUID.randomUUID().toString().substring(0, 6);

        messageHelper.setText("<h3>亲爱的用户：</h3>\n" +
                "您好！感谢您使用建祥药店，您的邮箱" + email +
                "<p>正在进行安全验证，</p>" +
                "本次请求的验证码为： " +
                "<a href='#'><span style='color:red;'>" + verificationCode + "</span></a>" +
                "<span style='font-size:14px;color:#aaaaaa'>(为了保障您帐号的安全性，请在5分钟内完成验证。)</span>", true);
        mailSender.send(messageHelper.getMimeMessage());

        log.info("发送人为:{}, 收件人为:{},  验证码为：{}, 发送时间为:{}",
                "2964302621@qq.com", email, verificationCode, time);

        return verificationCode;
    }
}
