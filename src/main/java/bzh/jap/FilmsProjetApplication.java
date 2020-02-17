package bzh.jap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FilmsProjetApplication {

	public static void main(String[] args) {
		System.setProperty("file.encoding", "UTF-8");
		SpringApplication.run(FilmsProjetApplication.class, args);
	}

}