package com.photoapp.apiusers.service;

import com.photoapp.apiusers.dto.UserDTO;
import com.photoapp.apiusers.model.Users;

public interface UsersServices {
  Users createUser(UserDTO userDTO);

}
