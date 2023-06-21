package com.photoapp.apiusers.service.impl;

import com.photoapp.apiusers.dto.UserDTO;
import com.photoapp.apiusers.model.Users;
import com.photoapp.apiusers.repository.UserRepository;
import com.photoapp.apiusers.service.UsersServices;
import java.util.UUID;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;


@Service
public class UsersService implements UsersServices {

  final
  UserRepository repository;

  public UsersService(UserRepository repository) {
    this.repository = repository;
  }

  @Override
  public Users createUser(UserDTO userDTO) {
    Users model = new Users();
    BeanUtils.copyProperties(userDTO,model);
    return repository.save(model);
  }
}
