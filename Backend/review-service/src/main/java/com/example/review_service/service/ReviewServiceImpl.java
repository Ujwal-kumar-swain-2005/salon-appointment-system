package com.example.review_service.service;

import com.example.review_service.modal.Review;
import com.example.review_service.payload.ReviewRequest;
import com.example.review_service.payload.SalonDto;
import com.example.review_service.payload.UserDto;
import com.example.review_service.repository.ReviewRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Override
    public Review createReview(ReviewRequest req, UserDto userDto, SalonDto salonDto) throws Exception {
        Review review = new Review();
    if(userDto == null){
        throw new Exception("user is not null");
    }
        review.setReviewText(req.getReviewText());
        review.setRating(req.getRating());
        review.setUserId(userDto.getId());
        review.setSalonId(salonDto.getId());

        return reviewRepository.save(review);
    }

    @Override
    public List<Review> getReviewBySalonId(Long salonId) {
        return reviewRepository.findBySalonId(salonId);
    }
    @Override
    public Review updateReview(ReviewRequest request, Long reviewId, Long userId) throws Exception {

        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new Exception("Review not found"));
        if (!review.getUserId().equals(userId)) {
            throw new Exception("You are not allowed to update this review");
        }

        review.setReviewText(request.getReviewText());
        review.setRating(request.getRating());

        return reviewRepository.save(review);
    }

    @Override
    public void deleteReview(Long reviewId, Long userId) throws Exception {

        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new Exception("Review not found"));
        if (!review.getUserId().equals(userId)) {
            throw new Exception("You are not allowed to delete this review");
        }

        reviewRepository.delete(review);
    }
}