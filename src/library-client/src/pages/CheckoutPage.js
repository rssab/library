import React from "react";

import { UserProvider } from '../context/UserContext';

const CheckoutPage = (props) => {
    
}

export default (props) => {
    return(
        <UserProvider>
            {
                value => {
                    <CheckoutPage {...props} userContext={value}/>
                }
            }
        </UserProvider>
    );
} 