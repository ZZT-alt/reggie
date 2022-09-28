package com.zztqvq;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zztqvq.common.R;
import com.zztqvq.dto.DishDto;
import com.zztqvq.entity.Employee;
import com.zztqvq.mapper.DishMapper;
import com.zztqvq.mapper.EmployeeMapper;
import com.zztqvq.util.CodeUtil;
import com.zztqvq.util.MailUtil;
import com.zztqvq.util.SqlSessionFactoryUtils;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

@SpringBootTest
class ReggieTakeOutApplicationTests {

//    @Test
//    void test01() throws IOException {
//        SqlSessionFactory sqlSessionFactory = SqlSessionFactoryUtils.getSqlSessionFactory();
//        SqlSession sqlSession = sqlSessionFactory.openSession();
//        EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
//        List<Employee> employeeList = mapper.getAll();
//        for (Employee employee : employeeList) {
//            System.out.println(employee);
//        }
//        sqlSession.close();
//    }
//
//    @Test
//    void test02(){
//        SqlSessionFactory sqlSessionFactory = SqlSessionFactoryUtils.getSqlSessionFactory();
//        SqlSession sqlSession = sqlSessionFactory.openSession();
//        DishMapper mapper = sqlSession.getMapper(DishMapper.class);
//        //List<DishDto> page = mapper.selectPage(1, 20,null);
//        //System.out.println(page);
//    }
//
////    @Test
////    void test03() throws EmailException {
////        HtmlEmail send = new HtmlEmail();
//////        send.setHostName("smtp.qq.com");
//////        send.setAuthentication("1621918481@qq.com","tbrfhiqmnwkredcd");
//////        send.setFrom("1621918481@qq.com");
//////        send.addTo("zztqvq@gmail.com");
//////        send.setSubject("Test");
//////        send.
////        send.setSmtpPort(587);
////        send.setSSLOnConnect(true);
////        send.setHostName("smtp.gmail.com");
////        send.setAuthentication("zztqvq@gmail.com", "pzsacemwtfrectbv");
////        send.setFrom("zztqvq@gmail.com");
////        send.addTo("1621918481@qq.com");
////        send.setSubject("Test");
////        send.setMsg("欢迎使用邮箱验证本系统！");
////        send.setCharset("UTF-8");
////        send.send();
////    }
//
////    @Test
////    public void test04() throws MessagingException {
////        String host = "smtp.gmail.com";
////        String from = "zztqvq@gmail.com";
////        String password = "pzsacemwtfrectbv";
////        Properties props = System.getProperties();
////        props.put("mail.smtp.starttls.enable", "true"); // 在本行添加
////        props.put("mail.smtp.host", host);
////        props.put("mail.smtp.user", from);
////        props.put("mail.smtp.password", password);
////        props.put("mail.smtp.port", "587");
////        props.put("mail.smtp.auth", "true");
////
////        String[] to = {"1621918481@qq.com"}; // 在本行添加
////
////        Session session = Session.getDefaultInstance(props, null);
////        MimeMessage message = new MimeMessage(session);
////        message.setFrom(new InternetAddress(from));
////
////        InternetAddress[] toAddress = new InternetAddress[to.length];
////
////        // 获取地址的array
////        for (int i = 0; i < to.length; i++) { // 从while循环更改而成
////            toAddress[i] = new InternetAddress(to[i]);
////        }
////        System.out.println(Message.RecipientType.TO);
////
////        for (InternetAddress address : toAddress) { // 从while循环更改而成
////            message.addRecipient(Message.RecipientType.TO, address);
////        }
////        message.setSubject("Test");
////        message.setText("Welcome to JavaMail");
////        Transport transport = session.getTransport("smtp");
////        transport.connect(host, from, password);
////        transport.sendMessage(message, message.getAllRecipients());
////        transport.close();
////    }
//
//    @Test
//    public void test05(){
//       new Thread(new MailUtil("1621918481@qq.com","123")).start();
//    }
}
