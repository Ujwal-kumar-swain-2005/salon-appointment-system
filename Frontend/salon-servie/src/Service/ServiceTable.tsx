import React, { useState } from 'react'
import {
    PencilSquareIcon,
    TrashIcon
} from '@heroicons/react/24/outline'

interface Service {
    id: number
    name: string
    image: string
    price: string
    duration: string
    status: 'Active' | 'Inactive'
}

const services: Service[] = [
    {
        id: 1,
        name: 'Hair Cut',
        image: 'https://images.unsplash.com/photo-1605497788044-5a32c7078486',
        price: '$20',
        duration: '30 min',
        status: 'Active',
    },
    {
        id: 2,
        name: 'Hair Coloring',
        image: 'https://images.unsplash.com/photo-1522335789203-aabd1fc54bc9',
        price: '$50',
        duration: '90 min',
        status: 'Inactive',
    },
    {
        id: 3,
        name: 'Facial Treatment',
        image: 'https://images.unsplash.com/photo-1596755389378-c31d21fd1273',
        price: '$35',
        duration: '45 min',
        status: 'Active',
    },
]

const ServiceTable: React.FC = () => {
    const [selectedService, setSelectedService] = useState<Service | null>(null)

    return (
        <div className="bg-white rounded-lg shadow p-5">
            <div className="flex items-center justify-between mb-4">
                <h1 className="text-2xl font-bold">Services</h1>
                <button className="bg-green-600 text-white px-4 py-2 rounded-lg hover:bg-green-700">
                    + Add Service
                </button>
            </div>
            <table className="w-full text-sm border rounded-lg overflow-hidden">
                <thead className="bg-gray-100 text-left">
                    <tr>
                        <th className="p-3">Service</th>
                        <th className="p-3">Price</th>
                        <th className="p-3">Duration</th>
                        <th className="p-3">Status</th>
                        <th className="p-3 text-right">Actions</th>
                    </tr>
                </thead>

                <tbody>
                    {services.map((service) => (
                        <tr
                            key={service.id}
                            onClick={() => setSelectedService(service)}
                            className={`border-t cursor-pointer transition
                ${selectedService?.id === service.id
                                    ? 'bg-green-50'
                                    : 'hover:bg-gray-50'
                                }
              `}
                        >

                            <td className="p-3 flex items-center gap-3">
                                <img
                                    src={service.image}
                                    alt={service.name}
                                    className="w-12 h-12 rounded-lg object-cover"
                                />
                                <span className="font-medium">
                                    {service.name}
                                </span>
                            </td>

                            <td className="p-3">{service.price}</td>
                            <td className="p-3">{service.duration}</td>

                            <td className="p-3">
                                <span
                                    className={`px-2 py-1 rounded-full text-xs font-medium
                    ${service.status === 'Active'
                                            ? 'bg-green-100 text-green-600'
                                            : 'bg-gray-200 text-gray-600'
                                        }
                  `}
                                >
                                    {service.status}
                                </span>
                            </td>

                            {/* Actions */}
                            <td className="p-3 text-right">
                                <div className="flex justify-end gap-3">
                                    <button
                                        onClick={(e: React.MouseEvent<HTMLButtonElement>) => {
                                            e.stopPropagation()
                                            alert('Edit service')
                                        }}
                                        className="text-blue-600 hover:text-blue-800"
                                    >
                                        <PencilSquareIcon className="w-5 h-5" />
                                    </button>

                                    <button
                                        onClick={(e: React.MouseEvent<HTMLButtonElement>) => {
                                            e.stopPropagation()
                                            alert('Delete service')
                                        }}
                                        className="text-red-500 hover:text-red-700"
                                    >
                                        <TrashIcon className="w-5 h-5" />
                                    </button>
                                </div>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>

            {/* Detail panel */}
            {selectedService && (
                <div className="mt-6 border-t pt-4 flex gap-4">
                    <img
                        src={selectedService.image}
                        alt={selectedService.name}
                        className="w-24 h-24 rounded-lg object-cover"
                    />

                    <div className="space-y-1 text-sm">
                        <p><b>Service:</b> {selectedService.name}</p>
                        <p><b>Price:</b> {selectedService.price}</p>
                        <p><b>Duration:</b> {selectedService.duration}</p>
                        <p>
                            <b>Status:</b>{' '}
                            <span className="font-medium">
                                {selectedService.status}
                            </span>
                        </p>
                    </div>
                </div>
            )}
        </div>
    )
}

export default ServiceTable
