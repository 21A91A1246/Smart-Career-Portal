import { useState } from "react";
import { registerUser } from "../../api/userApi";
import "./Register.css";

const Register = () => {

  const [formData, setFormData] = useState({
    firstName: "",
    lastName: "",
    email: "",
    password: "",
    role: "USER",
    companyName: ""
  });

  const handleChange = (e) => {

    const { name, value } = e.target;

    setFormData((prev) => ({
      ...prev,
      [name]: value
    }));
  };

  const handleSubmit = async (e) => {

    e.preventDefault();

    try {

      const response =
        await registerUser(formData);

      console.log(response);

      alert("Registration Successful");

      setFormData({
        firstName: "",
        lastName: "",
        email: "",
        password: "",
        role: "USER",
        companyName: ""
      });

    } catch (error) {

      console.error(error);

      alert("Registration Failed");
    }
  };

  return (
    <div className="register-container">

      <form
        className="register-form"
        onSubmit={handleSubmit}
      >

        <h2>Create Account</h2>

        <input
          type="text"
          name="firstName"
          placeholder="First Name"
          value={formData.firstName}
          onChange={handleChange}
        />

        <input
          type="text"
          name="lastName"
          placeholder="Last Name"
          value={formData.lastName}
          onChange={handleChange}
        />

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

        <select
          name="role"
          value={formData.role}
          onChange={handleChange}
        >
          <option value="USER">
            USER
          </option>

          <option value="RECRUITER">
            RECRUITER
          </option>
        </select>

        {
          formData.role === "RECRUITER" && (

            <input
              type="text"
              name="companyName"
              placeholder="Company Name"
              value={formData.companyName}
              onChange={handleChange}
            />

          )
        }

        <button type="submit">
          Register
        </button>

      </form>

    </div>
  );
};

export default Register;