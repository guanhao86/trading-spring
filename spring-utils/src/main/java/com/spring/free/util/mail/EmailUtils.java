package com.spring.free.util.mail;

import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Created by spink on 2017/6/30.
 */
@Slf4j
public class EmailUtils {

    public static final String SEND_EMAIL = "a1055237071@qq.com";
    public static final String EMAIL_PWD = "schtxjjfkldmbajj";
    public static final String HOST = "smtp.qq.com";
    public static final int PORT = 465;

    public static boolean sendMail(String subject, String content, String receiver) {
        return send(subject, content, receiver, SEND_EMAIL, HOST, PORT, EMAIL_PWD, "email");
    }

    public static boolean send(String subject, String content, String receiver, String sendMail, String host, int port, String pwd, String type) {
        boolean result = true;
        //, String host, int port, String from, String pwd, String type
        try {
            JavaMailSenderImpl senderImpl = new JavaMailSenderImpl();
            senderImpl = getJavaMailSender(senderImpl, host, port, sendMail, pwd, type);
            // 建立邮件消息,发送简单邮件和html邮件的区别
            MimeMessage mailMessage = senderImpl.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage);
            // 设置收件人，寄件人
            messageHelper.setTo(receiver);
            messageHelper.setFrom(sendMail);
            messageHelper.setSubject(subject);
            // true 表示启动HTML格式的邮件
            messageHelper.setText(content, true);

            // 发送邮件
            senderImpl.send(mailMessage);
        } catch (MessagingException e) {
            // TODO Auto-generated catch block
            result = false;
            log.info("EmailUtils.method [sendHtmlMail]: email send result-" + result + ",error message-" + e);
        }

        return result;
    }

    private static Properties getProperties(String type, String from, String pwd, int port, String host) {
        Properties prop = new Properties();
        if ("email".equals(type)) {
            // 将这个参数设为true，让服务器进行认证,认证用户名和密码是否正确
            prop.put("mail.smtp.auth", "true");
            prop.put("mail.smtp.ssl.enable", "true");
            prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        } else if ("gmail".equals(type)) {
            prop.put("mail.smtp.starttls.enable", "true");
            prop.put("mail.smtp.ssl.trust", host);
            prop.put("mail.smtp.user", from);
            prop.put("mail.smtp.password", pwd);
            prop.put("mail.smtp.port", port);
            prop.put("mail.smtp.auth", "true");
        }
        return prop;
    }

    private static JavaMailSenderImpl getJavaMailSender(JavaMailSenderImpl senderImpl, String host, int port, String from, String pwd, String type) {
        // 设定mail server
        senderImpl.setHost(host);
        senderImpl.setPort(port);
        // 根据自己的情况,设置发件邮箱地址
        senderImpl.setUsername(from);
        // 根据自己的情况, 设置password
        senderImpl.setPassword(pwd);
        senderImpl.setDefaultEncoding("UTF-8");
        senderImpl.setJavaMailProperties(getProperties(type, from, pwd, port, host));
        return senderImpl;
    }
}

