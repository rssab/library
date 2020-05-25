import React from "react";
import "./App.scss";
import UserContainer from "./containers/UserContainer";
import Navigation from "./components/navigation/Navigation";
import { Switch, Route } from "react-router-dom";
import { Container, Row, Col } from "reactstrap";
import CheckoutPage from "./pages/CheckoutPage";

const App = () => {
  return (
    <div className="App">
      <UserContainer>
        <Navigation />
        <Container>
          <Row>
            <Col sm="12" md={{ size: 10, offset: 1 }}>
              <Switch>
                <Route path="/" exact></Route>
                <Route path="/browse"></Route>
                <Route path="/checkout/:id?" component={CheckoutPage}></Route>
              </Switch>
            </Col>
          </Row>
        </Container>
      </UserContainer>
    </div>
  );
};

export default App;
