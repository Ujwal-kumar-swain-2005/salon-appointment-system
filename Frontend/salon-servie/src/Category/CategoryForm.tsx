import React, { useState } from 'react'

type Category = {
  id: number
  name: string
  image: string
}

type CategoryFormProps = {
  onAdd: (category: Category) => void
}

const CategoryForm: React.FC<CategoryFormProps> = ({ onAdd }) => {
  const [name, setName] = useState<string>('')
  const [image, setImage] = useState<File | null>(null)

  const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault()

    if (!name || !image) return

    const newCategory: Category = {
      id: Date.now(),
      name,
      image: URL.createObjectURL(image),
    }

    onAdd(newCategory)
    setName('')
    setImage(null)
  }

  const handleFileChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    if (e.target.files && e.target.files[0]) {
      setImage(e.target.files[0])
    }
  }

  return (
    <form
      onSubmit={handleSubmit}
      className="bg-white p-5 rounded-lg shadow mb-6"
    >
      <h2 className="text-lg font-semibold mb-4">Add Category</h2>

      <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
        {/* Category Name */}
        <input
          type="text"
          placeholder="Category name"
          value={name}
          onChange={(e: React.ChangeEvent<HTMLInputElement>) =>
            setName(e.target.value)
          }
          className="border p-2 rounded"
        />

        {/* Image Upload */}
        <input
          type="file"
          accept="image/*"
          onChange={handleFileChange}
          className="border p-2 rounded"
        />
      </div>

      <button
        type="submit"
        className="mt-4 bg-green-600 text-white px-4 py-2 rounded hover:bg-green-700"
      >
        Add Category
      </button>
    </form>
  )
}

export default CategoryForm
