import React from "react";
import videoSrc from "../../assets/videos/cottonbro-studio.mp4";

const Banner: React.FC = () => {
  return (
    <div className="w-full relative h-[80vh]">
      <video
        className="w-full h-full object-cover"
        src={videoSrc}
        autoPlay
        loop
        muted
        playsInline
      />

      <div className="absolute top-1/2 left-1/2 transform 
                      -translate-x-1/2 -translate-y-1/2 
                      text-center text-white">
        <h1 className="text-4xl md:text-6xl font-bold mb-4">
          Welcome to Our Salon
        </h1>
        <p className="text-lg md:text-2xl mb-8">
          Experience the best beauty services
        </p>
        <input
          type="text"
          placeholder="Search for services..."
          className="px-4 py-2 rounded w-64 md:w-96 text-black"
        />
      </div>
    </div>
  );
};

export default Banner;
