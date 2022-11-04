import React from 'react';

function LoginPage(props) {
    async function sendLoginRequest(event){
        event.preventDefault();
        const username = document.getElementById("username-field").value;
        const password = document.getElementById("password-field").value;


        await fetch("/api/user-service/login",{
            method:"POST",
            headers:{
                'Content-Type': 'application/json'
            },
            body:JSON.stringify({
                'username':username,
                'password':password
            })
        })
    }

    return (
        <form id={"login-form"} onSubmit={sendLoginRequest}>
            <label id={"username-label"} htmlFor={"username"}>
                Username:
                <input type={"text"} id={"username-field"} name={"username"}/>
            </label>
            <label id={"password-label"} htmlFor={"password"}>
                Password:
                <input type={"password"} id={"password-field"} name={"password"}/>
            </label>
            <button type={"submit"}>Submit</button>
        </form>
    );
}

export default LoginPage;