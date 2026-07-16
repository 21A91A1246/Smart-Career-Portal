import { useEffect, useState } from "react";
import { createProfile, getProfile, updateProfile } from "../../api/userApi";
import { useAuth } from "../../context/AuthContext";
import Sidebar from "../../components/Sidebar";
import "./UserProfile.css";

const UserProfile = () => {
  const { user } = useAuth();
  const [profileExists, setProfileExists] = useState(false);

  const [formData, setFormData] = useState({
    headline: "",
    summary: "",
    skills: "",
    experiences: [
      { company: "", role: "", duration: "", description: "" }
    ],
    projects: [
      { title: "", technologies: "", description: "" }
    ]
  });

  useEffect(() => { loadProfile(); }, []);

  const loadProfile = async () => {
    try {
      const data = await getProfile(user.id);

      setProfileExists(true);
      setFormData({
        headline: data.headline || "",
        summary: data.summary || "",
        skills: data.skills?.join(", ") || "",
        experiences: data.experiences?.length
          ? data.experiences
          : [{ company: "", role: "", duration: "", description: "" }],
        projects: data.projects?.length
          ? data.projects
          : [{ title: "", technologies: "", description: "" }]
      });
    } catch {
      setProfileExists(false);
    }
  };

  const handleChange = e =>
    setFormData(prev => ({ ...prev, [e.target.name]: e.target.value }));

  const handleExperienceChange = (index, field, value) => {
    const updated = [...formData.experiences];
    updated[index][field] = value;
    setFormData(prev => ({ ...prev, experiences: updated }));
  };

  const handleProjectChange = (index, field, value) => {
    const updated = [...formData.projects];
    updated[index][field] = value;
    setFormData(prev => ({ ...prev, projects: updated }));
  };

  const addExperience = () =>
    setFormData(prev => ({
      ...prev,
      experiences: [
        ...prev.experiences,
        { company: "", role: "", duration: "", description: "" }
      ]
    }));

  const addProject = () =>
    setFormData(prev => ({
      ...prev,
      projects: [
        ...prev.projects,
        { title: "", technologies: "", description: "" }
      ]
    }));

  const handleSubmit = async e => {
    e.preventDefault();

    const payload = {
      headline: formData.headline,
      summary: formData.summary,
      skills: formData.skills
        .split(",")
        .map(s => s.trim())
        .filter(Boolean),
      experiences: formData.experiences,
      projects: formData.projects
    };

    try {
      if (profileExists) {
        await updateProfile(user.id, payload);
        alert("Profile Updated");
      } else {
        await createProfile(user.id, payload);
        setProfileExists(true);
        alert("Profile Created");
      }
    } catch (error) {
      console.error(error);
      alert("Failed To Save Profile");
    }
  };

 return (
    <div className="profile-layout">
      <Sidebar role="USER" />

      <div className="profile-container">
        <div className="profile-header">
          <h1>My Profile</h1>
          <p>Build and update your professional profile</p>
        </div>

        <form className="profile-form" onSubmit={handleSubmit}>
          <div className="form-group">
            <label>Headline</label>
            <input
              type="text"
              name="headline"
              value={formData.headline}
              onChange={handleChange}
              placeholder="e.g. Full Stack Developer"
            />
          </div>

          <div className="form-group">
            <label>Summary</label>
            <textarea
              rows="5"
              name="summary"
              value={formData.summary}
              onChange={handleChange}
              placeholder="Write a short professional summary..."
            />
          </div>

          <div className="form-group">
            <label>Skills</label>
            <input
              type="text"
              name="skills"
              placeholder="Java, Spring Boot, React"
              value={formData.skills}
              onChange={handleChange}
            />
          </div>

          <h2 className="section-title">Experience</h2>

          {formData.experiences.map((exp, index) => (
            <div key={index} className="section-card">
              <input
                placeholder="Company"
                value={exp.company}
                onChange={e =>
                  handleExperienceChange(index, "company", e.target.value)
                }
              />

              <input
                placeholder="Role"
                value={exp.role}
                onChange={e =>
                  handleExperienceChange(index, "role", e.target.value)
                }
              />

              <input
                placeholder="Duration"
                value={exp.duration}
                onChange={e =>
                  handleExperienceChange(index, "duration", e.target.value)
                }
              />

              <textarea
                placeholder="Description"
                value={exp.description}
                onChange={e =>
                  handleExperienceChange(index, "description", e.target.value)
                }
              />
            </div>
          ))}

          <button type="button" className="secondary-btn" onClick={addExperience}>
            + Add Experience
          </button>

          <h2 className="section-title">Projects</h2>

          {formData.projects.map((project, index) => (
            <div key={index} className="section-card">
              <input
                placeholder="Project Title"
                value={project.title}
                onChange={e =>
                  handleProjectChange(index, "title", e.target.value)
                }
              />

              <input
                placeholder="Technologies"
                value={project.technologies}
                onChange={e =>
                  handleProjectChange(index, "technologies", e.target.value)
                }
              />

              <textarea
                placeholder="Description"
                value={project.description}
                onChange={e =>
                  handleProjectChange(index, "description", e.target.value)
                }
              />
            </div>
          ))}

          <button type="button" className="secondary-btn" onClick={addProject}>
            + Add Project
          </button>

          <button type="submit" className="primary-btn">
            {profileExists ? "Update Profile" : "Create Profile"}
          </button>
        </form>
      </div>
    </div>
  );
};
export default UserProfile;