import { useEffect, useState } from "react";
import { getJobById, getUserApplications } from "../../api/jobApi";
import Sidebar from "../../components/Sidebar";
import { useAuth } from "../../context/AuthContext";

const AppliedJobs = () => {
  const { user } = useAuth();
  const [applications, setApplications] = useState([]);

  useEffect(() => { loadApplications(); }, []);

  const loadApplications = async () => {
    try {
      const apps = await getUserApplications(user.id);

      const enrichedApps = await Promise.all(
        apps.map(async app => {
          const job = await getJobById(app.jobId);
          return { ...app, job };
        })
      );

      setApplications(enrichedApps);
    } catch (error) {
      console.error(error);
    }
  };

  return (
    <div style={{ display: "flex", minHeight: "100vh", backgroundColor: "#f4f6f9" }}>
      <Sidebar role="USER" />

      <div style={{ flex: 1, padding: "30px", display: "flex", flexDirection: "column", alignItems: "center" }}>
        <center>
          <h1 style={{ marginBottom: "25px", color: "#1f2937", alignSelf: "flex-start", width: "100%", maxWidth: "750px" }}>
            Applied Jobs
          </h1>
        </center>

        {applications.length === 0 ? (
          <div style={{
            background: "white",
            padding: "20px",
            borderRadius: "12px",
            boxShadow: "0 2px 10px rgba(0,0,0,0.08)",
            width: "100%",
            maxWidth: "750px",
            textAlign: "center"
          }}>
            No Applications Found
          </div>
        ) : (
          applications.map(application => (
            <div
              key={application.id}
              style={{
                background: "white",
                padding: "22px",
                borderRadius: "14px",
                marginBottom: "18px",
                boxShadow: "0 4px 12px rgba(0,0,0,0.08)",
                borderLeft: "5px solid #2563eb",
                width: "100%",
                maxWidth: "750px",
                transition: "0.2s ease"
              }}
              onMouseEnter={e => e.currentTarget.style.transform = "translateY(-2px)"}
              onMouseLeave={e => e.currentTarget.style.transform = "translateY(0)"}
            >
              <h2 style={{ marginBottom: "8px", color: "#2563eb" }}>
                {application.job.title}
              </h2>

              <p style={{ margin: "4px 0" }}>
                <strong>Application ID:</strong> {application.id}
              </p>

              <p style={{ margin: "4px 0" }}>
                <strong>Location:</strong> {application.job.location}
              </p>

              <p style={{ margin: "4px 0" }}>
                <strong>Salary:</strong> ₹{application.job.salary}
              </p>

              <p style={{ margin: "4px 0" }}>
                <strong>Type:</strong> {application.job.employmentType}
              </p>

              <p style={{ margin: "6px 0" }}>
                <strong>Status:</strong>{" "}
                <span style={{
                  color: application.status === "APPLIED" ? "#16a34a" : "#dc2626",
                  fontWeight: "bold"
                }}>
                  {application.status}
                </span>
              </p>

              <p style={{ marginTop: "6px", color: "#6b7280" }}>
                <strong>Applied On:</strong>{" "}
                {new Date(application.appliedAt).toLocaleString()}
              </p>
            </div>
          ))
        )}
      </div>
    </div>
  );
};

export default AppliedJobs;