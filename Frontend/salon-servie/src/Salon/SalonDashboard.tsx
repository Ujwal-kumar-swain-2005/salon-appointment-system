import React, { useState } from 'react'
import { Outlet } from 'react-router-dom'
import Navbar from '../Navbar/Navbar'

import SalonDrawerList from './SalonDrawerList'
const SalonDashboard: React.FC = () => {
  const [open, setOpen] = useState<boolean>(false)

  return (
    <div className="min-h-screen bg-gray-100">
     
      <Navbar onToggle={() => setOpen(!open)} />

      <div className="flex">
   
        <SalonDrawerList open={open} onClose={() => setOpen(false)} />

        <main className="flex-1 p-6 mt-16 lg:ml-64">
          <Outlet />
        </main>
      </div>
    </div>
  )
}
export default SalonDashboard;