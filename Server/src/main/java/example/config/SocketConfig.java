package example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.Socket;

@Configuration
public class SocketConfig {

    @Bean
    public Socket socket() {
        Socket socket = new Socket();
        return socket;
    }

}
