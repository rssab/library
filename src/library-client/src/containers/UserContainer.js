import React, { Component } from "react";
import { UserProvider } from "../context/UserContext";
import { login } from "../data/Auth";

class UserContainer extends Component {
  state = {
    token: null,
    errors: [],
    user: null
  };

  handleUserLogin = (nuid, pin) => {
    try {
      let loginResult = login(nuid, pin);
      this.setState({
        user: loginResult.user,
        token: loginResult.token
      });
    } catch (err) {
      //TODO(skmshaffer): Handle this better.
      console.log(err);
    }
  };

  handleUserLogout = () => {
    this.setState(state => ({
      user: null,
      token: null
    }));
  };

  render() {
    return (
      <UserProvider
        value={{
          state: this.state,
          handleUserLogin: this.handleUserLogin,
          handleUserLogout: this.handleUserLogout
        }}
      >
        {this.props.children}
      </UserProvider>
    );
  }
}

export default UserContainer;
