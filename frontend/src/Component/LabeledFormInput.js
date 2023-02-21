import React from 'react';
import PropTypes from "prop-types";
import {fieldType} from "../Config/FormFieldData";

function LabeledFormInput({name,label,inputType,checkerFunction, refVariable}) {
    return (
        <label id={name+fieldType.label} htmlFor={name} className={"form-input-group"}>
            {label}:
            <input type={inputType}
                   id={name+fieldType.input}
                   className={"form-input-field"}
                   name={name}
                   onChange={checkerFunction} ref={refVariable}/>
            <div id={name+fieldType.error} className={"form-input-error"}></div>
        </label>
    );
}

export default LabeledFormInput;

LabeledFormInput.propTypes = {
    name: PropTypes.string,
    inputType: PropTypes.string,
    label: PropTypes.string,
    checkerFunction: PropTypes.func
};