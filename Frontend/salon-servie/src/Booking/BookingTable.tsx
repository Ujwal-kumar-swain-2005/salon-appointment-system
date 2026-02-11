import React, { useState } from 'react'

type Booking = {
  id: number
  customer: string
  avatar: string
  service: string
  date: string
  time: string
  price: string
  status: string
}

const bookings: Booking[] = [
  {
    id: 1,
    customer: 'Nguyen Van A',
    avatar: 'https://randomuser.me/api/portraits/men/32.jpg',
    service: 'Hair Cut',
    date: '2025-02-12',
    time: '10:00 - 11:00',
    price: '$20',
    status: 'Confirmed',
  },
  {
    id: 2,
    customer: 'Tran Thi B',
    avatar: 'https://randomuser.me/api/portraits/women/44.jpg',
    service: 'Hair Coloring',
    date: '2025-02-13',
    time: '14:00 - 16:00',
    price: '$50',
    status: 'Pending',
  },
]

const BookingTable = () => {
  const [selectedBooking, setSelectedBooking] = useState<Booking | null>(null)

  return (
    <div className="bg-white rounded-lg shadow p-5 flex gap-6">
      {/* TABLE */}
      <div className="flex-1">
        <h1 className="text-2xl font-bold mb-4">Bookings</h1>

        <table className="w-full text-sm border rounded-lg overflow-hidden">
          <thead className="bg-gray-100 text-left">
            <tr>
              <th className="p-3">Customer</th>
              <th className="p-3">Service</th>
              <th className="p-3">Date</th>
              <th className="p-3">Time</th>
              <th className="p-3">Status</th>
            </tr>
          </thead>

          <tbody>
            {bookings.map((booking) => (
              <tr
                key={booking.id}
                onClick={() => setSelectedBooking(booking)}
                className="border-t cursor-pointer hover:bg-green-50 transition"
              >
                <td className="p-3 flex items-center gap-3">
                  <img
                    src={booking.avatar}
                    alt={booking.customer}
                    className="w-9 h-9 rounded-full object-cover"
                  />
                  <span className="font-medium">
                    {booking.customer}
                  </span>
                </td>

                <td className="p-3">{booking.service}</td>
                <td className="p-3">{booking.date}</td>
                <td className="p-3">{booking.time}</td>

                <td className="p-3">
                  <span
                    className={`px-2 py-1 rounded-full text-xs font-medium
                      ${
                        booking.status === 'Confirmed'
                          ? 'bg-green-100 text-green-600'
                          : 'bg-yellow-100 text-yellow-600'
                      }
                    `}
                  >
                    {booking.status}
                  </span>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>

      {/* DETAIL PANEL */}
      {selectedBooking && (
        <div className="w-80 border-l pl-5">
          <h2 className="text-lg font-semibold mb-4">
            Booking Details
          </h2>

          <div className="flex items-center gap-3 mb-4">
            <img
              src={selectedBooking.avatar}
              alt={selectedBooking.customer}
              className="w-14 h-14 rounded-full object-cover"
            />
            <div>
              <p className="font-semibold">
                {selectedBooking.customer}
              </p>
              <p className="text-sm text-gray-500">
                {selectedBooking.service}
              </p>
            </div>
          </div>

          <div className="space-y-2 text-sm">
            <p><b>Date:</b> {selectedBooking.date}</p>
            <p><b>Time:</b> {selectedBooking.time}</p>
            <p><b>Price:</b> {selectedBooking.price}</p>
            <p>
              <b>Status:</b>{' '}
              <span
                className={`font-medium ${
                  selectedBooking.status === 'Confirmed'
                    ? 'text-green-600'
                    : 'text-yellow-600'
                }`}
              >
                {selectedBooking.status}
              </span>
            </p>
          </div>

          <div className="mt-5 flex gap-2">
            <button className="flex-1 bg-green-600 text-white py-2 rounded hover:bg-green-700">
              Confirm
            </button>
            <button className="flex-1 bg-red-500 text-white py-2 rounded hover:bg-red-600">
              Cancel
            </button>
          </div>
        </div>
      )}
    </div>
  )
}

export default BookingTable
