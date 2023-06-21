package com.photoapp.apiusers.repository;

import com.photoapp.apiusers.model.Users;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public
interface UserRepository extends CrudRepository<Users, Long> {

}
