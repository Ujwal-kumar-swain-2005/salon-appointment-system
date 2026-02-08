package com.example.review_service.client;

import com.example.review_service.payload.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
@FeignClient(name = "USER-SERVICE", url = "http://localhost:8081")
public interface UserFeignClient {

    @GetMapping("/profile")  // Changed from "/api/users/profile"
    ResponseEntity<UserDto> getUserInfo(@RequestHeader("Authorization") String token) throws Exception;

    @GetMapping("/profile")  // For debugging
    ResponseEntity<String> getUserInfoRaw(@RequestHeader("Authorization") String token) throws Exception;
}