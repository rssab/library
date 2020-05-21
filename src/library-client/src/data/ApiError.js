class ApiError extends Error {
  constructor(message, response, status) {
    super(message);
    this.response = response;
    this.status = status;
  }
}

export default ApiError;
