package co.kr.nomadlab.springgdic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@EnableJpaAuditing
public class SpringGdicApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringGdicApplication.class, args);
	}

}
