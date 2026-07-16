import { Navigate } from "react-router-dom";
import { useAuth } from "../context/AuthContext";

const ProtectedRoute = ({
  role,
  children,
}) => {

  const { user } = useAuth();

  if (!user) {
    return <Navigate to="/login" />;
  }

  if (user.role !== role) {
    return (
      <Navigate
        to={
          user.role === "RECRUITER"
            ? "/recruiter"
            : "/user"
        }
      />
    );
  }

  return children;
};

export default ProtectedRoute;