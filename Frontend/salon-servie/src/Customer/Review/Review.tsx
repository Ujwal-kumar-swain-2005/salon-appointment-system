import React from "react";
import StarIcon from "@mui/icons-material/Star";
import StarBorderIcon from "@mui/icons-material/StarBorder";

interface ReviewItem {
  id: number;
  name: string;
  rating: number; // 1–5
  comment: string;
  date: string;
}

const Review: React.FC = () => {
  const reviews: ReviewItem[] = [
    {
      id: 1,
      name: "John Doe",
      rating: 5,
      comment:
        "Amazing service! The staff was professional and the makeup was flawless.",
      date: "Dec 10, 2025",
    },
    {
      id: 2,
      name: "Anna Smith",
      rating: 4,
      comment: "Great experience overall. Friendly staff and clean salon.",
      date: "Nov 28, 2025",
    },
  ];

  return (
    <div className="pt-10 flex flex-col lg:flex-row gap-20">

      <section className="w-full md:w-1/2 lg:w-[40%] space-y-4">
        <h2 className="text-2xl font-semibold mb-5">Reviews</h2>

        {reviews.map((review) => (
          <div
            key={review.id}
            className="border p-5 rounded-lg shadow-sm bg-white space-y-2"
          >
            <div className="flex items-center justify-between">
              <h3 className="font-semibold text-gray-800">
                {review.name}
              </h3>
              <span className="text-xs text-gray-500">
                {review.date}
              </span>
            </div>
            <div className="flex items-center gap-1">
              {[1, 2, 3, 4, 5].map((i: number) =>
                i <= review.rating ? (
                  <StarIcon
                    key={i}
                    sx={{ fontSize: 18, color: "#facc15" }}
                  />
                ) : (
                  <StarBorderIcon
                    key={i}
                    sx={{ fontSize: 18, color: "#d1d5db" }}
                  />
                )
              )}
            </div>

            {/* Comment */}
            <p className="text-sm text-gray-600">
              {review.comment}
            </p>
          </div>
        ))}
      </section>

      {/* RIGHT – Summary */}
      <section className="w-full md:w-1/2 lg:w-[40%] space-y-4">
        <h2 className="text-2xl font-semibold mb-5">
          Rating Summary
        </h2>

        <div className="border rounded-lg p-5 shadow-sm bg-white space-y-4">
          <div className="flex items-center gap-3">
            <span className="text-4xl font-bold text-gray-800">
              4.5
            </span>

            <div>
              <div className="flex items-center gap-1">
                {[1, 2, 3, 4, 5].map((i: number) => (
                  <StarIcon
                    key={i}
                    sx={{ fontSize: 20, color: "#facc15" }}
                  />
                ))}
              </div>
              <p className="text-sm text-gray-500">
                Based on 128 reviews
              </p>
            </div>
          </div>

          {/* Rating bars */}
          {[5, 4, 3, 2, 1].map((star: number) => (
            <div
              key={star}
              className="flex items-center gap-2 text-sm"
            >
              <span>{star}★</span>
              <div className="w-full bg-gray-200 rounded h-2">
                <div
                  className="bg-yellow-400 h-2 rounded"
                  style={{ width: `${star * 15}%` }}
                />
              </div>
            </div>
          ))}
        </div>
      </section>
    </div>
  );
};

export default Review;
