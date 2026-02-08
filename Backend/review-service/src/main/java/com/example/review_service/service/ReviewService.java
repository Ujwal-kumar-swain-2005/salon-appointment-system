package com.example.review_service.service;

import com.example.review_service.modal.Review;
import com.example.review_service.payload.ReviewRequest;
import com.example.review_service.payload.SalonDto;
import com.example.review_service.payload.UserDto;

import java.util.List;

public interface ReviewService {
    Review createReview(ReviewRequest req, UserDto userDto, SalonDto salonDto) throws Exception;

    List<Review> getReviewBySalonId(Long salonId);

    Review updateReview(ReviewRequest request, Long reviewId, Long userId) throws Exception;

    void deleteReview(Long reviewId, Long userId) throws Exception;
}