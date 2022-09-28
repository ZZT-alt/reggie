package com.zztqvq.util;

import lombok.extern.slf4j.Slf4j;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Slf4j
public class MailUtil implements Runnable {
    /*=========收件人的信息==========*/
    private String email;// 收件人邮箱
    private String code;// 激活码

    /*=========初始化===============*/
    public MailUtil(String email, String code) {
        this.email = email;
        this.code = code;
    }


    public void run() {
        try {
            String host = "smtp.qq.com";
            String from = "1621918481@qq.com";
            String password = "tbrfhiqmnwkredcd";
            Properties props = System.getProperties();
            props.put("qq.smtp.starttls.enable", "true"); // 在本行添加
            props.put("qq.smtp.host", host);
            props.put("qq.smtp.user", from);
            props.put("qq.smtp.password", password);
            props.put("qq.smtp.port", "587");
            props.put("qq.smtp.auth", "true");
            Session session = Session.getDefaultInstance(props, null);
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            System.out.println(Message.RecipientType.TO);
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            message.setSubject("你有一封账号激活邮件");
            String content =
                    "<html><head></head><body><h1>请点击以下链接以完成激活账号</h1><h3><a href='http://localhost:8080/emailUser/save?code="
                            + code + "'>http://localhost:8080/emailUser/save?code=" + code
                            + "</href></h3></body></html>";
            message.setContent(content, "text/html;charset=UTF-8");
            Transport transport = session.getTransport("smtp");
            transport.connect(host, from, password);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        }
        catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
