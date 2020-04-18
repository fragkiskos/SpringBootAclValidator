package acldemo;

import acldemo.restControllers.UserDto;
import com.google.gson.Gson;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AclDemoApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(AclDemoApplication.class, args);
    }
}
