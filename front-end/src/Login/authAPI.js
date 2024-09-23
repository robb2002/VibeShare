import axios from "axios";

// Login user
export const loginUser = async (formData) => {
  const response = await axios.post("http://localhost:8080/api/users/login", {
    email: formData.email,
    password: formData.password,
  });
  return response;
};

// Register user
export const registerUser = async (formData) => {
  const response = await axios.post(
    "http://localhost:8080/api/users/register",
    {
      email: formData.email,
      username: formData.email.split("@")[0], // Create username from email
      password: formData.password,
    }
  );
  return response;
};
