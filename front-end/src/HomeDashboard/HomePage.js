import React, { useContext, useEffect, useState } from "react";
import { FiEdit2, FiMessageCircle } from "react-icons/fi";
import Post from "../Post/Post";
import { useNavigate } from "react-router";
import { UserContext } from "../DataManagement/UserContext";
import axios from "axios";

const HomePage = () => {
  const navigate = useNavigate();
  const { userId } = useContext(UserContext); // Get userId from context
  const [user, setUser] = useState(null);

  useEffect(() => {
    const fetchUserDetails = async () => {
      try {
        const response = await axios.get(
          `http://localhost:8080/api/users/${userId}`
        );
        setUser(response.data);
      } catch (error) {
        console.error("Failed to fetch user details:", error);
      }
    };

    if (userId) {
      fetchUserDetails();
    }
  }, [userId]);

  const handleEditClick = () => {
    navigate("/profile"); // Redirect to profile page
  };

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
              onClick={handleEditClick}
            />
          </div>
        </div>
        <span className="font-semibold">Username</span>
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
