import React from "react";
import { FiEdit2, FiMessageCircle } from "react-icons/fi";
import Post from "../Post/Post";

const HomePage = () => {
  return (
    <div className="flex flex-col h-screen">
      {/* Top bar: Profile, Edit, Message, Search */}
      <div className="flex justify-between items-center p-4 bg-gray-200 shadow-md space-x-6">
        {/* Left: User Profile with Edit Icon */}
        <div className="relative">
          <img
            src="https://via.placeholder.com/50"
            alt="User Profile"
            className="w-16 h-16 rounded-full"
          />
          {/* Edit Icon attached to the bottom right corner of the profile image */}
          <div className="absolute bottom-0 right-0 bg-white p-1 rounded-full shadow">
            <FiEdit2
              className="w-4 h-4 text-gray-700 cursor-pointer"
              title="Edit Profile"
            />
          </div>
        </div>

        {/* Center: Search Bar (without the icon) */}
        <div className="flex-1">
          <input
            type="text"
            placeholder="Search people..."
            className="w-full p-2 border border-gray-300 rounded-lg focus:outline-none focus:border-blue-500"
          />
        </div>

        {/* Right: Message Icon */}
        <div>
          <FiMessageCircle
            className="w-6 h-6 text-blue-500 cursor-pointer"
            title="Messages"
          />
        </div>
      </div>

      {/* Post Section */}
      <div className="flex-1 p-4 bg-gray-100 overflow-y-auto">
        <Post />
        <Post />
        {/* Add more <PostComponent /> here as needed */}
      </div>
    </div>
  );
};

export default HomePage;
