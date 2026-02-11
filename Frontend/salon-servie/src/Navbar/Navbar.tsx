import React, { useState } from "react";
import {
  BellIcon,
  UserCircleIcon,
  ChevronDownIcon,
  Bars3Icon,
} from "@heroicons/react/24/outline";
import Button from "@mui/material/Button";
import { useNavigate } from "react-router-dom";
interface User {
  username: string;
  role: "CUSTOMER" | "ADMIN" | "PARTNER";
}

interface NavbarProps {
  onToggle?: () => void;
}

const Navbar: React.FC<NavbarProps> = ({ onToggle }) => {
  const isAuthenticated: boolean = true;
  const navigate = useNavigate();
  const user: User = {
    username: "quang.dev",
    role: "CUSTOMER",
  };

  const [open, setOpen] = useState<boolean>(false);

  return (
    <nav className="sticky top-0 z-50 bg-white shadow-sm">
      <div className="max-w-7xl mx-auto px-5 py-3 flex items-center justify-between">
        <div className="flex items-center gap-10">
          <div className="md:hidden cursor-pointer" onClick={onToggle}>
            <Bars3Icon className="w-8 h-8 text-green-600" />
          </div>
          <h1 onClick={() => navigate("/")} className="text-2xl font-bold text-green-600 cursor-pointer">
            Salon<span className="text-pink-500">Booking</span>
          </h1>

          <ul className="hidden md:flex items-center gap-6 text-gray-600 font-medium">
            <li
              className="hover:text-green-600 cursor-pointer"
              onClick={() => navigate('/')}
            >
              Home
            </li>

            <li
              className="hover:text-green-600 cursor-pointer"
            >
              Services
            </li>

            <li
              className="hover:text-green-600 cursor-pointer"
              onClick={() => navigate('/booking')}
            >
              Booking
            </li>

            <li
              className="hover:text-green-600 cursor-pointer"
              onClick={() => navigate('/notification')}
            >
              Notification
            </li>
          </ul>
        </div>
        <div className="flex items-center gap-4 relative">
          <div className="relative cursor-pointer">
            <BellIcon className="w-6 h-6 text-gray-600 hover:text-green-600" />
            <span className="absolute -top-1 -right-1 w-2 h-2 bg-red-500 rounded-full" />
          </div>
          {!isAuthenticated ? (
            <Button
              variant="contained"
              color="primary"
              size="small"
              sx={{ borderRadius: 2 }}
            >
              Login
            </Button>
          ) : (
            <div className="relative">
              <div
                onClick={() => setOpen((prev) => !prev)}
                className="flex items-center gap-2 cursor-pointer px-3 py-1 rounded-lg hover:bg-slate-100"
              >
                <UserCircleIcon className="w-7 h-7 text-green-600" />
                <span className="text-sm font-medium text-gray-700">
                  {user.username}
                </span>
                <ChevronDownIcon className="w-4 h-4 text-gray-500" />
              </div>
              {open && (
                <div className="absolute right-0 mt-2 w-56 bg-white border rounded-xl shadow-lg overflow-hidden">
                  <div className="px-4 py-3 border-b">
                    <p className="text-sm font-semibold text-gray-800">
                      {user.username}
                    </p>
                    <p className="text-xs text-gray-500">
                      Customer Account
                    </p>
                  </div>
                  <ul className="text-sm text-gray-600">
                    <li
                      className="px-4 py-2 hover:bg-slate-100 cursor-pointer"
                      onClick={() => {
                        navigate('/salon-dashboard')
                        setOpen(false)
                      }}
                    >
                      Become Partner
                    </li>
                    <li
                      className="px-4 py-2 hover:bg-slate-100 cursor-pointer"
                      onClick={() => {
                        navigate('/booking')
                        setOpen(false)
                      }}
                    >
                      My Booking
                    </li>
                    <li className="px-4 py-2 hover:bg-slate-100 cursor-pointer">
                      Help & Support
                    </li>
                    <li
                      className="px-4 py-2 hover:bg-slate-100 cursor-pointer text-red-500"
                      onClick={() => {
                        setOpen(false)
                        console.log('Logout')
                      }}
                    >
                      Logout
                    </li>
                  </ul>
                </div>
              )}
            </div>
          )}
        </div>
      </div>
    </nav>
  );
};

export default Navbar;
