/**
 * Module for handling login and logout from the system.
 */
const AuthApiAccessor = requestHandler => {
  const login = async (nuid, pin) => {
    return requestHandler.post("/api/login", {
      nuid: nuid,
      pin: pin
    });
  };

  return {
    login: login
  };
};

export { AuthApiAccessor };
