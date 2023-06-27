package com.photoapp.apiusers.service;

import com.photoapp.apiusers.model.Albums;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "albums-ms")
public interface AlbumsServiceClient {

  @GetMapping("/users/{id}/albums")
    // @Retry(name="albums-ws")
    // @CircuitBreaker(name="albums-ws", fallbackMethod="getAlbumsFallback")
  List<Albums> getAlbums(@PathVariable Long id);

  // default List<AlbumResponseModel> getAlbumsFallback(String id, Throwable exception) {
  // 	System.out.println("Param = " + id);
  // 	System.out.println("Exception class=" + exception.getClass().getName());
  // 	System.out.println("Exception took place: " + exception.getMessage());
  // 	return new ArrayList<>();
  //
  // }
}