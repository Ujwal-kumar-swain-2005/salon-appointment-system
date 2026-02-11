import React from 'react'
import {
  CreditCardIcon,
  BanknotesIcon,
  CheckCircleIcon,
  ClockIcon,
} from '@heroicons/react/24/outline'

type PaymentType = {
  id: number
  customer: string
  method: string
  amount: string
  date: string
  status: 'Paid' | 'Pending'
}

const payments: PaymentType[] = [
  {
    id: 1,
    customer: 'Nguyen Van A',
    method: 'Credit Card',
    amount: '$20',
    date: '2025-02-12',
    status: 'Paid',
  },
  {
    id: 2,
    customer: 'Tran Thi B',
    method: 'Cash',
    amount: '$50',
    date: '2025-02-13',
    status: 'Pending',
  },
]

const Payment: React.FC = () => {
  return (
    <div className="space-y-6">
      {/* HEADER */}
      <h1 className="text-2xl font-bold">Payments</h1>

      {/* SUMMARY */}
      <div className="grid grid-cols-1 md:grid-cols-3 gap-5">
        <div className="bg-white p-5 rounded-xl shadow flex items-center gap-4">
          <BanknotesIcon className="w-10 h-10 text-green-600" />
          <div>
            <p className="text-sm text-gray-500">Total Revenue</p>
            <h2 className="text-xl font-bold">$3,250</h2>
          </div>
        </div>

        <div className="bg-white p-5 rounded-xl shadow flex items-center gap-4">
          <CreditCardIcon className="w-10 h-10 text-blue-600" />
          <div>
            <p className="text-sm text-gray-500">Online Payments</p>
            <h2 className="text-xl font-bold">$2,100</h2>
          </div>
        </div>

        <div className="bg-white p-5 rounded-xl shadow flex items-center gap-4">
          <BanknotesIcon className="w-10 h-10 text-yellow-600" />
          <div>
            <p className="text-sm text-gray-500">Cash Payments</p>
            <h2 className="text-xl font-bold">$1,150</h2>
          </div>
        </div>
      </div>
      <div className="bg-white rounded-xl shadow p-5">
        <h2 className="text-lg font-semibold mb-4">Recent Payments</h2>

        <table className="w-full text-sm border">
          <thead className="bg-gray-100">
            <tr>
              <th className="p-3 text-left">Customer</th>
              <th className="p-3 text-left">Method</th>
              <th className="p-3 text-left">Amount</th>
              <th className="p-3 text-left">Date</th>
              <th className="p-3 text-left">Status</th>
            </tr>
          </thead>

          <tbody>
            {payments.map((p) => (
              <tr key={p.id} className="border-t hover:bg-gray-50">
                <td className="p-3 font-medium">{p.customer}</td>
                <td className="p-3">{p.method}</td>
                <td className="p-3 font-semibold">{p.amount}</td>
                <td className="p-3">{p.date}</td>
                <td className="p-3">
                  <span
                    className={`px-2 py-1 rounded-full text-xs flex items-center gap-1 w-fit
                      ${
                        p.status === 'Paid'
                          ? 'bg-green-100 text-green-600'
                          : 'bg-yellow-100 text-yellow-600'
                      }
                    `}
                  >
                    {p.status === 'Paid' ? (
                      <CheckCircleIcon className="w-4 h-4" />
                    ) : (
                      <ClockIcon className="w-4 h-4" />
                    )}
                    {p.status}
                  </span>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  )
}

export default Payment
