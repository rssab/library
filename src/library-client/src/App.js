import React from "react";
import "./App.scss";
import UserContainer from "./containers/UserContainer";
import Navigation from "./components/navigation/Navigation";
import { Switch, Route } from "react-router-dom";

const App = () => {
  return (
    <div className="App">
      <UserContainer>
        <Navigation />
        <Switch>
          <Route path="/"></Route>
          <Route path="/browse"></Route>
          <Route path="/checkout"></Route>
        </Switch>
      </UserContainer>
    </div>
  );
};

export default App;
