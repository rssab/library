import React, { useState } from "react";
import { UserProvider } from "../context/UserContext";
import { AuthService } from "../data/AuthService";
import RequestHandler from "../data/RequestHandler";
import jwtDecode from "jwt-decode";

const UserContainer = props => {
  const [token, setToken] = useState(null);
  const [errors, setErrors] = useState([]);
  const [user, setUser] = useState(null);

  const apiRequestHandler = RequestHandler();
  const authApiAccessor = AuthService(apiRequestHandler);

  const handleUserLogin = (nuid, pin) => {
    authApiAccessor
      .login(nuid, pin)
      .then(result => {
        setToken(result.token);
        setUser(jwtDecode(result.token));
        apiRequestHandler.setBearerToken(result.token);
        setErrors([]);
      })
      .catch(err => {
        setErrors([err]);
      });
  };

  const handleUserLogout = () => {
    setUser(null);
    setToken(null);
    apiRequestHandler.clearBearerToken();
  };

  const isLoggedIn = () => {
    return user !== null & token !== null; 
  }

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
        apiRequestHandler: apiRequestHandler
      }}
    >
      {props.children}
    </UserProvider>
  );
};

export default UserContainer;
