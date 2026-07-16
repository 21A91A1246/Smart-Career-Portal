import { Routes, Route, Navigate } from "react-router-dom";

import Navbar from "./components/Navbar";
import ProtectedRoute from "./components/ProtectedRoute";

import UserDashboard from "./pages/user/UserDashboard";
import RecruiterDashboard from "./pages/recruiter/RecruiterDashboard";
import { useAuth } from "./context/AuthContext";
import UserProfile from "./pages/user/UserProfile";
import Jobs from "./pages/user/Jobs";
import AppliedJobs from "./pages/user/AppliedJobs";

import PostJob from "./pages/recruiter/PostJob";
import MyJobs from "./pages/recruiter/MyJobs";
import Register from "./pages/auth/Register";
import Login from "./pages/auth/Login";
import Applicants from "./pages/recruiter/Applicants";
import PageNotFound from "./components/PageNotFound";
import Footer from "./components/Footer";

function App() {
  const { user } = useAuth();

  return (
    <div className="app-container">
      <Navbar />

      <div className="page-content">
      <Routes >
        <Route
          path="/"
          element={
            user ? (
              <Navigate
                to={
                  user.role === "RECRUITER"
                    ? "/recruiter"
                    : "/user"
                }
              />
            ) : (
              <Navigate to="/login" />
            )
          }
        />

        <Route
          path="/user"
          element={
            <ProtectedRoute role="USER">
              <UserDashboard />
            </ProtectedRoute>
          }
        />

        <Route
          path="/recruiter"
          element={
            <ProtectedRoute role="RECRUITER">
              <RecruiterDashboard />
            </ProtectedRoute>
          }
        />

        {/* auth routs */}
        <Route
          path="/register"
          element={<Register />}
        />
        <Route
          path="/login"
          element={<Login />}
        />

        <Route
          path="/user/profile"
          element={
            <ProtectedRoute role="USER">
              <UserProfile />
            </ProtectedRoute>
          }
        />
        <Route
          path="/recruiter/applicants/:jobId"
          element={
            <ProtectedRoute
              role="RECRUITER"
            >
              <Applicants />
            </ProtectedRoute>
          }
        />

        <Route
          path="/user/jobs"
          element={
            <ProtectedRoute role="USER">
              <Jobs />
            </ProtectedRoute>
          }
        />

        <Route
          path="/user/applied"
          element={
            <ProtectedRoute role="USER">
              <AppliedJobs />
            </ProtectedRoute>
          }
        />

        <Route
          path="/recruiter/post-job"
          element={
            <ProtectedRoute role="RECRUITER">
              <PostJob />
            </ProtectedRoute>
          }
        />

        <Route
          path="/recruiter/my-jobs"
          element={
            <ProtectedRoute role="RECRUITER">
              <MyJobs />
            </ProtectedRoute>
          }
        />

        <Route path="*" element={<PageNotFound/>}/>

      </Routes>
      </div>
    </div>
  )
}

export default App;