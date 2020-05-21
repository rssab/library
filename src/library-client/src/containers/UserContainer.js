import React, { useState } from "react";
import { UserProvider } from "../context/UserContext";
import { AuthApiAccessor } from "../data/AuthApiAccessor";
import ApiRequestHandler from "../data/ApiRequestHandler";
import jwtDecode from "jwt-decode";

const UserContainer = props => {
  const [token, setToken] = useState(null);
  const [errors, setErrors] = useState([]);
  const [user, setUser] = useState(null);

  const apiRequestHandler = ApiRequestHandler();
  const authApiAccessor = AuthApiAccessor(apiRequestHandler);

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

  return (
    <UserProvider
      value={{
        state: {
          token: token,
          errors: errors,
          user: user
        },
        handleUserLogin: handleUserLogin,
        handleUserLogout: handleUserLogout
      }}
    >
      {props.children}
    </UserProvider>
  );
};

export default UserContainer;
