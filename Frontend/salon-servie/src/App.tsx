import React from "react";
import "./App.css";
import { ThemeProvider } from "@mui/material/styles";
import greenTheme from "./theme/greenTheme";
import { Routes, Route, BrowserRouter } from "react-router-dom";

import Booking from "./Booking/Booking";
import Notification from "./Notification/Notification";
import NotFound from "./ErrorPage/NotFound";
import Home from "./Customer/Home/Home";
import Navbar from "./Navbar/Navbar";
import Payment from "./Payment/Payment";
import Profile from "./Profile/Profile";
function App() {
  return (
    <>
      <ThemeProvider theme={greenTheme}>
      <BrowserRouter>
        <Routes>
          <Route path="*" element={<NotFound />} />
          <Route path="/" element={<Home />} />
          <Route path="/booking" element={<Booking />} />
          <Route path="/notification" element={<Notification />} />
          <Route path = "/navbar" element = {<Navbar/>}/>
          <Route path="/payment" element={<Payment />} />
          <Route path = "/profile" element = {<Profile/>}/>
      </Routes>
      </BrowserRouter>
    </ThemeProvider>
    </>
  );
}

export default App;
