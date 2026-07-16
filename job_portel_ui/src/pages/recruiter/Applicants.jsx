import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { getJobApplications } from "../../api/jobApi";
import { getUserById, getProfile } from "../../api/userApi";
import Sidebar from "../../components/Sidebar";
import "./Applicants.css";

const Applicants = () => {
  const { jobId } = useParams();
  const [applications, setApplications] = useState([]);

  useEffect(() => { loadApplicants(); }, [jobId]);

  const loadApplicants = async () => {
    try {
      const applications = await getJobApplications(jobId);

      const enrichedApplicants = await Promise.all(
        applications.map(async (application) => {
          try {
            const user = await getUserById(application.userId);

            let profile = null;
            try {
              profile = await getProfile(application.userId);
            } catch {
              profile = { headline: "Profile Not Created", summary: "", skills: [], experiences: [], projects: [] };
            }

            return { ...application, user, profile };
          } catch {
            return { ...application };
          }
        })
      );

      setApplications(enrichedApplicants);
    } catch (error) {
      console.error(error);
    }
  };

  return (
  <div className="applicants-layout">
    <Sidebar role="RECRUITER" />

    <div className="applicants-container">

      <div className="applicants-banner">
        <h2>Applicant Management 👥</h2>
        <p>
          Review candidates, evaluate profiles, and find the best talent for your organization.
        </p>
      </div>

      <div className="applicants-header">
        <h1>Applicants</h1>
        <span>Job ID: {jobId}</span>
      </div>

      {applications.length === 0 ? (
        <div className="empty-applicants">
          <h2>No Applicants Yet</h2>
          <p>
            Applications submitted for this job posting will appear here.
          </p>
        </div>
      ) : (
        applications.map(application => (
          <div
            key={application.id}
            className="applicant-card"
          >
            <div className="applicant-top">
              <div>
                <h2>
                  {application.user?.firstName}{" "}
                  {application.user?.lastName}
                </h2>

                <p className="email">
                  {application.user?.email}
                </p>
              </div>

              <div className="status-chip">
                {application.status}
              </div>
            </div>

            <div className="applicant-info">
              <p>
                <strong>Applied:</strong>{" "}
                {new Date(
                  application.appliedAt
                ).toLocaleString()}
              </p>

              <p>
                <strong>Headline:</strong>{" "}
                {application.profile?.headline}
              </p>
            </div>

            <div className="summary-box">
              <h4 style={{margin:'2px'}}>Professional Summary</h4>
              <p style={{margin:'2px'}}>
                {application.profile?.summary ||
                  "No Summary Added"}
              </p>
            </div>

            <div className="skills-box">
              <h4 style={{margin:'2px'}}>Skills</h4>
              <div className="skills-list">
                {application.profile?.skills?.length ? (
                  application.profile.skills.map(
                    (skill, index) => (
                      <span
                        key={index}
                        className="skill-chip"
                      >
                        {skill}
                      </span>
                    )
                  )
                ) : (
                  <span>No Skills Added</span>
                )}
              </div>
            </div>

            <div className="resume-section">
              

              {application.resumeUrl ? (
                <a
                  href={application.resumeUrl}
                  target="_blank"
                  rel="noreferrer"
                  className="resume-btn"
                >
                  View Resume
                </a>
              ) : (
                <span>
                  Resume Not Uploaded
                </span>
              )}
            </div>

            <div className="cover-letter">
              <h4 style={{margin:'3px'}}>Cover Letter</h4>

              <p style={{margin:'0px'}}>
                {application.coverLetter ||
                  "Not Provided"}
              </p>
            </div>
          </div>
        ))
      )}
    </div>
  </div>
);
};

export default Applicants;