import React from "react";
import Button from "@mui/material/Button";
import AddIcon from "@mui/icons-material/Add";
import AccessTimeIcon from "@mui/icons-material/AccessTime";
interface ServiceCardProps {
  name: string;
  description: string;
  duration: string;
  price: number;
  imageUrl: string;
  onAdd?: () => void;
}
const ServiceCard: React.FC = () => {
  return (
    <div className="w-full bg-white rounded-lg border shadow-sm hover:shadow-md transition p-4">
      <div className="flex items-center justify-between gap-5">
    
        <div className="space-y-2 w-[65%]">
          <h2 className="text-lg md:text-xl font-semibold text-gray-800">
            Bridal Makeup
          </h2>

          <p className="text-sm text-gray-600 line-clamp-2">
            Experience the magic of our bridal makeup services, where we blend
            artistry and elegance to create a stunning look for your special
            day.
          </p>

          <div className="flex items-center gap-4 text-sm text-gray-700">
            <div className="flex items-center gap-1">
              <AccessTimeIcon fontSize="small" />
              <span>2 hrs</span>
            </div>

            <span className="text-green-600 font-semibold">$150</span>
          </div>
          <Button
            variant="contained"
            color="success"
            size="small"
            startIcon={<AddIcon />}
            sx={{ textTransform: "none", mt: 1 }}
          >
            Add
          </Button>
        </div>
        <div className="flex-shrink-0">
          <img
            className="w-28 h-28 md:w-32 md:h-32 object-cover rounded-md"
            src="https://i.pinimg.com/736x/95/b1/9b/95b19b60ed907c7347053e3af9d12ce9.jpg"
            alt="Service"
          />
        </div>
      </div>
    </div>
  );
};

export default ServiceCard;
