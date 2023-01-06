import React from 'react';

function LogoutButton({userInfoNuller}) {
    async function logoutFunction(){
        await fetch("/api/user-service/logout");
        userInfoNuller();
    }
    return (
        <li><span id={"logout"} className={"navbar-link"} onClick={logoutFunction}>Logout</span></li>
    );
}

export default LogoutButton;