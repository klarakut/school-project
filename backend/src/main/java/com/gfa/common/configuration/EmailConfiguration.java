package com.gfa.common.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class EmailConfiguration {

    private final Environment env;
@Autowired
    public EmailConfiguration(Environment env) {
        this.env = env;
    }

    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSenderImpl = new JavaMailSenderImpl();
        mailSenderImpl.setHost(env.getProperty("spring.mail.host"));
        mailSenderImpl.setPort(Integer.parseInt(env.getProperty("spring.mail.port")));
        mailSenderImpl.setUsername(env.getProperty("spring.mail.username"));
        mailSenderImpl.setPassword(env.getProperty("spring.mail.password"));
        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "smtp");
        mailSenderImpl.setJavaMailProperties(props);
        return mailSenderImpl;
    }
}
