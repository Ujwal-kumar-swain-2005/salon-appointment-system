import React from "react";
import "./App.css";
import { ThemeProvider } from "@mui/material/styles";
import greenTheme from "./theme/greenTheme";
import { Routes, Route, BrowserRouter } from "react-router-dom";

import Booking from "./Booking/Booking";
import Notification from "./Notification/Notification";
import NotFound from "./ErrorPage/NotFound";
import Home from "./Customer/Home/Home";

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
          
      </Routes>
      </BrowserRouter>
    </ThemeProvider>
    </>
  );
}

export default App;
