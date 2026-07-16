import { useState } from "react";
import { createJob } from "../../api/jobApi";
import { useAuth } from "../../context/AuthContext";
import Sidebar from "../../components/Sidebar";
import "./PostJob.css"

const PostJob = () => {
  const { user } = useAuth();

  const [formData, setFormData] = useState({
    title: "",
    description: "",
    location: "",
    employmentType: "FULL_TIME",
    salary: "",
    companyId: user.id,
    createdBy: user.id,
    status: "ACTIVE"
  });

  const handleChange = e =>
    setFormData(prev => ({ ...prev, [e.target.name]: e.target.value }));

  const handleSubmit = async e => {
    e.preventDefault();

    try {
      await createJob({
        ...formData,
        salary: Number(formData.salary)
      });

      alert("Job Posted Successfully");

      setFormData({
        title: "",
        description: "",
        location: "",
        employmentType: "FULL_TIME",
        salary: "",
        companyId: user.id,
        createdBy: user.id,
        status: "ACTIVE"
      });
    } catch (error) {
      console.error(error);
      alert("Failed To Post Job");
    }
  };
return (
  <div className="postjob-layout">
    <Sidebar role="RECRUITER" />

    <div className="postjob-container">

      <div className="postjob-banner">
        <h2>Create New Opportunity 🚀</h2>
        <p>
          Reach talented professionals and find the perfect candidate for your team.
        </p>
      </div>

      <div className="postjob-card">

        <h1>Post New Job</h1>

        <form
          onSubmit={handleSubmit}
          className="postjob-form"
        >
          <input
            type="text"
            name="title"
            placeholder="Job Title"
            value={formData.title}
            onChange={handleChange}
          />

          <textarea
            rows="6"
            name="description"
            placeholder="Job Description"
            value={formData.description}
            onChange={handleChange}
          />

          <input
            type="text"
            name="location"
            placeholder="Location"
            value={formData.location}
            onChange={handleChange}
          />

          <select
            name="employmentType"
            value={formData.employmentType}
            onChange={handleChange}
          >
            <option value="FULL_TIME">Full Time</option>
            <option value="PART_TIME">Part Time</option>
            <option value="REMOTE">Remote</option>
          </select>

          <input
            type="number"
            name="salary"
            placeholder="Salary"
            value={formData.salary}
            onChange={handleChange}
          />

          <button type="submit">
            Post Job
          </button>
        </form>

      </div>
    </div>
  </div>
);
};
export default PostJob