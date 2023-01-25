import React, {useState} from 'react';
import LoadingIndicator from "../Component/LoadingIndicator";
import {fieldType,fieldName} from "../Config/FormFieldData";
import LabeledFormInput from "../Component/LabeledFormInput";
import APIRequests from "../Utility/APIRequests";

const minPasswordLength = 6;
const minUsernameLength = 4;


function RegisterPage() {
    const [loading, setLoading] = useState(false);
    const [invalidRequestError, setInvalidRequestError] = useState(null);

    function isUsernameValid(){
        const username = document.getElementById(fieldName.username+fieldType.input).value;
        if(username.length < minUsernameLength) {
            document.getElementById(fieldName.username+fieldType.error).innerText = `Username must be at least ${minUsernameLength} characters`;
            return false;
        }
        document.getElementById(fieldName.username+fieldType.error).innerText = "";
        return true;
    }
    function isPasswordValid(){
        const password = document.getElementById(fieldName.password+fieldType.input).value;
        if (password.length < minPasswordLength) {
            document.getElementById(fieldName.password+fieldType.error).innerText = `Password must be at least ${minPasswordLength} characters long`;
            return false;
        }
        if (!password.match(/([A-Z]).*([a-z]).(.*\d)/gm)) {
            document.getElementById(fieldName.password+fieldType.error).innerText = "Password must have at least one UPPERCASE, one lowercase and one digit character";
            return false;
        }
        document.getElementById(fieldName.password+fieldType.error).innerText = "";
        return true;
    }
    function passwordsMatch(){
        let password = document.getElementById(fieldName.password+fieldType.input).value;
        let repeat = document.getElementById(fieldName.passwordRepeat+fieldType.input).value;
        if(password !== repeat) {
            document.getElementById(fieldName.passwordRepeat+fieldType.error).innerText = "Passwords do not match";
            return false;
        }
        document.getElementById(fieldName.passwordRepeat+fieldType.error).innerText = "";
        return true;
    }
    function isEmailValid(){
        let email = document.getElementById(fieldName.email+fieldType.input).value;
        console.log("validation: " + email);
        if(email
            .toLowerCase()
            .match(
                /^(?=.{1,64}@)[A-Za-z0-9_-]+(\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\.[A-Za-z0-9-]+)*(\.[A-Za-z]{2,})$/
            )) {
            document.getElementById(fieldName.email+fieldType.error).innerText = "";
            return true;
        }
        document.getElementById(fieldName.email+fieldType.error).innerText = "Invalid E-mail";
        return false
    }

    async function checkDetailsAreValid(event){
        event.preventDefault();

        if(isEmailValid() && isUsernameValid() && isPasswordValid() && passwordsMatch()) {
            const password = document.getElementById(fieldName.password+fieldType.input).value;
            const username = document.getElementById(fieldName.username+fieldType.input).value;
            const email = document.getElementById(fieldName.email+fieldType.input).value;
            setLoading(true);
            let statusCode = await APIRequests.post("/user-service/register",{
                'username':username,
                'password':password,
                'email':email
            }).then(response => response.status);
            setLoading(false);
            if(statusCode === 409) {
                setInvalidRequestError("User already exists!")
            } else if (statusCode === 201){
                window.location.href = "/";
            } else {
                setInvalidRequestError("Something went wrong while sending the request");
            }
        }

    }
    if (loading) return (<><LoadingIndicator/></>);
    return (
        <form id={'register-form'} onSubmit={checkDetailsAreValid}>
            {invalidRequestError != null && <div id={fieldName.serverResponse}>{invalidRequestError}</div>}
            <LabeledFormInput label={"Username"} name={fieldName.username} inputType={"text"} checkerFunction={isUsernameValid}/>
            <LabeledFormInput label={"E-mail"} name={fieldName.email} inputType={"email"} checkerFunction={isEmailValid}/>
            <LabeledFormInput label={"Password"} name={fieldName.password} inputType={"password"} checkerFunction={isPasswordValid}/>
            <LabeledFormInput label={"Password repeat"} name={fieldName.passwordRepeat} inputType={"password"} checkerFunction={passwordsMatch}/>
            <button type={"submit"}>Submit</button>
        </form>
    );
}

export default RegisterPage;