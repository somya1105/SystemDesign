package org.expenseTracker.configurations.launcher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
@ComponentScan(basePackages = "org.expenseTracker.api.user")
public class ExpenseTrackerLauncher {

    public static void main(String[] args) {
        SpringApplication.run(ExpenseTrackerLauncher.class, args);
    }
}
