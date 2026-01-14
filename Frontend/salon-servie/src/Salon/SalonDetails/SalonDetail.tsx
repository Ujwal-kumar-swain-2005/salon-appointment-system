import React from "react";

const SalonDetail: React.FC = () => {
  return (
    <div className="space-y-5 mb-20">
      <section className="grid grid-cols-2 gap-3">
        <div className="col-span-2">
          <img
            className="w-full rounded-md h-[15rem] object-cover"
            src="https://i.pinimg.com/1200x/be/a0/f1/bea0f1bf0b7140adb38915aca30e7718.jpg"
            alt="Salon main view"
          />
        </div>

        <div className="col-span-1">
          <img
            className="w-full rounded-md h-[15rem] object-cover"
            src="https://i.pinimg.com/1200x/3c/e4/ac/3ce4ac8f931c8a77addaf3c101d2fbf3.jpg"
            alt="Salon interior"
          />
        </div>

        <div className="col-span-1">
          <img
            className="w-full rounded-md h-[15rem] object-cover"
            src="https://i.pinimg.com/474x/16/69/b8/1669b8ab4c50d18e41f6d7f2c37f2d9a.jpg"
            alt="Salon service area"
          />
        </div>
      </section>

      <section>
        <div className="space-y-2">
          <h2 className="text-3xl font-semibold">
            Glamour Beauty Salon
          </h2>
          <p className="text-gray-600">
            123 Main Street, Cityville
          </p>
          <p className="text-gray-600">
            Open: 9:00 AM - 8:00 PM
          </p>
        </div>
      </section>
    </div>
  );
};

export default SalonDetail;
