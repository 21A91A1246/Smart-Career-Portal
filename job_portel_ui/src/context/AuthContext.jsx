import {
  createContext,
  useContext,
  useState
} from "react";

import { jwtDecode } from "jwt-decode";

const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(() => {
    const token = localStorage.getItem("token");
    if (!token) return null;

    try {
      const decoded = jwtDecode(token);
      return {
        id: decoded.empId,
        email: decoded.sub,
        role: decoded.role
      };

    } catch {

      return null;
    }
  });

  const login = token => {
    localStorage.setItem("token",token);
    const decoded = jwtDecode(token);
    const userData = {
      id: decoded.empId,
      email: decoded.sub,
      role: decoded.role,
      name:decoded.name
    };

    setUser(userData);
  };

  const logout = () => {
    localStorage.removeItem("token");
    setUser(null);
  };

  return (
    <AuthContext.Provider
      value={{
        user,
        login,
        logout
      }}
    >
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth =
  () => useContext(AuthContext);