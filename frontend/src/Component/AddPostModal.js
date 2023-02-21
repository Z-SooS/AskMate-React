import React, {useEffect, useRef, useState} from 'react';
import LabeledFormInput from "./LabeledFormInput";
import {fieldName} from "../Config/FormFieldData";
import Select from "react-select";
import LoadingIndicator from "./LoadingIndicator";
import '../ComponentStyle/Modals.css';
import APIRequests from "../Utility/APIRequests";

function AddPostModal({reloadFunction}) {
    const [error, setError] = useState(null);
    const [isOpen, setIsOpen] = useState(false);
    const [isLoading, setIsLoading] = useState(false);

    const tagData = useRef(null);

    const titleElement = useRef();
    const messageElement = useRef();
    let selectedTags = useRef([]);

    function updateSelectedTags(updatedTags) {
        selectedTags.current = updatedTags;
    }

    function openModal() {
        setIsLoading(true);
        setIsOpen(true);
    }

    function closeModal() {
        setIsOpen(false);
    }

    async function getTags() {
        await APIRequests.get("/tag-service/all-tags")
            .then(r => {
                if (!r.ok) throw new Error(`Request returned with ${r.status}`)
                return r.json();
            })
            .then(rData => {
                tagData.current = rData;
                setError(null);
            })
            .catch(err => {
                tagData.current = null;
                setError(err.message);
            });
    }

    useEffect(() => {
        if (isOpen === false) return;
        if (tagData.current == null) getTags()
            .then(() => {setIsLoading(false);});
    }, [isOpen]);

    async function sendNewPostRequest(event) {
        event.preventDefault();

        const tags = selectedTags.current.map(t => {
            return {id: t.value, name: t.label}
        });

        // ToDo Sanitize strings
        await APIRequests.post("/post-service/add-post",{
            "title": titleElement.current?.value,
            "message": messageElement.current?.value,
            "tags": tags
        })
        .then(()=>{reloadFunction();setIsOpen(false);});
    }


    if (isLoading) {
        return (<div className={"background-shade"}>
            <aside className={"modal"} id={"new-post-modal"}><LoadingIndicator/></aside>
        </div>)
    }
    if (!isOpen) {
        return (<button onClick={openModal} className={"add-post-modal"}>Add new post</button>)
    }
    if (error != null) {
        return (
            <>
                <button onClick={openModal} className={"add-post-modal"}>Add new post</button>
                <div className={"background-shade"}>
                    <aside className={"modal"} id={"new-post-modal"}>
                        <button onClick={closeModal} className={"close-modal-button close-add-post-modal"}>x</button>
                        <p className={"error"} id={"new-post-error"}>{error}</p>
                    </aside>
                </div>
            </>
        )
    }
    if (tagData.current != null) {
        const selectOption = tagData.current.map(t => {
            return {value: t.id, label: t.name};
        });
        return (
            <>
                <button onClick={openModal} className={"add-post-modal"}>Add new post</button>
                <div className={"background-shade"}>
                    <aside className={"modal"} id={"new-post-modal"}>
                        <button onClick={closeModal} className={"close-modal-button close-add-post-modal"}>x</button>
                        <form onSubmit={sendNewPostRequest}>
                            <LabeledFormInput label={"Title"} name={fieldName.postTitle} inputType={"text"} refVariable={titleElement}/>
                            <LabeledFormInput label={"Message"} name={fieldName.postMessage} inputType={"text"} refVariable={messageElement}/>
                            <Select
                                isMulti
                                options={selectOption}
                                onChange={updateSelectedTags}
                            />
                            <button type={"submit"}>Add new Post</button>
                        </form>
                    </aside>
                </div>
            </>
        );
    }
    return (<></>);
}

export default AddPostModal;