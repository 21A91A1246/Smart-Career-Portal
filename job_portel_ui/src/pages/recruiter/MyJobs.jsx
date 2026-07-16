import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { deleteJob, getRecruiterJobs } from "../../api/jobApi";
import { useAuth } from "../../context/AuthContext";
import Sidebar from "../../components/Sidebar";
import "./Myjobs.css";

const MyJobs = () => {
  const { user } = useAuth();
  const [jobs, setJobs] = useState([]);

  useEffect(() => {
    loadJobs();
  }, []);

  const loadJobs = async () => {
    try {
      setJobs(await getRecruiterJobs(user.id));
    } catch (error) {
      console.error(error);
    }
  };

  const handleDelete = async jobId => {
    if (!window.confirm("Delete this job?")) return;

    try {
      await deleteJob(jobId);
      setJobs(jobs.filter(job => job.id !== jobId));
    } catch (error) {
      console.error(error);
    }
  };

  return (
    <div className="myjobs-layout">
      <Sidebar role="RECRUITER" />

      <div className="myjobs-container">
        <div className="page-header">
          <h1>My Jobs</h1>
          <span>{jobs.length} Jobs Posted</span>
        </div>

        {jobs.length === 0 ? (
          <div className="empty-card">
            No jobs posted yet.
          </div>
        ) : (
          jobs.map(job => (
            <div key={job.id} className="job-card">
              <div className="job-header">
                <h2>{job.title}</h2>

                <span className="status-chip">
                  {job.status || "ACTIVE"}
                </span>
              </div>

              <p className="job-description">
                {job.description}
              </p>

              <div className="job-info">
                <span>📍 {job.location}</span>
                <span>💰 ₹{job.salary}</span>
                <span>💼 {job.employmentType}</span>
              </div>

              <div className="job-actions">
                <Link
                  className="applicants-btn"
                  to={`/recruiter/applicants/${job.id}`}
                >
                  View Applicants
                </Link>

                <button
                  className="delete-btn"
                  onClick={() => handleDelete(job.id)}
                >
                  Delete
                </button>
              </div>
            </div>
          ))
        )}
      </div>
    </div>
  );
};

export default MyJobs;