package start;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

@ComponentScan("curse")
@SpringBootApplication
public class StartRestServices {
    public static void main(String[] args) {
        SpringApplication.run(StartRestServices.class, args);
    }

    @Bean(name="props")
    public Properties getBdProperties(){
        Properties serverProps=new Properties();
        try {
            serverProps.load(StartRestServices.class.getResourceAsStream("/curseserver.properties"));
            System.out.println("Server properties set. ");
            serverProps.list(System.out);
        } catch (IOException e) {
            System.err.println("Cannot find curseserverr.properties "+e);
            return null;
        }
        return serverProps;
    }
}
