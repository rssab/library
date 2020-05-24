import React, { useState } from "react";
import { UserProvider } from "../context/UserContext";
import { AuthService } from "../data/AuthService";
import RequestHandler from "../data/RequestHandler";
import jwtDecode from "jwt-decode";

const UserContainer = props => {
  const [token, setToken] = useState(null);
  const [errors, setErrors] = useState([]);
  const [user, setUser] = useState(null);

  const requestHandler = RequestHandler();
  const authService = AuthService(requestHandler);

  const handleUserLogin = (nuid, pin) => {
    authService
      .login(nuid, pin)
      .then(result => {
        setToken(result.token);
        setUser(jwtDecode(result.token));
        requestHandler.setBearerToken(result.token);
        setErrors([]);
      })
      .catch(err => {
        setErrors([err]);
      });
  };

  const handleUserLogout = () => {
    setUser(null);
    setToken(null);
    requestHandler.clearBearerToken();
  };

  const isLoggedIn = () => {
    return (user !== null) & (token !== null);
  };

  return (
    <UserProvider
      value={{
        state: {
          token: token,
          errors: errors,
          user: user
        },
        handleUserLogin: handleUserLogin,
        handleUserLogout: handleUserLogout,
        isLoggedIn: isLoggedIn,
        requestHandler: requestHandler
      }}
    >
      {props.children}
    </UserProvider>
  );
};

export default UserContainer;
