package code.mentor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DailyNewApplication {

    public static void main(String[] args) {
        SpringApplication.run(DailyNewApplication.class, args);
    }

}
