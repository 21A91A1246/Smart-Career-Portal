import { useEffect, useState, useRef } from "react";
import {
  getAllJobs,
  applyForJob,
  uploadResume,
  getUserApplications
} from "../../api/jobApi";
import { generateResume } from "../../api/resumeApi";
import Sidebar from "../../components/Sidebar";
import { useAuth } from "../../context/AuthContext";
import jsPDF from "jspdf";
import html2canvas from "html2canvas";
import "./Jobs.css";

const Jobs = () => {
  const { user } = useAuth();

  const [jobs, setJobs] = useState([]);
  const [selectedJob, setSelectedJob] = useState(null);

  const [resume, setResume] = useState(null);
  const [generatedResume, setGeneratedResume] = useState(null);

  const [showResume, setShowResume] = useState(false);
  const [coverLetter, setCoverLetter] = useState("");
  const [loading, setLoading] = useState(false);
  const [appliedJobs, setAppliedJobs] = useState([]);

  const [mode, setMode] = useState(""); 
  // "upload" | "ai"

  const resumeRef = useRef(null);

  useEffect(() => {
    loadJobs();
    loadAppliedJobs();
  }, []);

  // ---------------- LOAD JOBS ----------------
  const loadJobs = async () => {
    try {
      const data = await getAllJobs();
      const activeJobs = data.filter(job => job.status === "ACTIVE");
      setJobs(activeJobs);
    } catch (error) {
      console.error(error);
    }
  };

  // ---------------- LOAD APPLIED ----------------
  const loadAppliedJobs = async () => {
    try {
      const applications = await getUserApplications(user.id);
      setAppliedJobs(applications.map(app => app.jobId));
    } catch (error) {
      console.error(error);
    }
  };

  // ---------------- AI RESUME ----------------
  const handleGenerateResume = async (jobId) => {
    try {
      setLoading(true);
      setMode("ai");

      const resumeData = await generateResume(user.id, jobId);

      setGeneratedResume(resumeData);
      setSelectedJob(jobId);
      setShowResume(true);
      setResume(null);
    } catch (error) {
      console.error(error);
      alert("Failed To Generate Resume");
    } finally {
      setLoading(false);
    }
  };

  // ---------------- PDF DOWNLOAD ----------------
  const downloadPDF = async () => {
    const input = resumeRef.current;

    const canvas = await html2canvas(input, {
      scale: 2,
      useCORS: true
    });

    const imgData = canvas.toDataURL("image/png");

    const pdf = new jsPDF("p", "mm", "a4");

    const imgWidth = 210;
    const pageHeight = 297;

    const imgHeight = (canvas.height * imgWidth) / canvas.width;

    let heightLeft = imgHeight;
    let position = 0;

    pdf.addImage(imgData, "PNG", 0, position, imgWidth, imgHeight);

    heightLeft -= pageHeight;

    while (heightLeft > 0) {
      position = heightLeft - imgHeight;
      pdf.addPage();
      pdf.addImage(imgData, "PNG", 0, position, imgWidth, imgHeight);
      heightLeft -= pageHeight;
    }

    pdf.save("resume.pdf");
  };

  // ---------------- APPLY ----------------
  const handleApply = async () => {
    try {
      if (!selectedJob) {
        alert("Please select a job");
        return;
      }

      if (!resume && !generatedResume) {
        alert("Please upload or generate resume");
        return;
      }

      let resumeUrl = "";

      if (resume) {
        resumeUrl = await uploadResume(resume);
      } else {
        resumeUrl = "AI_GENERATED";
      }

      await applyForJob({
        jobId: selectedJob,
        userId: user.id,
        resumeUrl,
        coverLetter
      });

      alert("Applied Successfully");
      await loadAppliedJobs();

      // RESET EVERYTHING
      setSelectedJob(null);
      setResume(null);
      setGeneratedResume(null);
      setShowResume(false);
      setCoverLetter("");
      setMode("");
    } catch (error) {
      console.error(error);
      alert(error.response?.data?.message || "Application Failed");
    }
  };

  // ---------------- UI ----------------
  return (
    <div className="jobs-layout">
      <Sidebar role="USER" />

      <div className="jobs-container">
        <h1>Available Jobs</h1>

        {jobs.length === 0 ? (
          <p>No Jobs Available</p>
        ) : (
          jobs.map(job => (
            <div key={job.id} className="job-card">
              <h3>{job.title}</h3>
              <p>{job.description}</p>

              <p><strong>Location:</strong> {job.location}</p>
              <p><strong>Salary:</strong> ₹{job.salary}</p>
              <p><strong>Employment Type:</strong> {job.employmentType}</p>

              {appliedJobs.includes(job.id) ? (
                <button disabled className="applied-btn">
                  ✅ Applied
                </button>
              ) : (
                <div style={{ display: "flex", gap: "10px", marginTop: "10px" }}>
                  <button
                    onClick={() => {
                      setSelectedJob(job.id);
                      setMode("upload");
                      setShowResume(false);
                      setGeneratedResume(null);
                    }}
                  >
                    Upload Resume
                  </button>

                  <button onClick={() => handleGenerateResume(job.id)}>
                    Generate AI Resume
                  </button>
                </div>
              )}
            </div>
          ))
        )}

        {/* APPLY PANEL */}
        {selectedJob && (
          <div className="apply-box">
            <h2>Apply For Job</h2>
            <p>Job ID: {selectedJob}</p>

            {/* UPLOAD MODE */}
            {mode === "upload" && (
              <>
                <h3>Upload Resume (Required)</h3>
                <input
                  type="file"
                  accept=".pdf"
                  onChange={e => setResume(e.target.files[0])}
                />
              </>
            )}

            {/* AI MODE */}
            {showResume && generatedResume && (
              <>
                <hr />

                <div ref={resumeRef} style={{ background: "white", padding: "20px", color: "black" }}>
                  <h2>AI Resume Preview</h2>

                  <h3>Professional Summary</h3>
                  <p>{generatedResume.professionalSummary}</p>

                  <h3>Skills</h3>
                  <ul>
                    {generatedResume.prioritizedSkills?.map((skill, i) => (
                      <li key={i}>{skill}</li>
                    ))}
                  </ul>

                  <h3>Experience</h3>
                  {generatedResume.tailoredExperiences?.map((exp, i) => (
                    <div key={i}>
                      <h4>{exp.role}</h4>
                      <p>{exp.company}</p>
                      <p>{exp.duration}</p>
                    </div>
                  ))}

                  <h3>Projects</h3>
                  {generatedResume.tailoredProjects?.map((p, i) => (
                    <div key={i}>
                      <h4>{p.title}</h4>
                    </div>
                  ))}
                </div>

                <button className="download-btn" onClick={downloadPDF}>
                  Download Resume PDF
                </button>
              </>
            )}

            {loading && <p>Generating Resume...</p>}

            {/* COVER LETTER */}
            <textarea
              rows="5"
              placeholder="Cover Letter"
              value={coverLetter}
              onChange={e => setCoverLetter(e.target.value)}
              style={{ width: "100%", padding: "10px", borderRadius: "6px" }}
            />

            <button className="apply-btn" onClick={handleApply}>
              Submit Application
            </button>
          </div>
        )}
      </div>
    </div>
  );
};

export default Jobs;