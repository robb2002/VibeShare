import HomePage from "./HomeDashboard/HomePage";
import LoginSignupForm from "./Login/LoginSignupForm";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/home" element={<HomePage />} />

        <Route path="/" element={<LoginSignupForm />} />
      </Routes>
    </Router>
  );
}

export default App;
