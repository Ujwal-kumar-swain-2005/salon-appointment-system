import {
  ScissorsIcon,
  SparklesIcon,
  HandRaisedIcon,
  HeartIcon,
  PaintBrushIcon,
  FaceSmileIcon
} from '@heroicons/react/24/solid'

import type { ComponentType, SVGProps } from 'react'
export type Service = {
  id: number
  title: string
  description: string
  icon: ComponentType<SVGProps<SVGSVGElement>>
}
export const services: Service[] = [
  {
    id: 1,
    title: 'Haircut',
    description: 'Professional haircuts to suit your style.',
    icon: ScissorsIcon
  },
  {
    id: 2,
    title: 'Facial',
    description: 'Rejuvenating facials for glowing skin.',
    icon: SparklesIcon
  },
  {
    id: 3,
    title: 'Manicure',
    description: 'Beautifully crafted manicures and pedicures.',
    icon: HandRaisedIcon
  },
  {
    id: 4,
    title: 'Massage Therapy',
    description: 'Relaxing massages to relieve stress and tension.',
    icon: HeartIcon
  },
  {
    id: 5,
    title: 'Hair Coloring',
    description: 'Vibrant hair colors to express your personality.',
    icon: PaintBrushIcon
  },
  {
    id: 6,
    title: 'Makeup Services',
    description: 'Professional makeup for special occasions.',
    icon: FaceSmileIcon
  }
]
