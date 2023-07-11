/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.photoapp.apialbums.controller;

import com.photoapp.apialbums.model.Albums;
import com.photoapp.apialbums.service.AlbumsService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class AlbumsController {

  final
  AlbumsService albumsService;

  public AlbumsController(AlbumsService albumsService) {
    this.albumsService = albumsService;
  }

  @GetMapping("/{id}/albums")
  public List<Albums> userAlbums(@PathVariable Long id) {
    return albumsService.getAlbums(id);
  }
}
