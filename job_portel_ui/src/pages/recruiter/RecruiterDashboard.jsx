import { useEffect, useState } from "react";
import {  getJobApplications, getRecruiterJobs } from "../../api/jobApi";
import Sidebar from "../../components/Sidebar";
import { useAuth } from "../../context/AuthContext";
import "./UserDashboard.css";

const RecruiterDashboard = () => {

  const { user } = useAuth();
  const [totalJobs, setTotalJobs] = useState(0);
  const [activeJobs, setActiveJobs] = useState(0);
  const [applications, setApplications] = useState(0);

  useEffect(() => {
    loadDashboard();
  }, []);

  const loadDashboard = async () => {
    try {
      const jobs = await getRecruiterJobs(user.id);

      setTotalJobs(jobs.length);

      setActiveJobs(
        jobs.filter(job => job.status === "ACTIVE").length
      );

      let totalApplications = 0;

      for (const job of jobs) {
        try {
          const apps = await getJobApplications(job.id);
          totalApplications += apps.length;
        } catch (error) {
          console.error(error);
        }
      }

      setApplications(totalApplications);
    } catch (error) {
      console.error(error);
    }
  };
  return (
  <div className="dashboard-container">
    <Sidebar role="RECRUITER" />

    <div className="dashboard-content">

      {/* HERO */}
      <div className="dashboard-hero">
        <div className="recruiter-banner">
          <h2>Find Your Next Great Hire 🚀</h2>
          <p>
            Manage job postings, review applicants, and connect with talented
            professionals who can help grow your organization.
          </p>
        </div>

        <div className="hero-card">
          <img
            src="https://illustrations.popsy.co/amber/shaking-hands.svg"
            alt="recruitment"
          />
        </div>
      </div>

      {/* STATS */}
      <div className="stats-container">
        <div className="stat-card">
          <h3>Total Jobs</h3>
          <p>{totalJobs}</p>
        </div>

        <div className="stat-card">
          <h3>Applications</h3>
          <p>{applications}</p>
        </div>

        <div className="stat-card">
          <h3>Active Jobs</h3>
          <p>{activeJobs}</p>
        </div>

        <div className="stat-card">
          <h3>Hiring Insight</h3>
          <p style={{ fontSize: "14px", color: "#475569" }}>
            The best candidates apply within 48 hours of job posting ⚡
          </p>
        </div>
      </div>

      {/* INFO SECTION */}
      <div className="info-section">
        <h2>Hiring Insight</h2>
        <p>
          Strong job descriptions attract better candidates. Keep roles clear, simple,
          and highlight growth opportunities to attract top talent.
        </p>
      </div>

    </div>
  </div>
);
};

export default RecruiterDashboard;