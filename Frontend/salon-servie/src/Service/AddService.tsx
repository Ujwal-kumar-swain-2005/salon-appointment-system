import React, { useState } from 'react'
import type { ChangeEvent } from 'react'
const AddService: React.FC = () => {
  const [image, setImage] = useState<string | null>(null)

  const handleImageChange = (e: ChangeEvent<HTMLInputElement>) => {
    const file = e.target.files?.[0]
    if (file) {
      setImage(URL.createObjectURL(file))
    }
  }

  return (
    <div className="max-w-4xl bg-white rounded-xl shadow p-6">
      <h1 className="text-2xl font-bold mb-6">Add New Service</h1>

      <div className="grid grid-cols-1 md:grid-cols-3 gap-6">

        <div className="md:col-span-1">
          <label className="block text-sm font-medium mb-2">
            Service Image
          </label>

          <div className="border-2 border-dashed rounded-lg p-4 flex flex-col items-center justify-center text-center cursor-pointer hover:border-green-500 transition">
            {image ? (
              <img
                src={image}
                alt="Preview"
                className="h-40 w-full object-cover rounded"
              />
            ) : (
              <>
                <p className="text-gray-500 text-sm">
                  Click to upload image
                </p>
                <p className="text-xs text-gray-400">
                  PNG, JPG up to 5MB
                </p>
              </>
            )}

            <input
              type="file"
              accept="image/*"
              onChange={handleImageChange}
              className="hidden"
              id="serviceImage"
            />
          </div>

          <label
            htmlFor="serviceImage"
            className="block text-center mt-2 text-green-600 cursor-pointer text-sm font-medium"
          >
            Choose Image
          </label>
        </div>

        <div className="md:col-span-2 space-y-4">

     
          <div>
            <label className="block text-sm font-medium mb-1">
              Service Name
            </label>
            <input
              type="text"
              placeholder="Hair Cut"
              className="w-full border rounded-lg px-3 py-2 focus:outline-none focus:ring-2 focus:ring-green-500"
            />
          </div>
          <div>
            <label className="block text-sm font-medium mb-1">
              Category
            </label>
            <select className="w-full border rounded-lg px-3 py-2">
              <option>Hair</option>
              <option>Beard</option>
              <option>Facial</option>
              <option>Massage</option>
            </select>
          </div>
          <div className="grid grid-cols-2 gap-4">
            <div>
              <label className="block text-sm font-medium mb-1">
                Duration (minutes)
              </label>
              <input
                type="number"
                placeholder="60"
                className="w-full border rounded-lg px-3 py-2"
              />
            </div>

            <div>
              <label className="block text-sm font-medium mb-1">
                Price ($)
              </label>
              <input
                type="number"
                placeholder="25"
                className="w-full border rounded-lg px-3 py-2"
              />
            </div>
          </div>

          <div>
            <label className="block text-sm font-medium mb-1">
              Description
            </label>
            <textarea
              rows={4}
              placeholder="Describe the service..."
              className="w-full border rounded-lg px-3 py-2"
            />
          </div>

          {/* Status */}
          <div className="flex items-center gap-6">
            <label className="flex items-center gap-2">
              <input type="radio" name="status" defaultChecked />
              <span className="text-sm">Active</span>
            </label>
            <label className="flex items-center gap-2">
              <input type="radio" name="status" />
              <span className="text-sm">Inactive</span>
            </label>
          </div>

          {/* ACTIONS */}
          <div className="flex justify-end gap-3 pt-4">
            <button className="px-6 py-2 rounded-lg border hover:bg-gray-100">
              Cancel
            </button>
            <button className="px-6 py-2 rounded-lg bg-green-600 text-white hover:bg-green-700">
              Save Service
            </button>
          </div>

        </div>
      </div>
    </div>
  )
}

export default AddService
