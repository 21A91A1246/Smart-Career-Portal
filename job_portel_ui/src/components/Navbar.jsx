import { Link, useNavigate } from "react-router-dom";
import { useAuth } from "../context/AuthContext";
import "./Navbar.css";

const Navbar = () => {
  const { user, logout } = useAuth();
  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
    navigate("/login");
  };
  return (
    <nav className="navbar">
      <div className="navbar-left">
        <h2 className="logo">Job Portal</h2>
      </div>
      <div className="navbar-right">
        {user ? (
          <>
            <div className="user-info">
              <p>Hello {user?.name}</p>
              <span className="role-badge">{user.role}</span>
            </div>

            <button className="logout-btn" onClick={handleLogout}>
              Logout
            </button>
          </>
        ) : (
          <>
            <Link className="login-btn" to="/login">
              Login
            </Link>

            <Link className="register-btn" to="/register">
              Register
            </Link>
          </>
        )}
      </div>
    </nav>
  );
};

export default Navbar;