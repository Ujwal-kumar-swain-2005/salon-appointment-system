import React from 'react'
import { useLocation, useNavigate } from 'react-router-dom'
import { XMarkIcon } from '@heroicons/react/24/outline'

interface MenuItem {
  name: string;
  path: string;
  icon: React.ElementType;
  activeIcon?: React.ElementType;
}

interface DrawerListProps {
  menuItems: MenuItem[];
  open: boolean;
  onClose: () => void;
}

const DrawerList: React.FC<DrawerListProps> = ({ menuItems, open, onClose }) => {
  const location = useLocation();
  const navigate = useNavigate();

  return (
    <>
      {open && (
        <div
          className="fixed inset-0 bg-black/40 z-40 lg:hidden"
          onClick={onClose}
        />
      )}

      <aside
        className={`
          fixed top-0 left-0 z-50 h-screen w-64 bg-slate-900 text-white
          transform transition-transform duration-300
          ${open ? 'translate-x-0' : '-translate-x-full'}
          lg:translate-x-0
        `}
      >
        <div className="px-6 py-5 border-b border-slate-700 flex items-center justify-between">
          <h1 className="text-xl font-bold text-green-400">
            Salon<span className="text-pink-400">Admin</span>
          </h1>

          <button onClick={onClose} className="lg:hidden">
            <XMarkIcon className="w-5 h-5" />
          </button>
        </div>

        <ul className="py-4 space-y-1">
          {menuItems.map((item) => {
            const isActive = location.pathname === item.path
            const Icon = isActive && item.activeIcon ? item.activeIcon : item.icon

            return (
              <li
                key={item.name}
                onClick={() => {
                  navigate(item.path)
                  onClose()
                }}
                className={`
                  px-6 py-3 cursor-pointer flex items-center gap-3 rounded-r-full
                  ${isActive
                    ? 'bg-green-600 text-white'
                    : 'text-slate-300 hover:bg-slate-800'}
                `}
              >
                <Icon className="w-5 h-5" />
                <span className="text-sm font-medium">{item.name}</span>
              </li>
            )
          })}
        </ul>

        <div className="absolute bottom-0 w-full px-6 py-4 border-t border-slate-700 text-sm text-slate-400">
          © 2025 Salon Booking
        </div>
      </aside>
    </>
  )
}

export default DrawerList
