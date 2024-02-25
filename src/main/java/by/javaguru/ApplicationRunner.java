package by.javaguru;

import by.javaguru.config.ApplicationConfiguration;
import by.javaguru.model.dto.CompanyCreateDto;
import by.javaguru.model.dto.UserCreateDto;
import by.javaguru.model.service.CompanyService;
import by.javaguru.model.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;

public class ApplicationRunner {
    public static void main(String[] args) {
        try (var context = new AnnotationConfigApplicationContext()) {
            context.register(ApplicationConfiguration.class);
            context.getEnvironment().setActiveProfiles("prod");
            context.refresh();
            System.out.println(Arrays.toString(context.getEnvironment().getActiveProfiles()));

            var userService = context.getBean("userService", UserService.class);
            var user = userService.findById(2);
            System.out.println(user);
            userService.update(5, "Joshua Bloch");
            userService.delete(3);
            userService.create(new UserCreateDto("Steve Wozniak", 2));
            userService.findAll();

            var companyService = context.getBean("companyService", CompanyService.class);
            var company = companyService.findById(2);
            System.out.println(company);
            companyService.update(2, "Apple3");
            companyService.delete(13);
            companyService.create(new CompanyCreateDto("test123"));
            companyService.findAll();
        }
    }
}
