import React from 'react';
import APIRequests from "../Utility/APIRequests";

function LogoutButton({userInfoNuller}) {
    async function logoutFunction(){
        await APIRequests.get("/user-service/logout");
        userInfoNuller();
    }
    return (
        <li><span id={"logout"} className={"navbar-link"} onClick={logoutFunction}>Logout</span></li>
    );
}

export default LogoutButton;