package com.photoapp.apiusers.controller;

import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UsersController {

  private final Environment env;

  public UsersController(Environment env) {
    this.env = env;
  }

  @GetMapping("/check")
  public String check(){
    return "Service alive,port number : "+env.getProperty("local.server.port");
  }

}
