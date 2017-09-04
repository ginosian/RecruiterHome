import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by Martha on 9/2/2017.
 */
@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan("com.recruiting")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
