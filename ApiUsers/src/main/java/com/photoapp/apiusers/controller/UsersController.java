package com.photoapp.apiusers.controller;

import com.photoapp.apiusers.dto.UserDTO;
import com.photoapp.apiusers.model.Users;
import com.photoapp.apiusers.service.UsersServices;
import jakarta.validation.Valid;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UsersController {

  private final Environment env;
  private final UsersServices services;

  public UsersController(Environment env, UsersServices services) {
    this.env = env;
    this.services = services;
  }

  @GetMapping("/check")
  public String check(){
    return "Service alive,port number : "+env.getProperty("local.server.port")+", with secret token :"+env.getProperty("token.secret");
  }

  @PostMapping
  public ResponseEntity<Users> createUser(@Valid @RequestBody UserDTO user){
    return ResponseEntity.status(HttpStatus.OK).body(services.createUser(user));
  }

}
