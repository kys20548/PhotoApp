package com.photoapp.apiusers.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class Users {

  @Id
  @GeneratedValue
  private long id;

  @Column(nullable=false, length=50)
  private String firstName;

  @Column(nullable=false, length=50)
  private String lastName;

  @Column(nullable=false, length=120, unique=true)
  private String email;

  // @Column(nullable=false, unique=true)
  // private String userId;

  @Column(nullable=false)
  private String password;

  // @Column(nullable = false)
  // private LocalDateTime createTime;
}
