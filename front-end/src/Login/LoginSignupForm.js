import React, { useState, useEffect, useRef } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import { toast, ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import "./LoginSignupForm.css";

const LoginSignupForm = () => {
  const [isLogin, setIsLogin] = useState(true);
  const [loginData, setLoginData] = useState({
    usernameOrEmail: "",
    password: "",
  });
  const [signupData, setSignupData] = useState({
    email: "",
    username: "",
    password: "",
    confirmPassword: "",
  });

  const navigate = useNavigate();
  const loginInputRef = useRef(null);
  const signupInputRef = useRef(null);

  useEffect(() => {
    if (isLogin) {
      loginInputRef.current.focus();
    } else {
      signupInputRef.current.focus();
    }
  }, [isLogin]);

  const handleSignupClick = () => setIsLogin(false);
  const handleLoginClick = () => setIsLogin(true);

  const handleLoginChange = (e) => {
    setLoginData({ ...loginData, [e.target.name]: e.target.value });
  };

  const handleSignupChange = (e) => {
    setSignupData({ ...signupData, [e.target.name]: e.target.value });
  };

  const handleLoginSubmit = async (e) => {
    e.preventDefault();

    // Ensure username or email is being sent correctly
    const loginPayload = {
      username: loginData.usernameOrEmail.includes("@")
        ? null
        : loginData.usernameOrEmail, // If not an email, treat it as username
      email: loginData.usernameOrEmail.includes("@")
        ? loginData.usernameOrEmail
        : null, // If email, treat it as email
      password: loginData.password,
    };

    try {
      // Sending login request
      const { data, status } = await axios.post(
        "http://localhost:8080/api/users/login",
        loginPayload
      );

      // Check the response from backend
      if (status === 200 && data === "Login successful!") {
        toast.success("Login successful!");
        setTimeout(() => {
          navigate("/home");
        }, 1000);
      } else {
        toast.error("Login failed. Please try again.");
      }
    } catch (error) {
      // Handle backend error response
      if (error.response && error.response.data) {
        toast.error(error.response.data); // Backend sends error message directly
      } else {
        toast.error(
          "Login failed. Please check your credentials and try again."
        );
      }
    }
  };
  const handleSignupSubmit = async (e) => {
    e.preventDefault();

    // Check if passwords match
    if (signupData.password !== signupData.confirmPassword) {
      toast.error("Passwords do not match.");
      return;
    }

    try {
      // Sending signup request
      const { data, status } = await axios.post(
        "http://localhost:8080/api/users/register",
        {
          email: signupData.email,
          username: signupData.username,
          password: signupData.password,
        }
      );

      // Successful registration
      if (status === 200 && data === "User registered successfully!") {
        toast.success("Registration successful! Please login.");
        setTimeout(() => {
          setIsLogin(true); // Switch to login form after successful registration
        }, 2000);
      }
    } catch (error) {
      // Handle backend error response
      if (error.response && error.response.data) {
        toast.error(error.response.data); // Show the backend error message (e.g., user exists)
      } else {
        toast.error("Registration failed. Please try again.");
      }
    }
  };

  return (
    <div className="wrapper">
      <ToastContainer />
      <div className="title-text">
        <div className={`title ${isLogin ? "visible" : "hidden"}`}>
          Unlock Your Vibe.
        </div>
        <div className={`title ${!isLogin ? "visible" : "hidden"}`}>
          Your Vibe, Your Story. Start Here.
        </div>
      </div>
      <div className="form-container">
        <div className="slide-controls">
          <input
            type="radio"
            name="slide"
            id="login"
            checked={isLogin}
            onChange={handleLoginClick}
          />
          <input
            type="radio"
            name="slide"
            id="signup"
            checked={!isLogin}
            onChange={handleSignupClick}
          />
          <label htmlFor="login" className="slide login">
            Login
          </label>
          <label htmlFor="signup" className="slide signup">
            Signup
          </label>
          <div className="slider-tab"></div>
        </div>
        <div className="form-inner">
          <form
            className={`login ${isLogin ? "" : "hidden"}`}
            onSubmit={handleLoginSubmit}
          >
            <div className="field">
              <input
                ref={loginInputRef}
                type="text"
                name="usernameOrEmail"
                placeholder="Username or Email Address"
                value={loginData.usernameOrEmail}
                onChange={handleLoginChange}
                required
              />
            </div>
            <div className="field">
              <input
                type="password"
                name="password"
                placeholder="Password"
                value={loginData.password}
                onChange={handleLoginChange}
                required
              />
            </div>
            <div className="pass-link">
              <a href="#">Forgot password?</a>
            </div>
            <div className="field btn">
              <div className="btn-layer"></div>
              <input type="submit" value="Login" />
            </div>
            <div className="signup-link">
              Not a member?{" "}
              <a href="#" onClick={handleSignupClick}>
                Signup now
              </a>
            </div>
          </form>
          <form
            className={`signup ${!isLogin ? "" : "hidden"}`}
            onSubmit={handleSignupSubmit}
          >
            <div className="field">
              <input
                ref={signupInputRef}
                type="text"
                name="email"
                placeholder="Email Address"
                value={signupData.email}
                onChange={handleSignupChange}
                required
              />
            </div>
            <div className="field">
              <input
                type="text"
                name="username"
                placeholder="Username"
                value={signupData.username}
                onChange={handleSignupChange}
                required
              />
            </div>
            <div className="field">
              <input
                type="password"
                name="password"
                placeholder="Password"
                value={signupData.password}
                onChange={handleSignupChange}
                required
              />
            </div>
            <div className="field">
              <input
                type="password"
                name="confirmPassword"
                placeholder="Confirm password"
                value={signupData.confirmPassword}
                onChange={handleSignupChange}
                required
              />
            </div>
            <div className="field btn">
              <div className="btn-layer"></div>
              <input type="submit" value="Signup" />
            </div>
          </form>
        </div>
      </div>
    </div>
  );
};

export default LoginSignupForm;
