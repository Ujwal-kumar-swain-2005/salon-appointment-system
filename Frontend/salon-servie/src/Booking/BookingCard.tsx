import React from 'react'

import {
  CalendarDaysIcon,
  ClockIcon,
} from "@heroicons/react/24/outline";
import Button from "@mui/material/Button";

const BookingCard: React.FC = () => {
  return (
    <div className="bg-white rounded-xl shadow-md p-5 md:flex gap-5 hover:shadow-lg transition">
  
      <img
        src="https://images.unsplash.com/photo-1600948836101-f9ffda59d250"
        alt="Salon"
        className="w-full md:w-40 h-40 object-cover rounded-lg"
      />
      <div className="flex-1 flex flex-col justify-between">
        <div className="space-y-2">
          <h2 className="text-2xl font-semibold text-gray-800">
            Luxury Beauty Salon
          </h2>
          <ul className="text-gray-600 text-sm list-disc list-inside">
            <li>Hair Cut</li>
            <li>Beard Trim</li>
            <li>Facial Treatment</li>
          </ul>
          <div className="flex flex-wrap items-center gap-4 text-sm text-gray-500 mt-2">
            <div className="flex items-center gap-1">
              <CalendarDaysIcon className="w-5 h-5 text-green-500" />
              <span>25 Dec 2025</span>
            </div>

            <div className="flex items-center gap-1">
              <ClockIcon className="w-5 h-5 text-green-500" />
              <span>
                10:30 AM <span className="mx-1">→</span> 11:30 AM
              </span>
            </div>
          </div>
        </div>
        <div className="flex items-center justify-between mt-4">
          <span className="text-xl font-bold text-green-500">
            $45.00
          </span>

          <Button
            variant="contained"
            color="error"
            size="small"
            sx={{ borderRadius: 2 }}
          >
            Cancel
          </Button>
        </div>
      </div>
    </div>
  );
};

export default BookingCard;
