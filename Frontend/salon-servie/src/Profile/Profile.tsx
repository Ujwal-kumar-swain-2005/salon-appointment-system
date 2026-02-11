import React, { useState }   from 'react'
import type { ChangeEvent } from 'react'
import { CameraIcon } from '@heroicons/react/24/outline'

const Profile: React.FC = () => {
  const [avatar, setAvatar] = useState<string | null>(null)
  const [name, setName] = useState<string>('Salon Admin')
  const [email, setEmail] = useState<string>('admin@salon.com')
  const role: string = 'SALON_OWNER'

  const handleAvatarChange = (e: ChangeEvent<HTMLInputElement>) => {
    const file = e.target.files?.[0]
    if (file) {
      setAvatar(URL.createObjectURL(file))
    }
  }

  return (
    <div className="bg-white p-6 rounded-xl shadow max-w-3xl">
      <h1 className="text-xl font-bold mb-6">Profile</h1>

      {/* Avatar */}
      <div className="flex items-center gap-6 mb-6">
        <div className="relative group">
          <img
            src={avatar || 'https://via.placeholder.com/120'}
            alt="Avatar"
            className="w-28 h-28 rounded-full object-cover border"
          />

          <label className="absolute inset-0 bg-black/40 flex items-center justify-center rounded-full opacity-0 group-hover:opacity-100 cursor-pointer transition">
            <CameraIcon className="w-6 h-6 text-white" />
            <input
              type="file"
              className="hidden"
              accept="image/*"
              onChange={handleAvatarChange}
            />
          </label>
        </div>

        <div>
          <h2 className="text-lg font-semibold">{name}</h2>
          <p className="text-sm text-gray-500">{email}</p>
          <span className="inline-block mt-2 px-3 py-1 text-xs bg-green-100 text-green-600 rounded-full">
            {role}
          </span>
        </div>
      </div>
      <div className="space-y-4">
        <div>
          <label className="block text-sm font-medium mb-1">
            Full Name
          </label>
          <input
            value={name}
            onChange={(e: ChangeEvent<HTMLInputElement>) => setName(e.target.value)}
            className="w-full border rounded px-3 py-2 focus:ring-2 focus:ring-green-500 outline-none"
          />
        </div>

        <div>
          <label className="block text-sm font-medium mb-1">
            Email
          </label>
          <input
            value={email}
            onChange={(e: ChangeEvent<HTMLInputElement>) => setEmail(e.target.value)}
            className="w-full border rounded px-3 py-2 focus:ring-2 focus:ring-green-500 outline-none"
          />
        </div>

        <div className="flex justify-end">
          <button className="bg-green-600 text-white px-6 py-2 rounded hover:bg-green-700 transition">
            Save Changes
          </button>
        </div>
      </div>
    </div>
  )
}

export default Profile
