import React, { useState, useEffect, useContext } from "react";
import Dropzone from "react-dropzone";
import { FiEdit3 } from "react-icons/fi";
import { AiOutlinePlus } from "react-icons/ai";
import { toast, ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import "tailwindcss/tailwind.css";
import axios from "axios";
import { UserContext } from "../DataManagement/UserContext";

const Profile = () => {
  const { userId } = useContext(UserContext); // Get userId from context
  const [profileData, setProfileData] = useState({
    username: "",
    bio: "",
    location: "",
    profilePicture: null,
  });

  useEffect(() => {
    const fetchUserProfile = async () => {
      try {
        const { data } = await axios.get(
          `http://localhost:8080/api/users/${userId}/profile`
        );
        setProfileData((prev) => ({ ...prev, ...data }));
      } catch (error) {
        toast.error("Failed to load user profile.", { autoClose: false });
      }
    };

    if (userId) {
      fetchUserProfile();
    }
  }, [userId]);

  const handleDrop = (acceptedFiles) => {
    setProfileData({ ...profileData, profilePicture: acceptedFiles[0] });
    toast.success("Profile picture uploaded!");
  };

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setProfileData({ ...profileData, [name]: value });
  };

  const handleProfileUpdate = async () => {
    try {
      // Update user info
      await axios.put(`http://localhost:8080/api/users/${userId}/profile`, {
        username: profileData.username,
        bio: profileData.bio,
        location: profileData.location,
      });
      toast.success("Profile updated successfully!");

      // If profile picture is uploaded
      if (profileData.profilePicture) {
        const formData = new FormData();
        formData.append("profilePicture", profileData.profilePicture);

        await axios.post(
          `http://localhost:8080/api/users/${userId}/uploadProfilePicture`,
          formData,
          {
            headers: { "Content-Type": "multipart/form-data" },
          }
        );

        toast.success("Profile picture uploaded successfully!");
      }
    } catch (error) {
      toast.error("Error updating profile.");
    }
  };

  return (
    <div className="flex justify-center items-center min-h-screen bg-gray-100">
      <ToastContainer />
      <div className="w-full max-w-md p-6 bg-white rounded-lg shadow-lg space-y-6">
        <h2 className="text-center text-2xl font-bold">Update Profile Here</h2>

        {/* Profile Picture Section */}
        <div className="relative w-32 h-32 mx-auto">
          <Dropzone onDrop={handleDrop} multiple={false}>
            {({ getRootProps, getInputProps }) => (
              <div
                {...getRootProps()}
                className="w-full h-full rounded-full border-2 border-gray-300 flex items-center justify-center cursor-pointer relative"
              >
                <input {...getInputProps()} />
                {profileData.profilePicture ? (
                  <img
                    src={URL.createObjectURL(profileData.profilePicture)}
                    alt="Profile"
                    className="w-full h-full rounded-full object-cover"
                  />
                ) : (
                  <div className="text-gray-500 text-2xl">+</div>
                )}
                <div className="absolute bottom-0 right-0 bg-blue-500 text-white p-1 rounded-full">
                  <AiOutlinePlus size={20} />
                </div>
              </div>
            )}
          </Dropzone>
        </div>

        {/* Username Section */}
        <div className="space-y-2">
          <label className="text-sm font-medium">Username</label>
          <div className="relative">
            <input
              type="text"
              name="username"
              value={profileData.username}
              onChange={handleInputChange}
              className="w-full border-b-2 border-gray-300 focus:outline-none focus:border-blue-500 p-2 text-lg"
              placeholder="Enter your username"
            />
            <FiEdit3 className="absolute right-2 top-2 text-gray-400" />
          </div>
        </div>

        {/* Bio Section */}
        <div className="space-y-2">
          <label className="text-sm font-medium">Bio</label>
          <div className="relative">
            <input
              type="text"
              name="bio"
              value={profileData.bio}
              onChange={handleInputChange}
              className="w-full border-b-2 border-gray-300 focus:outline-none focus:border-blue-500 p-2 text-lg"
              placeholder="Add your bio"
            />
            <FiEdit3 className="absolute right-2 top-2 text-gray-400" />
          </div>
        </div>

        {/* Location Section */}
        <div className="space-y-2">
          <label className="text-sm font-medium">Location</label>
          <div className="relative">
            <input
              type="text"
              name="location"
              value={profileData.location}
              onChange={handleInputChange}
              className="w-full border-b-2 border-gray-300 focus:outline-none focus:border-blue-500 p-2 text-lg"
              placeholder="Add your location"
            />
            <FiEdit3 className="absolute right-2 top-2 text-gray-400" />
          </div>
        </div>

        {/* Submit Button */}
        <div>
          <button
            onClick={handleProfileUpdate}
            className="w-full bg-blue-500 text-white font-bold py-2 px-4 rounded-lg mt-4"
          >
            Update Profile
          </button>
        </div>
      </div>
    </div>
  );
};

export default Profile;
