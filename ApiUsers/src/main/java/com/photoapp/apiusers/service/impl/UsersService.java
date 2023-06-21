package com.photoapp.apiusers.service.impl;

import com.photoapp.apiusers.dto.UserDTO;
import com.photoapp.apiusers.model.Users;
import com.photoapp.apiusers.repository.UserRepository;
import com.photoapp.apiusers.service.UsersServices;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UsersService implements UsersServices {

  final
  UserRepository repository;
  final
  BCryptPasswordEncoder encoder;

  public UsersService(UserRepository repository,
      BCryptPasswordEncoder encoder) {
    this.repository = repository;
    this.encoder = encoder;
  }

  @Override
  public Users createUser(UserDTO userDTO) {
    Users model = new Users();

    BeanUtils.copyProperties(userDTO,model);
    model.setPassword(encoder.encode(userDTO.getPassword()));

    return repository.save(model);
  }
}
