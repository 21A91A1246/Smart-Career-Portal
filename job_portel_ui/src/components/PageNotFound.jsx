import { Link } from "react-router-dom";
import "./Footer.css";

const PageNotFound = () => {
  return (
    <div className="notfound-container">
      <div className="notfound-box">
        <h1>404</h1>
        <h2>Page Not Found</h2>
        <p>The page you are looking for doesn’t exist or has been moved.</p>

        <Link to="/" className="home-btn">
          Go to Home
        </Link>
      </div>
    </div>
  );
};

export default PageNotFound;