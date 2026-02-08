package com.example.review_service.controller;

import com.example.review_service.client.SalonFeignClient;
import com.example.review_service.client.UserFeignClient;
import com.example.review_service.modal.Review;
import com.example.review_service.payload.ReviewRequest;
import com.example.review_service.service.ReviewService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {
    private static final Logger log = LoggerFactory.getLogger(ReviewController.class);
    private final ReviewService reviewService;
    private final UserFeignClient userFeignClient;
    private final SalonFeignClient salonFeignClient;

    public ReviewController(ReviewService reviewService, UserFeignClient userFeignClient, SalonFeignClient salonFeignClient) {
        this.reviewService = reviewService;
        this.userFeignClient = userFeignClient;
        this.salonFeignClient = salonFeignClient;
    }

    @PostMapping("/salon/{salonId}")
    public ResponseEntity<Review> createReview(@RequestBody ReviewRequest request, @PathVariable Long salonId,
                                               @RequestHeader("Authorization") String token) throws Exception {

        log.info("======= DEBUGGING USER FEIGN CLIENT =======");

        // Get raw JSON first to see what's actually returned
        var rawResponse = userFeignClient.getUserInfoRaw(token);
        String rawJson = rawResponse.getBody();
        log.info("RAW JSON Response: {}", rawJson);

        // Now get the DTO
        var response = userFeignClient.getUserInfo(token);
        var userDto = response.getBody();

        log.info("UserDto after deserialization: {}", userDto);
        log.info("UserId: {}", userDto != null ? userDto.getId() : "null");
        log.info("User fullname: {}", userDto != null ? userDto.getFullName() : "null");
        log.info("User Email: {}", userDto != null ? userDto.getEmail() : "null");
        log.info("==========================================");

        if (userDto == null || userDto.getId() == null) {
            log.error("UserDto or UserId is null! Cannot create review.");
            return ResponseEntity.badRequest().build();
        }

        var salonDto = salonFeignClient.getSalonById(salonId).getBody();
        var review = reviewService.createReview(request, userDto, salonDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(review);
    }

      @GetMapping("/salon/{salonId}")
    public ResponseEntity<List<Review>> getReviewsBySalon(@PathVariable Long salonId,
                                                          @RequestHeader("Authorization") String token) throws Exception {

        userFeignClient.getUserInfo(token).getBody();
        var salonDto = salonFeignClient.getSalonById(salonId).getBody();

        return ResponseEntity.ok(reviewService.getReviewBySalonId(salonDto.getId()));
    }
    @PutMapping("/{reviewId}")
    public ResponseEntity<Review> updateReview(@PathVariable Long reviewId, @RequestBody ReviewRequest request,
                                               @RequestHeader("Authorization") String token) throws Exception {

        var userDto = userFeignClient.getUserInfo(token).getBody();
        return ResponseEntity.ok(reviewService.updateReview(request, reviewId, userDto.getId()));
    }
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long reviewId, @RequestHeader("Authorization") String token)
            throws Exception {
        var userDto = userFeignClient.getUserInfo(token).getBody();
        reviewService.deleteReview(reviewId, userDto.getId());
        return ResponseEntity.noContent().build();
    }
}
