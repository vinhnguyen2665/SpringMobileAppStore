import React from 'react';
import {Redirect} from 'react-router-dom';
import {useShowLoginPopup} from "../examples/redux/loginPopup";

export default function RedirectLoginPage() {
    const {showLoginPopup} = useShowLoginPopup();
    showLoginPopup();
    return (
        <>
            <Redirect to='/'  />
        </>
    );
}
