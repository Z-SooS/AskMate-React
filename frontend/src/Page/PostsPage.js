import React, {useEffect, useState} from 'react';
import LoadingIndicator from "../Component/LoadingIndicator";
import AddPostModal from "../Component/AddPostModal";
import APIRequests from "../Utility/APIRequests";
import PostsPageItem from "../Component/PostList/PostsPageItem";
import {useLocation, useParams} from "react-router-dom";
import breadCrumbFunctions from "../Utility/BreadCrumbFunctions";
import "../Utility/TagArray.js"

function PostsPage() {
    const TagArray = require("../Utility/TagArray")
    const {pageFromPath} = useParams();
    let [data, setData] = useState([]);
    let [orderBy, setOrderBy] = useState("score");
    let [orderDir, setOrderDir] = useState("DESC");
    const [tags, setTags] = useState(new TagArray([]));
    let [page,setPage] = useState(pageFromPath != null ? parseInt(pageFromPath) : 0);
    let [postIsOpen, setPostIsOpen] = useState(false);
    const [error,setError] = useState(null);
    const [loading, setLoading] = useState(true);

    const location = useLocation();

    async function getData(){
        await APIRequests.get(`/post-service/posts/${page}?order=${orderBy}&direction=${orderDir}${tags.array.map(t => "&tag="+t).join()}`)
            .then(response => {
                if (!response.ok) {
                    throw new Error(`The returned response was ${response.status}`);
                }
                if(response.status === 204) {
                    throw new Error('No posts were found')
                }
                return response.json()
            })
            .then(actualData => {
                setError(null)
                setData(actualData);
            })
            .catch(err => {
                setData([]);
                setError(err.message);
            })
            .finally(() => {setLoading(false);})
    }

    async function reloadFunction(){
        await getData();
    }

    function setBreadCrumb(){
        if(location.pathname === '/posts' && location.search === '') {
            breadCrumbFunctions.set(new Map([
                ['Home','home'],
                ['Posts','posts']
            ]));
            setPage(0);
            return;
        }
        breadCrumbFunctions.set(new Map([
            ['Home','home'],
            ['Posts','posts'],
            ['Search',`posts/0?order=${orderBy}&direction=${orderDir}${tags.array.map(t => "&tag="+t).join()}`],
            [`Page ${page+1}`,`posts/${page}?order=${orderBy}&direction=${orderDir}${tags.array.map(t => "&tag="+t).join()}`]
        ]))
    }

    function setQueryParams() {

        const queryParams = new URLSearchParams(location.search);
        const queryTags = new TagArray(queryParams.getAll('tag'));
        if (!queryTags.equals(tags)) {
            setTags(queryTags);
        }
        const order = queryParams.get('order');
        const direction = queryParams.get('direction');

        if (order != null && order !== orderBy) setOrderBy(order);
        if (direction != null && direction !== orderDir) setOrderDir(direction);
        if (pageFromPath != null) {
            setPage(parseInt(pageFromPath));
        } else {
            setPage(0);
        }
    }
    useEffect(() => {
        setBreadCrumb();
        setLoading(true);
        getData();
    }, [page,orderDir,orderBy,tags]);
    useEffect(() => {
        setPostIsOpen(false);
        setQueryParams();
    }, [location.pathname,location.search]);
    if(error) return (<div className={"error"} id={"response-error-box"}>{`There is a problem fetching data from the server - ${error}`}</div>);
    return (
        <>
            {/*Debug*/}
            {/*<Link to={`/posts/${page+1}?order=${orderBy}&direction=${orderDir}${tags.array.map(t => "&tag="+t).join()}`} onClick={()=>{setPage(page+1)}}>Increase page number</Link>*/}
            <AddPostModal reloadFunction={reloadFunction}/>
            {loading && <LoadingIndicator/>}
            {data.length > 0 && data.map(p => (<PostsPageItem postObject={p} key={p.id}></PostsPageItem>))}
        </>
    );
}

export default PostsPage;