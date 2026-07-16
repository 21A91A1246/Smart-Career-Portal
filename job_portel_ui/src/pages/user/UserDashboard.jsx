import { useEffect, useState } from "react";
import { getAllUsers, getProfile } from "../../api/userApi";
import Sidebar from "../../components/Sidebar";
import "./UserDashboard.css";
import { useAuth } from "../../context/AuthContext";
import { getUserApplications } from "../../api/jobApi";

const UserDashboard = () => {

  const { user } = useAuth();
  const [jobsApplied, setJobsApplied] = useState(0);
  const [profileStatus, setProfileStatus] = useState("Incomplete");

  useEffect(() => {
    loadDashboard();
  }, []);

  const loadDashboard = async () => {
    try {
      const applications = await getUserApplications(user.id);
      setJobsApplied(applications.length);
    } catch (error) {
      console.error(error);
    }

    try {
      const profile = await getProfile(user.id);

      if (profile && profile.headline) {
        setProfileStatus("Complete");
      }
    } catch {
      setProfileStatus("Incomplete");
    }
  };

  return (
  <div className="dashboard-container">
    <Sidebar role="USER" />

    <div className="dashboard-content">

      {/* HERO SECTION */}
      <div className="dashboard-hero">
        <div>
          <h1>Welcome back, {user.firstName} 👋</h1>
          <p>
            Explore jobs, apply faster, and build your career profile to stand out to recruiters.
          </p>
        </div>

        <div className="hero-card">
          <img
            src="https://illustrations.popsy.co/gray/work-from-home.svg"
            alt="job search"
          />
        </div>
      </div>

      {/* STATS */}
      <div className="stats-container">
        <div className="stat-card">
          <h3>Jobs Applied</h3>
          <p>{jobsApplied}</p>
        </div>

        <div className="stat-card">
          <h3>Profile Status</h3>
          <p style={{ color: profileStatus === "Complete" ? "#16a34a" : "#dc2626" }}>
            {profileStatus}
          </p>
        </div>

        <div className="stat-card">
          <h3>Resume Status</h3>
          <p style={{ color: "#16a34a" }}>Available</p>
        </div>

        <div className="stat-card">
          <h3>Tip</h3>
          <p style={{ fontSize: "14px", color: "#475569" }}>
            Complete your profile to get 3x more interview calls 🚀
          </p>
        </div>
      </div>

      {/* INFO SECTION */}
      <div className="info-section">
        <h2>Career Tip of the Day</h2>
        <p>
          Recruiters prefer candidates with clear skills, real projects, and updated resumes.
          Keep applying consistently — momentum matters more than perfection.
        </p>
      </div>

    </div>
  </div>
);
};

export default UserDashboard;