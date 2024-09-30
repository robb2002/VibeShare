import HomePage from "./HomeDashboard/HomePage";
import LoginSignupForm from "./Login/LoginSignupForm";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import Profile from "./Profile/Profile";
import { useState } from "react";
import { UserContext } from "./DataManagement/UserContext";

function App() {
  const [userId, setUserId] = useState(null);
  return (
    <UserContext.Provider value={{ userId, setUserId }}>
      <Router>
        <div>
          <Routes>
            <Route path="/" element={<LoginSignupForm />} />
            <Route path="/home" element={<HomePage />} />
            <Route path="/profile" element={<Profile />} />
            {/* You can add more routes for other components as needed */}
          </Routes>
        </div>
      </Router>
    </UserContext.Provider>
  );
}

export default App;
