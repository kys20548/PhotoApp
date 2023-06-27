package com.photoapp.apiusers.service;

import com.photoapp.apiusers.dto.UserDTO;
import com.photoapp.apiusers.model.Users;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UsersServices extends UserDetailsService {
  Users createUser(UserDTO userDTO);
  Users getUserByEmail(String email);
  Users getUserById(Long id);

}
