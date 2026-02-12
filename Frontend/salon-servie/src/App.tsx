import React from "react";
import "./App.css";
import { ThemeProvider } from "@mui/material/styles";
import greenTheme from "./theme/greenTheme";
import { Routes, Route, BrowserRouter } from "react-router-dom";
import CustomerRoutes from "./Route/CustomerRoutes";
import Booking from "./Booking/Booking";
import Notification from "./Notification/Notification";
import NotFound from "./ErrorPage/NotFound";
import Home from "./Customer/Home/Home";
import Navbar from "./Navbar/Navbar";
import Payment from "./Payment/Payment";
import Profile from "./Profile/Profile";
import AddService from "./Service/AddService";
import ServiceTable from "./Service/ServiceTable";
import Transaction from "./Transaction/Transaction";
import SalonDashboard from "./Salon/SalonDashboard";
import BookingTable from "./Booking/BookingTable";
import CategoryPage from "./Category/CategoryPage";
import Notifications from "./Notification/Notification";  
function App() {
  return (
    <>
      <ThemeProvider theme={greenTheme}>
      <BrowserRouter>
        <Routes>
          <Route path="/salon-dashboard" element={<SalonDashboard />}>
          <Route index element={<BookingTable />} /> {/* default */}
          <Route path="bookings" element={<BookingTable />} />
          <Route path="services" element={<ServiceTable />} />
          <Route path="add-service" element={<AddService />} />
          <Route path="transactions" element={<Transaction />} />
          <Route path="categories" element={<CategoryPage />} />
          <Route path="notifications" element={<Notifications />} />
          <Route path="profile" element={<Profile />} />
          <Route path="payment" element={<Payment />} />

        </Route>
     
        <Route path="*" element={<CustomerRoutes />} />
      </Routes>
      </BrowserRouter>
    </ThemeProvider>
    </>
  );
}

export default App;
