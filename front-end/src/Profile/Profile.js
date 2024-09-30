import React, { useState, useContext, useEffect } from "react";
import axios from "axios";
import Dropzone from "react-dropzone";

import { toast, ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import "tailwindcss/tailwind.css"; // Ensure Tailwind is installed and configured
import { UserContext } from "../DataManagement/UserContext";

const Profile = () => {
  const { userId } = useContext(UserContext); // Get userId from context after login
  const [profileData, setProfileData] = useState({
    username: "",
    email: "",
    bio: "",
    location: "",
    profilePicture: null,
  });
  const [isUploading, setIsUploading] = useState(false);

  useEffect(() => {
    // Fetch user data when the component mounts
    const fetchUserProfile = async () => {
      try {
        const { data } = await axios.get(
          `http://localhost:8080/api/users/${userId}/profile`
        );
        setProfileData((prev) => ({ ...prev, ...data }));
      } catch (error) {
        toast.error("Failed to load user profile.");
      }
    };

    fetchUserProfile();
  }, [userId]);

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setProfileData({ ...profileData, [name]: value });
  };

  const handleDrop = (acceptedFiles) => {
    setProfileData({ ...profileData, profilePicture: acceptedFiles[0] });
  };

  const handleProfileUpdate = async (e) => {
    e.preventDefault();

    try {
      // Update user info
      await axios.put(`http://localhost:8080/api/users/${userId}/profile`, {
        username: profileData.username,
        email: profileData.email,
        bio: profileData.bio,
        location: profileData.location,
      });
      toast.success("Profile updated successfully!");

      // If profile picture is uploaded
      if (profileData.profilePicture) {
        const formData = new FormData();
        formData.append("profilePicture", profileData.profilePicture);
        setIsUploading(true);

        await axios.post(
          `http://localhost:8080/api/users/${userId}/uploadProfilePicture`,
          formData,
          {
            headers: { "Content-Type": "multipart/form-data" },
          }
        );

        toast.success("Profile picture uploaded successfully!");
        setIsUploading(false);
      }
    } catch (error) {
      toast.error("Error updating profile.");
      setIsUploading(false);
    }
  };

  return (
    <div className="container mx-auto p-4">
      <ToastContainer />
      <h2 className="text-2xl font-bold mb-4">Manage Your Profile</h2>
      <form onSubmit={handleProfileUpdate} className="space-y-6">
        {/* Username */}
        <div>
          <label className="block text-sm font-medium">Username</label>
          <input
            type="text"
            name="username"
            value={profileData.username}
            onChange={handleInputChange}
            className="mt-1 block w-full border border-gray-300 rounded-md shadow-sm p-2"
            required
          />
        </div>

        {/* Email */}
        <div>
          <label className="block text-sm font-medium">Email</label>
          <input
            type="email"
            name="email"
            value={profileData.email}
            onChange={handleInputChange}
            className="mt-1 block w-full border border-gray-300 rounded-md shadow-sm p-2"
            required
          />
        </div>

        {/* Bio */}
        <div>
          <label className="block text-sm font-medium">Bio</label>
          <textarea
            name="bio"
            value={profileData.bio}
            onChange={handleInputChange}
            className="mt-1 block w-full border border-gray-300 rounded-md shadow-sm p-2"
          />
        </div>

        {/* Location */}
        <div>
          <label className="block text-sm font-medium">Location</label>
          <input
            type="text"
            name="location"
            value={profileData.location}
            onChange={handleInputChange}
            className="mt-1 block w-full border border-gray-300 rounded-md shadow-sm p-2"
          />
        </div>

        {/* Profile Picture */}
        <div className="mt-4">
          <label className="block text-sm font-medium">Profile Picture</label>
          <Dropzone onDrop={handleDrop} multiple={false}>
            {({ getRootProps, getInputProps }) => (
              <div
                {...getRootProps()}
                className="border border-dashed border-gray-400 p-4 rounded-md mt-1 cursor-pointer"
              >
                <input {...getInputProps()} />
                {profileData.profilePicture ? (
                  <p>{profileData.profilePicture.name}</p>
                ) : (
                  <p>Drag 'n' drop a picture here, or click to select one</p>
                )}
              </div>
            )}
          </Dropzone>
        </div>

        {/* Submit Button */}
        <div>
          <button
            type="submit"
            disabled={isUploading}
            className="w-full bg-blue-500 text-white font-bold py-2 px-4 rounded-md"
          >
            {isUploading ? "Uploading..." : "Update Profile"}
          </button>
        </div>
      </form>
    </div>
  );
};

export default Profile;
