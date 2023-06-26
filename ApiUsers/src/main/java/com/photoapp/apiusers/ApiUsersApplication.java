package com.photoapp.apiusers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.web.exchanges.HttpExchangeRepository;
import org.springframework.boot.actuate.web.exchanges.InMemoryHttpExchangeRepository;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class ApiUsersApplication {

  public static void main(String[] args) {
    SpringApplication.run(ApiUsersApplication.class, args);
  }

  @Bean
  public HttpExchangeRepository httpExchangeRepository(){
    return new InMemoryHttpExchangeRepository();
  }
}
