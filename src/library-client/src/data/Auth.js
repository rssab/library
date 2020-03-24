/**
 * Data access methods for pulling and reading login data.
 */
import API_BASE_URL from "./Globals";

const login = (nuid, pin) => {
  return {
    user: { nuid: nuid, firstName: "Karl", lastName: "Shaffer" },
    token: "somegarbagethatrepresentsaJWT"
  };
};

export { login };
