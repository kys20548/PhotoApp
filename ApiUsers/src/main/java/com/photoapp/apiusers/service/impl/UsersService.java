package com.photoapp.apiusers.service.impl;

import com.photoapp.apiusers.dto.UserDTO;
import com.photoapp.apiusers.model.Albums;
import com.photoapp.apiusers.model.Users;
import com.photoapp.apiusers.repository.UserRepository;
import com.photoapp.apiusers.service.AlbumsServiceClient;
import com.photoapp.apiusers.service.UsersServices;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class UsersService implements UsersServices {

  final
  UserRepository repository;
  final
  BCryptPasswordEncoder encoder;

  final
  AlbumsServiceClient albumsServiceClient;

  public UsersService(UserRepository repository,
      BCryptPasswordEncoder encoder, AlbumsServiceClient albumsServiceClient) {
    this.repository = repository;
    this.encoder = encoder;
    this.albumsServiceClient = albumsServiceClient;
  }

  @Override
  public Users createUser(UserDTO userDTO) {
    Users model = new Users();

    BeanUtils.copyProperties(userDTO,model);
    model.setPassword(encoder.encode(userDTO.getPassword()));

    return repository.save(model);
  }

  @Override
  public Users getUserByEmail(String email) {
    return repository.findByEmail(email);
  }

  @Override
  public Users getUserById(Long id) {
    log.debug("Before calling albums Microservice");
    List<Albums> albumsList = albumsServiceClient.getAlbums(id);
    log.debug("After calling albums Microservice");
    Users user = repository.findById(id).get();
    user.setListAlbums(albumsList);
    return repository.findById(id).get();
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Users userEntity = this.getUserByEmail(username);

    if(userEntity == null) throw new UsernameNotFoundException(username);

    return new User(userEntity.getEmail(),userEntity.getPassword(),
        true, true, true, true, new ArrayList<>());
  }
}
