package bzh.jap.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@ComponentScan(basePackages={"bzh.jap.controller"})
public class FilmsProjetApplication {

	public static void main(String[] args) {
		SpringApplication.run(FilmsProjetApplication.class, args);
	}

}