package com.nhXJH;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * 文件模块---启动程序
 * 
 * @author nhXJH
 */
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
@EnableRabbit
public class LibraryMSApplication {
    public static void main(String[] args) {
        // System.setProperty("spring.devtools.restart.enabled", "false");
        SpringApplication.run(LibraryMSApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ 图书管理服务启动成功   ლ(´ڡ`ლ)ﾞ  \n" +
                " ________  ___  ___  ________  ________  _______   _______   ________     \n" +
                "|\\   ____\\|\\  \\|\\  \\|\\   ____\\|\\   ____\\|\\  ___ \\ |\\  ___ \\ |\\   ___ \\    \n" +
                "\\ \\  \\___|\\ \\  \\\\\\  \\ \\  \\___|\\ \\  \\___|\\ \\   __/|\\ \\   __/|\\ \\  \\_|\\ \\   \n" +
                " \\ \\_____  \\ \\  \\\\\\  \\ \\  \\    \\ \\  \\    \\ \\  \\_|/_\\ \\  \\_|/_\\ \\  \\ \\\\ \\  \n" +
                "  \\|____|\\  \\ \\  \\\\\\  \\ \\  \\____\\ \\  \\____\\ \\  \\_|\\ \\ \\  \\_|\\ \\ \\  \\_\\\\ \\ \n" +
                "    ____\\_\\  \\ \\_______\\ \\_______\\ \\_______\\ \\_______\\ \\_______\\ \\_______\\\n" +
                "   |\\_________\\|_______|\\|_______|\\|_______|\\|_______|\\|_______|\\|_______|\n" +
                "   \\|_________|                                                           \n" +
                "--------------------------------------------------------------------------------------------------------\n" +
                "————————————————————————————————————————————————————————————————————————————————————————————————————————");
    }
}
