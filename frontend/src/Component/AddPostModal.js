import React, {useEffect, useState} from 'react';
import LabeledFormInput from "./LabeledFormInput";
import {fieldName, fieldType} from "../Config/FormFieldData";
import Select from "react-select";
import LoadingIndicator from "./LoadingIndicator";
import '../ComponentStyle/Modals.css';

function AddPostModal({reloadFunction}) {
    const [tagData, setTagData] = useState(null);
    const [error, setError] = useState(null);
    const [selectedTags, setSelectedTags] = useState("");
    const [isOpen, setIsOpen] = useState(false);
    const [isLoading, setIsLoading] = useState(false);
    useEffect(() => {
        if (isOpen === false) {
            return;
        }

        async function getTags() {
            await fetch("/api/tag-service/all-tags")
                .then(r => {
                    if (!r.ok) throw new Error(`Request returned with ${r.status}`)
                    return r.json();
                })
                .then(rData => {
                    setTagData(rData);
                    setError(null);
                })
                .catch(err => {
                    setTagData(null);
                    setError(err.message);
                });
        }

        getTags().then(() => {
            setIsLoading(false);
        });
    }, [isOpen]);

    function updateSelectedTags(selectedTags) {
        setSelectedTags(selectedTags);
    }

    function openModal() {
        setIsLoading(true);
        setIsOpen(true);
    }

    function closeModal() {
        setIsOpen(false);
    }

    async function sendNewPostRequest(event) {
        event.preventDefault();
        const title = document.getElementById(fieldName.postTitle + fieldType.input).value;
        const message = document.getElementById(fieldName.postMessage + fieldType.input).value;
        const tags = selectedTags.map(t => {
            return {id: t.value, name: t.label}
        });

        await fetch("/api/post-service/add-post", {
            method: "POST",
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                "title": title,
                "message": message,
                "tags": tags
            })
        }).then(()=>{reloadFunction()})
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
    if (tagData != null) {
        const selectOption = tagData.map(t => {
            return {value: t.id, label: t.name};
        });
        return (
            <>
                <button onClick={openModal} className={"add-post-modal"}>Add new post</button>
                <div className={"background-shade"}>
                    <aside className={"modal"} id={"new-post-modal"}>
                        <button onClick={closeModal} className={"close-modal-button close-add-post-modal"}>x</button>
                        <form onSubmit={sendNewPostRequest}>
                            <LabeledFormInput label={"Title"} name={fieldName.postTitle} inputType={"text"}/>
                            <LabeledFormInput label={"Message"} name={fieldName.postMessage} inputType={"text"}/>
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