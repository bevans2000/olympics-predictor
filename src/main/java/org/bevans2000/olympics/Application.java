package org.bevans2000.olympics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Configuration
@ComponentScan
@EnableAutoConfiguration(exclude = { 
        org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration.class 
    })
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}



@ResponseStatus(HttpStatus.NOT_FOUND)
class UserNotFoundException extends RuntimeException {

	public UserNotFoundException(String userId) {
		super("could not find user '" + userId + "'.");
	}
}
