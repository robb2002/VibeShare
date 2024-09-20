import React, { useState, useEffect, useRef } from "react";
import "./LoginSignupForm.css";

const LoginSignupForm = () => {
    const [isLogin, setIsLogin] = useState(true);

    // Refs to target the first input in login and signup forms
    const loginInputRef = useRef(null);
    const signupInputRef = useRef(null);

    useEffect(() => {
        if (isLogin) {
            // Focus on the first input in the login form
            loginInputRef.current.focus();
        } else {
            // Focus on the first input in the signup form
            signupInputRef.current.focus();
        }
    }, [isLogin]); // Runs every time the `isLogin` state changes

    const handleSignupClick = () => {
        setIsLogin(false);
    };

    const handleLoginClick = () => {
        setIsLogin(true);
    };

    return (
        <div className="wrapper">
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
                    <form className={`login ${isLogin ? "" : "hidden"}`}>
                        <div className="field">
                            <input
                                ref={loginInputRef} // Set focus to this input on login form
                                type="text"
                                placeholder="Email Address"
                                required
                            />
                        </div>
                        <div className="field">
                            <input type="password" placeholder="Password" required />
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
                    <form className={`signup ${!isLogin ? "" : "hidden"}`}>
                        <div className="field">
                            <input
                                ref={signupInputRef} // Set focus to this input on signup form
                                type="text"
                                placeholder="Email Address"
                                required
                            />
                        </div>
                        <div className="field">
                            <input type="password" placeholder="Password" required />
                        </div>
                        <div className="field">
                            <input type="password" placeholder="Confirm password" required />
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
