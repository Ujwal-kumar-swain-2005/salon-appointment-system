import React from 'react'
import DrawerList from '../Admin/DrawerList'
import {
  HomeIcon,
  ClipboardDocumentListIcon,
  ScissorsIcon,
  PlusCircleIcon,
  CreditCardIcon,
  ArrowsRightLeftIcon,
  Squares2X2Icon,
  BellIcon,
  UserCircleIcon,
  ArrowRightOnRectangleIcon,
} from '@heroicons/react/24/outline'

import {
  HomeIcon as HomeSolid,
  ClipboardDocumentListIcon as BookingSolid,
  ScissorsIcon as ScissorsSolid,
  PlusCircleIcon as PlusSolid,
  CreditCardIcon as CreditSolid,
  ArrowsRightLeftIcon as TransactionSolid,
  Squares2X2Icon as CategorySolid,
  BellIcon as BellSolid,
  UserCircleIcon as UserSolid,
  ArrowRightOnRectangleIcon as LogoutSolid,
} from '@heroicons/react/24/solid'

type IconType = React.ComponentType<React.SVGProps<SVGSVGElement>>

interface MenuItem {
  name: string
  path: string
  icon: IconType
  activeIcon: IconType
}

interface SalonDrawerListProps {
  open: boolean
  onClose: () => void
}

const menuItems: MenuItem[] = [
  { name: 'Dashboard', path: '/salon-dashboard', icon: HomeIcon, activeIcon: HomeSolid },
  { name: 'Bookings', path: '/salon-dashboard/bookings', icon: ClipboardDocumentListIcon, activeIcon: BookingSolid },
  { name: 'Services', path: '/salon-dashboard/services', icon: ScissorsIcon, activeIcon: ScissorsSolid },
  { name: 'Add Service', path: '/salon-dashboard/add-service', icon: PlusCircleIcon, activeIcon: PlusSolid },
  { name: 'Payment', path: '/salon-dashboard/payment', icon: CreditCardIcon, activeIcon: CreditSolid },
  { name: 'Transactions', path: '/salon-dashboard/transactions', icon: ArrowsRightLeftIcon, activeIcon: TransactionSolid },
  { name: 'Categories', path: '/salon-dashboard/categories', icon: Squares2X2Icon, activeIcon: CategorySolid },
  { name: 'Notifications', path: '/salon-dashboard/notifications', icon: BellIcon, activeIcon: BellSolid },
  { name: 'Profile', path: '/salon-dashboard/profile', icon: UserCircleIcon, activeIcon: UserSolid },
  { name: 'Logout', path: '/logout', icon: ArrowRightOnRectangleIcon, activeIcon: LogoutSolid },
]

const SalonDrawerList: React.FC<SalonDrawerListProps> = ({ open, onClose }) => {
  return (
    <DrawerList
      menuItems={menuItems}
      open={open}
      onClose={onClose}
    />
  )
}

export default SalonDrawerList
