package client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@EnableDiscoveryClient
@SpringBootApplication
@RestController
public class Html5Client {

    private final DiscoveryClient discoveryClient;

    @Autowired
    public Html5Client(DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
    }

    // <1>
    @RequestMapping(value = "/greetings-client-uri",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    Map<String, String> greetingClientURI() throws Exception {
        return discoveryClient.getInstances("greetings-client")
                .stream()
                .findAny()
                .map(serviceInstance -> Collections.singletonMap("uri", serviceInstance.getUri().toString()))
                .orElse(null);
    }

    public static void main(String[] args) {
        SpringApplication.run(Html5Client.class, args);
    }
}

