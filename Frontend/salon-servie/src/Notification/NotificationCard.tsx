import React from "react";
import {
  BellIcon,
  CheckCircleIcon,
  ExclamationTriangleIcon,
} from "@heroicons/react/24/outline";

type NotificationType = "info" | "success" | "warning";

interface NotificationCardProps {
  type?: NotificationType;
  title?: string;
  message?: string;
  time?: string;
  isRead?: boolean;
}

const NotificationCard: React.FC<NotificationCardProps> = ({
  type = "info",
  title = "Notification",
  message = "This is a notification message.",
  time = "2 minutes ago",
  isRead = false,
}) => {
  const iconMap: Record<NotificationType, React.ComponentType<React.SVGProps<SVGSVGElement>>> =
    {
      info: BellIcon,
      success: CheckCircleIcon,
      warning: ExclamationTriangleIcon,
    };

  const colorMap: Record<NotificationType, string> = {
    info: "text-blue-500 bg-blue-100",
    success: "text-green-500 bg-green-100",
    warning: "text-yellow-500 bg-yellow-100",
  };

  const Icon = iconMap[type];

  return (
    <div
      className={`flex gap-4 p-4 rounded-xl border transition cursor-pointer
        ${isRead ? "bg-white" : "bg-slate-50"}
        hover:shadow-md`}
    >
      <div className={`p-2 rounded-full ${colorMap[type]}`}>
        <Icon className="w-6 h-6" />
      </div>
      <div className="flex-1">
        <div className="flex justify-between items-center">
          <h4 className="font-semibold text-gray-800">
            {title}
          </h4>
          <span className="text-xs text-gray-400">
            {time}
          </span>
        </div>

        <p className="text-sm text-gray-600 mt-1">
          {message}
        </p>
      </div>
      {!isRead && (
        <span className="w-2 h-2 bg-green-500 rounded-full mt-2" />
      )}
    </div>
  );
};

export default NotificationCard;
