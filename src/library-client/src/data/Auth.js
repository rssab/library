/**
 * Data access methods for pulling and reading login data.
 */
import API_BASE_URL from "./Globals";

const login = (nuid, pin) => {
  // TODO(skmshaffer): Update with actual code to contact the server and login!
  return {
    user: { nuid: nuid, firstName: "Karl", lastName: "Shaffer" },
    token: "somegarbagethatrepresentsaJWT"
  };
};

export { login };
