import React from "react";
import {
  ExclamationTriangleIcon,
  HomeIcon,
} from "@heroicons/react/24/outline";
import Button from "@mui/material/Button";
import { useNavigate } from "react-router-dom";
const NotFound: React.FC = () => {
const navigate = useNavigate();

  return (
    <div className="min-h-[80vh] flex flex-col items-center justify-center text-center px-4">
      <ExclamationTriangleIcon className="w-20 h-20 text-red-500 mb-4" />
      <h1 className="text-6xl font-bold text-gray-800">
        404
      </h1>
      <p className="text-lg text-gray-600 mt-3 max-w-md">
        Oops! The page you are looking for doesn’t exist.
      </p>
      <div className="flex gap-4 mt-6">
        <Button
          variant="contained"
          color="primary"
          startIcon={<HomeIcon className="w-5 h-5" />}
          onClick={() => navigate("/")}
          sx={{ borderRadius: 2 }}
        >
          Go Home
        </Button>

        <Button
          variant="outlined"
          color="primary"
          onClick={() => navigate(-1)}
          sx={{ borderRadius: 2 }}
        >
          Go Back
        </Button>
      </div>
    </div>
  );
};

export default NotFound;
