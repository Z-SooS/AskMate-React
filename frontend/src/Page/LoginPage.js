import React from 'react';
import LabeledFormInput from "../Component/LabeledFormInput";
import {fieldName} from "../Config/FormFieldData";

function LoginPage({userInfoSetter, getUserInfoFromCookie}) {
    async function sendLoginRequest(event){
        event.preventDefault();
        const form = new FormData(event.currentTarget);
        const username = form.get(fieldName.username);
        const password = form.get(fieldName.password);


        await fetch("/api/user-service/login",{
            method:"POST",
            headers:{
                'Content-Type': 'application/json'
            },
            body:JSON.stringify({
                'username':username,
                'password':password
            })
        }).then((r) =>{
            if(r.status === 200) {
                userInfoSetter(getUserInfoFromCookie());
                return;
            }
            // ToDo Throw Error
        })
    }

    return (
        <form id={"login-form"} onSubmit={sendLoginRequest}>
            <LabeledFormInput label={"Username"} name={fieldName.username} inputType={"text"}/>
            <LabeledFormInput label={"Password"} name={fieldName.password} inputType={"password"}/>
            <button type={"submit"}>Submit</button>
        </form>
    );
}

export default LoginPage;