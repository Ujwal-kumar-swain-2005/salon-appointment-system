import React from "react";
import { StarIcon, MapPinIcon } from "@heroicons/react/24/solid";
import { useNavigate } from "react-router-dom";

const SalonCard: React.FC = () => {
  const navigate = useNavigate();

  return (
    <div
      onClick={() => navigate("/salon/2")}
      className="w-56 md:w-80 rounded-md bg-slate-100 shadow
                 hover:shadow-xl hover:-translate-y-1
                 transition-all duration-300 cursor-pointer"
    >
      {/* Image */}
      <div className="overflow-hidden rounded-t-md">
        <img
          className="w-full h-[15rem] object-cover transition-transform duration-300 hover:scale-105"
          src="https://i.pinimg.com/1200x/e0/3e/70/e03e70641a7a7a9ac4ae2530252da2ff.jpg"
          alt="Salon Hair"
        />
      </div>

      {/* Content */}
      <div className="p-5 space-y-2">
        <h1 className="font-semibold text-lg">
          Glamour Hair Studio
        </h1>

        {/* Description */}
        <p className="text-sm text-gray-600 line-clamp-2">
          Professional hair styling, coloring, and beauty services
          with modern techniques.
        </p>

        {/* Rating */}
        <div className="flex items-center gap-1">
          <StarIcon className="h-4 w-4 text-yellow-400" />
          <StarIcon className="h-4 w-4 text-yellow-400" />
          <StarIcon className="h-4 w-4 text-yellow-400" />
          <StarIcon className="h-4 w-4 text-yellow-400" />
          <StarIcon className="h-4 w-4 text-gray-300" />
          <span className="text-sm text-gray-600 ml-1">
            (4.0)
          </span>
        </div>

        {/* Location */}
        <div className="flex items-center gap-1 text-gray-600 text-sm">
          <MapPinIcon className="h-4 w-4" />
          <span>Ho Chi Minh City</span>
        </div>
      </div>
    </div>
  );
};

export default SalonCard;
