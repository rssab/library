import React from "react";

import { Alert, Form, FormGroup, Label, Input, Button } from "reactstrap";

const LoginForm = props => {
  const nuid = React.createRef();
  const pin = React.createRef();

  const handleSubmit = event => {
    event.preventDefault();

    let nuidContent = nuid.current.value;
    let pinContent = pin.current.value;

    props.loginCallback(nuidContent, pinContent);
  };

  const renderLoginError = () => {
    return props.error ? (
      <Alert color="primary" className="mx-2">
        {props.error}
      </Alert>
    ) : null;
  };

  return (
    <div>
      <Form className="px-4" onSubmit={handleSubmit}>
        {/* Conditionally render errors */}
        {renderLoginError()}
        <FormGroup row>
          <Label for="nuid">nuid</Label>
          <Input type="text" name="nuid" id="nuid" innerRef={nuid}></Input>
        </FormGroup>
        <FormGroup row>
          <Label for={"pin"}>pin</Label>
          <Input type="password" name="pin" id="pin" innerRef={pin}></Input>
        </FormGroup>
        <FormGroup row>
          <Button color="primary" block>
            login
          </Button>
          <Button color="secondary" block>
            register
          </Button>
        </FormGroup>
      </Form>
    </div>
  );
};
