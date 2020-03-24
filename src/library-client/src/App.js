import React from "react";
import "./App.scss";
import UserContainer from "./containers/UserContainer";
import { BrowserRouter } from "react-router-dom";

const App = () => {
  return (
    <BrowserRouter>
      <div className="App">
        <UserContainer>
          <p>Hello world!</p>
        </UserContainer>
      </div>
    </BrowserRouter>
  );
};

export default App;
