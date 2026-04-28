package com.capstone.capstone.mail;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender mailSender;

    public void sendTempPassword(String to, String tempPassword) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(to);
        message.setSubject("[Dev Bridge] 임시 비밀번호 안내");
        message.setText("임시 비밀번호는 [" + tempPassword + "] 입니다.\n로그인 후 비밀번호를 변경해주세요.");

        mailSender.send(message);
    }
}