package cn.pompip.browser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class BrowserApplication {

    public static void main(String[] args) {
        SpringApplication.run(BrowserApplication.class, args);
    }
}
