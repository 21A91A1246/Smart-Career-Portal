import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { loginUser } from "../../api/userApi";
import { useAuth } from "../../context/AuthContext";
import { jwtDecode } from "jwt-decode";

const Login = () => {
  const navigate = useNavigate();
  const { login } = useAuth();

  const [formData, setFormData] = useState({ email: "", password: "" });

  const handleChange = ({ target: { name, value } }) =>
    setFormData(prev => ({ ...prev, [name]: value }));

  const handleSubmit = async e => {
    e.preventDefault();
    try {
      const response = await loginUser(formData);
      login(response.token);
      const decoded = jwtDecode(response.token);
      navigate(
        decoded.role === "RECRUITER" ? "/recruiter" : "/user"
      );

    } catch (error) {
      console.error(error);
      alert("Invalid Credentials");
    }
  };

  return (
    <div className="register-container">
      <form className="register-form" onSubmit={handleSubmit}>
        <h2>Login</h2>

        <input
          type="email"
          name="email"
          placeholder="Email"
          value={formData.email}
          onChange={handleChange}
        />

        <input
          type="password"
          name="password"
          placeholder="Password"
          value={formData.password}
          onChange={handleChange}
        />

        <button type="submit">Login</button>
      </form>
    </div>
  );
};

export default Login;