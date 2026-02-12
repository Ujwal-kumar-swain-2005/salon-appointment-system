import React, { useState } from 'react'

interface TransactionType {
  id: number
  customer: string
  service: string
  date: string
  amount: string
  method: string
  status: 'Paid' | 'Pending'
}

const transactions: TransactionType[] = [
  {
    id: 1,
    customer: 'Nguyen Van A',
    service: 'Hair Cut',
    date: '2025-02-10',
    amount: '$25',
    method: 'Credit Card',
    status: 'Paid',
  },
  {
    id: 2,
    customer: 'Tran Thi B',
    service: 'Hair Coloring',
    date: '2025-02-11',
    amount: '$60',
    method: 'Cash',
    status: 'Pending',
  },
]

const Transaction: React.FC = () => {
  const [selected, setSelected] = useState<TransactionType | null>(null)

  return (
    <div className="bg-white rounded-xl shadow p-6 flex gap-6">
      <div className="flex-1">
        <h1 className="text-2xl font-bold mb-4">Transactions</h1>

        <table className="w-full text-sm border">
          <thead className="bg-gray-100">
            <tr>
              <th className="p-3 text-left">Customer</th>
              <th className="p-3 text-left">Service</th>
              <th className="p-3 text-left">Date</th>
              <th className="p-3 text-left">Method</th>
              <th className="p-3 text-left">Amount</th>
              <th className="p-3 text-left">Status</th>
            </tr>
          </thead>

          <tbody>
            {transactions.map((tx) => (
              <tr
                key={tx.id}
                onClick={() => setSelected(tx)}
                className="border-t cursor-pointer hover:bg-green-50 transition"
              >
                <td className="p-3 font-medium">{tx.customer}</td>
                <td className="p-3">{tx.service}</td>
                <td className="p-3">{tx.date}</td>
                <td className="p-3">{tx.method}</td>
                <td className="p-3 font-semibold">{tx.amount}</td>
                <td className="p-3">
                  <span
                    className={`px-2 py-1 rounded-full text-xs ${
                      tx.status === 'Paid'
                        ? 'bg-green-100 text-green-600'
                        : 'bg-yellow-100 text-yellow-600'
                    }`}
                  >
                    {tx.status}
                  </span>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
      {selected && (
        <div className="w-80 border-l pl-5">
          <h2 className="text-lg font-semibold mb-3">
            Transaction Details
          </h2>

          <div className="space-y-2 text-sm">
            <p><b>Customer:</b> {selected.customer}</p>
            <p><b>Service:</b> {selected.service}</p>
            <p><b>Date:</b> {selected.date}</p>
            <p><b>Payment Method:</b> {selected.method}</p>
            <p><b>Amount:</b> {selected.amount}</p>
            <p>
              <b>Status:</b>{' '}
              <span
                className={`font-medium ${
                  selected.status === 'Paid'
                    ? 'text-green-600'
                    : 'text-yellow-600'
                }`}
              >
                {selected.status}
              </span>
            </p>
          </div>

          {selected.status === 'Pending' && (
            <button className="w-full mt-5 bg-green-600 text-white py-2 rounded-lg hover:bg-green-700">
              Mark as Paid
            </button>
          )}
        </div>
      )}
    </div>
  )
}

export default Transaction
