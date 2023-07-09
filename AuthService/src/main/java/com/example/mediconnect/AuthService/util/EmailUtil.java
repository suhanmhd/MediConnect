package com.example.mediconnect.AuthService.util;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;


import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;


@Component
public class EmailUtil {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendOtpEmail(String email, String otp) throws MessagingException {
//        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
//        simpleMailMessage.setTo(email);
//        simpleMailMessage.setSubject("verify otp");
//        simpleMailMessage.setText(otp);
//        javaMailSender.send(simpleMailMessage);

//        javaMailSender.send(simpleMailMessage);

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
        mimeMessageHelper.setTo(email);
        mimeMessageHelper.setSubject("Verify OTP");

        mimeMessageHelper.setText(otp);      System.out.println(mimeMessage);



        javaMailSender.send(mimeMessage);

    }
}