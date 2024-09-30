import React from "react";
import { FiHeart, FiMessageCircle } from "react-icons/fi";

const Post = () => {
  return (
    <div className="bg-white p-4 rounded-lg shadow-md mb-6">
      {" "}
      {/* Increased margin-bottom for more space */}
      {/* Post Header: Username and Message Icon */}
      <div className="flex justify-between items-center mb-4">
        <div className="flex items-center space-x-2">
          <img
            src="https://via.placeholder.com/40"
            alt="User Avatar"
            className="w-10 h-10 rounded-full"
          />
          <span className="font-semibold">Username</span>
        </div>
        <FiMessageCircle className="w-5 h-5 text-gray-500 cursor-pointer" />
      </div>
      {/* Post Content */}
      <div className="mb-4">
        <p>
          This is an example post content. You can add images, text, or anything
          here.
        </p>
        <img
          src="https://via.placeholder.com/200"
          alt="Post"
          className="w-full h-auto mt-2 rounded-md"
        />
      </div>
      {/* Post Footer: Like and Comment Icons */}
      <div className="flex space-x-6">
        {" "}
        {/* Increased space between Like and Comment */}
        <div className="flex items-center space-x-1 cursor-pointer">
          <FiHeart className="text-red-500" />
          <span>Like</span>
        </div>
        <div className="flex items-center space-x-1 cursor-pointer">
          <FiMessageCircle className="text-gray-500" />
          <span>Comment</span>
        </div>
      </div>
    </div>
  );
};

export default Post;
