import React, { useState } from "react";
import { UserProvider } from "../context/UserContext";
import { login } from "../data/Auth";

const UserContainer = props => {
  const [token, setToken] = useState(null);
  const [errors, setErrors] = useState([]);
  const [user, setUser] = useState(null);

  const handleUserLogin = (nuid, pin) => {
    try {
      let loginResult = login(nuid, pin);

      setToken(loginResult.token);
      setUser(loginResult.user);
    } catch (err) {
      //TODO(skmshaffer): Handle this better.
      console.log(err);
    }
  };

  const handleUserLogout = () => {
    setUser(null);
    setToken(null);
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
