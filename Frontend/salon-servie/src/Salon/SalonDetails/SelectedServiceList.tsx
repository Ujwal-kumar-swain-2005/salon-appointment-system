import React from "react";
import IconButton from "@mui/material/IconButton";
import DeleteIcon from "@mui/icons-material/Delete";
import AccessTimeIcon from "@mui/icons-material/AccessTime";

interface Service {
  id: number;
  name: string;
  duration: string;
  price: number;
}

const SelectedServiceList: React.FC = () => {
  
  const services: Service[] = [
    {
      id: 1,
      name: "Bridal Makeup",
      duration: "2 hrs",
      price: 150,
    },
    {
      id: 2,
      name: "Hair Styling",
      duration: "1.5 hrs",
      price: 80,
    },
  ];

  const totalPrice = services.reduce(
    (sum, service) => sum + service.price,
    0
  );

  return (
    <div className="py-5 space-y-4">
      {services.map((service) => (
        <div
          key={service.id}
          className="flex items-center justify-between border rounded-md p-3 hover:bg-gray-50 transition"
        >
          {/* Info */}
          <div className="space-y-1">
            <h3 className="font-medium text-gray-800 text-sm">
              {service.name}
            </h3>

            <div className="flex items-center gap-1 text-xs text-gray-500">
              <AccessTimeIcon fontSize="inherit" />
              <span>{service.duration}</span>
            </div>
          </div>
          <div className="flex items-center gap-2">
            <span className="font-semibold text-green-600 text-sm">
              ${service.price}
            </span>

            <IconButton size="small" color="error">
              <DeleteIcon fontSize="small" />
            </IconButton>
          </div>
        </div>
      ))}

      {/* Total */}
      <div className="flex items-center justify-between pt-3 border-t">
        <span className="font-semibold text-gray-700">Total</span>
        <span className="font-bold text-green-700">
          ${totalPrice}
        </span>
      </div>
    </div>
  );
};

export default SelectedServiceList;
