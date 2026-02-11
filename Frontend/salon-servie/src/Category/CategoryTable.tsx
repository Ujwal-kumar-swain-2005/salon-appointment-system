import React from 'react'

type Category = {
  id: number
  name: string
  image: string
}

type CategoryTableProps = {
  categories: Category[]
}

const CategoryTable: React.FC<CategoryTableProps> = ({ categories }) => {
  return (
    <div className="bg-white rounded-lg shadow">
      <table className="w-full text-sm">
        <thead className="bg-gray-100">
          <tr>
            <th className="p-3 text-left">Image</th>
            <th className="p-3 text-left">Category Name</th>
            <th className="p-3 text-left">Actions</th>
          </tr>
        </thead>

        <tbody>
          {categories.map((cat) => (
            <tr key={cat.id} className="border-t">
              <td className="p-3">
                <img
                  src={cat.image}
                  alt={cat.name}
                  className="w-14 h-14 object-cover rounded"
                />
              </td>
              <td className="p-3 font-medium">{cat.name}</td>
              <td className="p-3">
                <button className="text-blue-600 hover:underline mr-3">
                  Edit
                </button>
                <button className="text-red-600 hover:underline">
                  Delete
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  )
}

export default CategoryTable
