import React, { useState } from "react";

import {
  Navbar,
  NavbarBrand,
  NavbarToggler,
  Collapse,
  Nav,
  NavItem,
  UncontrolledDropdown,
  DropdownToggle,
  DropdownMenu
} from "reactstrap";

import UserDropdown from "./UserDropdown";
import LoginForm from "./LoginForm";
import { UserConsumer } from "../../context/UserContext";

import { Link, NavLink } from "react-router-dom";

import { ReactComponent as RaikesLogo } from "../../img/LibraryProjectRSWhite.svg";

import "./Navigation.scss";

const Navigation = () => {
  const [isOpen, setIsOpen] = useState(false);
  const toggle = () => setIsOpen(!isOpen);

  const getLoginMenuText = nuid => {
    if (nuid) {
      return nuid;
    }

    return "Login";
  };

  const renderLoginPaneContent = (
    loggedIn,
    user,
    loginCallback,
    logoutCallback
  ) => {
    if (loggedIn) {
      return (
        <UserDropdown name={user.firstName} logoutCallback={logoutCallback} />
      );
    }

    return <LoginForm loginCallback={loginCallback} />;
  };

  return (
    <Navbar color="primary" dark expand="md">
      <Link className="navbar-brand" to="/">
        <RaikesLogo height="45px" />
      </Link>
      <NavbarToggler onClick={toggle} />
      <Collapse isOpen={isOpen} navbar>
        <Nav navbar className="mr-auto">
          <NavItem>
            <NavLink className="nav-link" to="/browse">
              Browse
            </NavLink>
          </NavItem>
          <NavItem>
            <NavLink className="nav-link" to="/checkout">
              Checkout
            </NavLink>
          </NavItem>
        </Nav>
        <Nav navbar className="ml-auto">
          <UncontrolledDropdown nav inNavbar>
            <DropdownToggle caret nav>
              <UserConsumer>
                {userContext =>
                  getLoginMenuText(
                    userContext.state.user != null
                      ? userContext.state.user.nuid
                      : null
                  )
                }
              </UserConsumer>
            </DropdownToggle>
            <DropdownMenu right>
              <UserConsumer>
                {userContext =>
                  renderLoginPaneContent(
                    userContext.state.user != null,
                    userContext.state.user,
                    userContext.handleUserLogin,
                    userContext.handleUserLogout
                  )
                }
              </UserConsumer>
            </DropdownMenu>
          </UncontrolledDropdown>
        </Nav>
      </Collapse>
    </Navbar>
  );
};

export default Navigation;
