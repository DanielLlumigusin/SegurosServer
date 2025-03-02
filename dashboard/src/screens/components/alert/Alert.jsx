import "./Alert.css";

const Alert = ({ message }) => {
    return (
      <div className="alert-container">
        <div className="alert">
          <span>{message}</span>
        </div>
      </div>
    );
  };
  
  export default Alert;
  