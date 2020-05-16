import { API_BASE_URL } from "./Globals";
import ApiError from "./ApiError";

/**
 * Module for handling requests to the API. Maintains the required token and provides
 * methods for POST/GET requests as well as updating or clearing the token with
 * login/logout.
 */
const ApiRequestHandler = token => {
  let headers = {
    "Content-Type": "application/json"
  };

  /**
   * Processes a fetch request to the API for errors, throws an
   * error up the chain if there are errors otherwise
   * simply passes the JSON up.
   */
  const errorHandler = async response => {
    if (!response.ok) {
      let error;
      try {
        // Attempt to Parse Error from API.
        error = await response.json();
      } catch (err) {
        error = {
          message: "Something went wrong.",
          status: response.status
        };
      }

      // If it parses, throw it up to be handled elsewhere.
      throw new ApiError("API Error", error.message, error.status);
    } else {
      // If no error, just return the JSON.
      return response.json();
    }
  };

  const setBearerToken = token => {
    headers["Authorization"] = `Bearer ${token}`;
  };

  const clearBearerToken = token => {
    delete headers["Authorization"];
  };

  if (token) {
    setBearerToken(token);
  }

  const get = async path => {
    return fetch(`${API_BASE_URL}${path}`, {
      method: "GET",
      headers: headers
    }).then(errorHandler);
  };

  const post = async (path, payload) => {
    return fetch(`${API_BASE_URL}${path}`, {
      method: "POST",
      headers: headers,
      body: JSON.stringify(payload)
    }).then(errorHandler);
  };

  return {
    post: post,
    get: get,
    setBearerToken: setBearerToken,
    clearBearerToken: clearBearerToken
  };
};

export default ApiRequestHandler;
