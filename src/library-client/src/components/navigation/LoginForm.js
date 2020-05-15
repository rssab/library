import React from "react";

import {
  Alert,
  Form,
  FormGroup,
  Label,
  Input,
  Button,
  FormFeedback
} from "reactstrap";
import { validateNuid, validatePin } from "../../common/validators";

const LoginForm = props => {
  const [nuidValidationError, setNuidValidationError] = React.useState(false);
  const [pinValidationError, setPinValidationError] = React.useState(false);

  const [nuid, setNuid] = React.useState();
  const [pin, setPin] = React.useState();

  const handleSubmit = event => {
    event.preventDefault();

    const validNuid = validateNuid(nuid);
    const validPin = validatePin(pin);

    if (validPin && validNuid) {
      props.loginCallback(nuid, pin);
    } else {
      setNuidValidationError(!validNuid);
      setPinValidationError(!validPin);
    }
  };

  const renderLoginError = () => {
    return props.error ? (
      <Alert color="primary" className="mx-2">
        {props.error}
      </Alert>
    ) : null;
  };

  return (
    <div className="login-pane">
      <Form className="px-4" onSubmit={handleSubmit}>
        {/* Conditionally render errors */}
        {renderLoginError()}
        <FormGroup row>
          <Label for="nuid">NUID</Label>
          <Input
            type="text"
            name="nuid"
            id="nuid"
            onChange={e => {
              setNuid(e.target.value);
            }}
            invalid={nuidValidationError}
          />
          <FormFeedback>
            That is not a valid NUID (should be 8 digits).
          </FormFeedback>
        </FormGroup>
        <FormGroup row>
          <Label for={"pin"}>PIN</Label>
          <Input
            type="password"
            name="pin"
            id="pin"
            onChange={e => {
              setPin(e.target.value);
            }}
            invalid={pinValidationError}
          />
          <FormFeedback>
            That is not a valid PIN (should be numeric 4-8 digits).
          </FormFeedback>
        </FormGroup>
        <FormGroup row>
          <Button color="primary" outline block>
            Login
          </Button>
          <Button color="secondary" outline block>
            New User? Click Here
          </Button>
        </FormGroup>
      </Form>
    </div>
  );
};

export default LoginForm;
