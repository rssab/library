/**
 * React Context for managing Errors within the app.
 */
import React from "react";

const ErrorContext = React.createContext();

const ErrorProvider = ErrorContext.Provider;
const ErrorConsumer = ErrorContext.Consumer;

export { ErrorProvider, ErrorConsumer };
