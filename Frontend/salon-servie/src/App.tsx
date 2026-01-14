
import React from 'react'
import './App.css'
import { ThemeProvider } from '@mui/material/styles';
import greenTheme from './theme/greenTheme';
import Home from './Customer/Home/Home';
import SalonDetails from './Salon/SalonDetails/SalonDetails';
import { Book } from '@mui/icons-material';
import Booking from './Booking/Booking';
import Notification from './Notification/Notification';
import Navbar from './Navbar/Navbar';
function App() {
 

return (
   
    <ThemeProvider theme={greenTheme}>
        <Home />
          <SalonDetails />
          <Booking />
          <Notification/>
          <Navbar/>
    </ThemeProvider>
  );
}

export default App
