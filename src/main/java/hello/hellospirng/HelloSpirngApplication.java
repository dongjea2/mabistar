package hello.hellospirng;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource(value = {"classpath:aws.properties"})
public class HelloSpirngApplication {

	public static void main(String[] args) {
		SpringApplication.run(HelloSpirngApplication.class, args);
	}
}
