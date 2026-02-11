import React, { useState } from 'react'
import {
  BellIcon,
  ClockIcon,
} from '@heroicons/react/24/outline'

type Notification = {
  id: number
  title: string
  message: string
  time: string
  unread: boolean
}

const mockNotifications: Notification[] = [
  {
    id: 1,
    title: 'New Booking',
    message: 'Nguyen Van A booked Hair Cut service',
    time: '5 minutes ago',
    unread: true,
  },
  {
    id: 2,
    title: 'Payment Successful',
    message: 'Transaction #12345 completed',
    time: '1 hour ago',
    unread: false,
  },
  {
    id: 3,
    title: 'New Review',
    message: 'A customer left a 5⭐ review',
    time: 'Yesterday',
    unread: false,
  },
]

const Notifications: React.FC = () => {
  const [notifications, setNotifications] = useState<Notification[]>(mockNotifications)

  const markAsRead = (id: number): void => {
    setNotifications((prev) =>
      prev.map((n) =>
        n.id === id ? { ...n, unread: false } : n
      )
    )
  }

  return (
    <div className="bg-white p-6 rounded-xl shadow max-w-4xl">
      <h1 className="text-xl font-bold mb-4 flex items-center gap-2">
        <BellIcon className="w-6 h-6 text-green-600" />
        Notifications
      </h1>

      <ul className="space-y-3">
        {notifications.map((n) => (
          <li
            key={n.id}
            onClick={() => markAsRead(n.id)}
            className={`p-4 border rounded-lg cursor-pointer transition
              ${n.unread
                ? 'bg-green-50 border-green-200'
                : 'hover:bg-gray-50'}
            `}
          >
            <div className="flex justify-between items-start">
              <div>
                <p className="font-medium text-gray-800">
                  {n.title}
                </p>
                <p className="text-sm text-gray-600">
                  {n.message}
                </p>
              </div>

              {n.unread && (
                <span className="text-xs bg-green-600 text-white px-2 py-1 rounded-full">
                  NEW
                </span>
              )}
            </div>

            <div className="flex items-center gap-1 text-xs text-gray-400 mt-2">
              <ClockIcon className="w-4 h-4" />
              {n.time}
            </div>
          </li>
        ))}
      </ul>
    </div>
  )
}

export default Notifications
