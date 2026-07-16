import { Link } from "react-router-dom";
import "./Sidebar.css";

const Sidebar = ({ role }) => {
  return (
    <div className="sidebar">
      {/* <div className="sidebar-header">
        <h2>Job Portal</h2>
      </div> */}

      <div className="sidebar-links">
        {role === "USER" && (
          <>
            <Link to="/user">Dashboard</Link>
            <Link to="/user/profile">My Profile</Link>
            <Link to="/user/jobs">Browse Jobs</Link>
            <Link to="/user/applied">Applied Jobs</Link>
          </>
        )}

        {role === "RECRUITER" && (
          <>
            <Link to="/recruiter">Dashboard</Link>
            <Link to="/recruiter/post-job">Post Job</Link>
            <Link to="/recruiter/my-jobs">My Jobs</Link>
          </>
        )}
      </div>
    </div>
  );
};

export default Sidebar;