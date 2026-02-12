import React from 'react'
import { Route, Routes } from 'react-router-dom'

import Home from '../Customer/Home/Home'
import Booking from '../Booking/Booking'
import Notification from '../Notification/Notification'
import SalonDetails from '../Salon/SalonDetails/SalonDetail'
import Navbar from '../Navbar/Navbar'
import NotFound from '../ErrorPage/NotFound'

const CustomerRoutes: React.FC = () => {
  return (
    <>
      <Navbar />
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/bookings" element={<Booking />} />
        <Route path="/notification" element={<Notification />} />
        <Route path="/salon/:id" element={<SalonDetails />} />
        <Route path="*" element={<NotFound />} />
      </Routes>
    </>
  )
}

export default CustomerRoutes
