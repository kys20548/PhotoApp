package com.photoapp.apiusers.service;

import com.photoapp.apiusers.model.Albums;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import java.util.ArrayList;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "albums-ms")
public interface AlbumsServiceClient {

  @GetMapping("/users/{id}/albums")
  @Retry(name="albums-ms")
  @CircuitBreaker(name="albums-ms", fallbackMethod="getAlbumsFallback")
  List<Albums> getAlbums(@PathVariable Long id);

  default List<Albums> getAlbumsFallback(Long id, Throwable exception) {
  	System.out.println("Param = " + id);
  	System.out.println("Exception class=" + exception.getClass().getName());
  	System.out.println("Exception took place: " + exception.getMessage());
  	return new ArrayList<>();

  }
}