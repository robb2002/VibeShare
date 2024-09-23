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

  const handleSignupClick = () => {
    setIsLogin(false);
  };

  const handleLoginClick = () => {
    setIsLogin(true);
  };

  const handleLoginChange = (e) => {
    setLoginData({ ...loginData, [e.target.name]: e.target.value });
  };

  const handleSignupChange = (e) => {
    setSignupData({ ...signupData, [e.target.name]: e.target.value });
  };

  const handleLoginSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await axios.post(
        "http://localhost:8080/api/users/login",
        loginData
      );
      toast.success("Login successful!");
      setTimeout(() => {
        navigate("/home");
      }, 1000);
    } catch (error) {
      const message =
        (error.response && error.response.data.message) ||
        "Login failed. Please try again.";
      toast.error(message);
    }
  };

  const handleSignupSubmit = async (e) => {
    e.preventDefault();
    if (signupData.password !== signupData.confirmPassword) {
      toast.error("Passwords do not match.");
      return;
    }
    try {
      const response = await axios.post(
        "http://localhost:8080/api/users/register",
        {
          email: signupData.email,
          password: signupData.password,
        }
      );
      toast.success("Registration successful! Please login now.");
      setTimeout(() => {
        setIsLogin(true);
      }, 2000);
    } catch (error) {
      const errorData = error.response?.data || {};
      if (errorData.message) {
        toast.error(errorData.message);
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
