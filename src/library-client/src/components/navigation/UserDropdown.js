import React from "react";
import { Button } from "reactstrap";

const UserDropdown = props => {
  return (
    <div className="p-2">
      <h5 className="text-center">Hi, {props.name}</h5>
      <Button color="primary" block outline onClick={props.logoutCallback}>
        log out
      </Button>
    </div>
  );
};

export default UserDropdown;
