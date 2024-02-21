package by.javaguru;

import by.javaguru.config.ApplicationConfiguration;
import by.javaguru.model.service.CompanyService;
import by.javaguru.model.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;

public class ApplicationRunner {
    public static void main(String[] args) {
        try (var context = new AnnotationConfigApplicationContext()) {
            context.register(ApplicationConfiguration.class);
            context.getEnvironment().setActiveProfiles("prod"); //или в app.properties -> spring.profiles.active=prod
            context.refresh();
            System.out.println(Arrays.toString(context.getEnvironment().getActiveProfiles()));

            var userService = context.getBean("userService", UserService.class);
            var user = userService.findById(2);
            System.out.println(user);
            var companyService = context.getBean("companyService", CompanyService.class);
            var company = companyService.findById(2);
            System.out.println(company);
        }
    }
}
