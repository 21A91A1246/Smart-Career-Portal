const Footer = () => {
  const styles = {
    footer: {
      background: "#f8fafc", /* Crisp, ultra-light slate gray background */
      color: "#ffffff", 
      padding: "20px 20px 20px 20px",
      fontFamily: "Arial, sans-serif",
      marginLeft: "220px", // Push footer past the fixed sidebar width
      width: "calc(100% - 220px)", // Account for the sidebar width offset
      boxSizing: "border-box",
    },
    footerContent: {
      display: "flex",
      justifyContent: "space-around",
      flexWrap: "wrap",
      maxWidth: "1200px",
      margin: "0 auto",
      gap: "20px",
    },
    footerSection: {
      flex: "1",
      minWidth: "250px",
    },
    heading: {
      color: "#3b82f6", // Vibrant accent blue matching standard UI design
      marginBottom: "10px",
    },
    text: {
      fontSize: "14px",
      lineHeight: "1.6",
      color: "#9ca3af", // Subtle gray text instead of raw white opacity
    },
    footerBottom: {
      marginTop: "15px",
      paddingTop: "15px",
      borderTop: "1px solid #1e293b",
      fontSize: "14px",
      color: "#6b7280",
      textAlign: "center",
      width: "100%",
    },
  };

  return (
    <footer style={styles.footer}>
      <div style={styles.footerContent}>
        <div style={styles.footerSection}>
          <h3 style={styles.heading}>Job Portal</h3>
          <p style={styles.text}>
            Connect job seekers with recruiters and discover new career
            opportunities.
          </p>
        </div>

        <div style={styles.footerSection}>
          <h4 style={styles.heading}>Contact</h4>
          <p style={styles.text}>Email: support@jobportal.com</p>
          <p style={styles.text}>Phone: +91 98765 43210</p>
        </div>
      </div>

      <div style={styles.footerBottom}>
        © {new Date().getFullYear()} Job Portal. All Rights Reserved.
      </div>
    </footer>
  );
};

export default Footer;