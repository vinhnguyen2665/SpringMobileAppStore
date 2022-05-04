package com.vinhnq.service;

import com.vinhnq.entity.User;
import com.vinhnq.beans.MailContentBeans;
import org.springframework.mail.SimpleMailMessage;

public interface MailService {
    void alertForgotPassword(User users, MailContentBeans mailContentBeans);

    void alertLogin(User users, MailContentBeans mailContentBeans);

    void sentWelcome(User users, MailContentBeans mailContentBeans);

    void sendMail(SimpleMailMessage message);
}
