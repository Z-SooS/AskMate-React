import React, {useEffect, useState} from 'react';
import LoadingIndicator from "../Component/LoadingIndicator";
import AddPostModal from "../Component/AddPostModal";
import APIRequests from "../Utility/APIRequests";
import PostsPageItem from "../Component/PostList/PostsPageItem";
import {useLocation, useParams} from "react-router-dom";
import breadCrumbFunctions from "../Utility/BreadCrumbFunctions";
import "../Utility/TagArray.js"

// ToDo Delete console logs
function PostsPage() {
    function getOrderByFromQuery(){
        const order = queryParams.get('order');
        return order == null ? 'score' : order;
    }
    function getOrderDirFromQuery(){
        const direction = queryParams.get('direction');
        return direction == null ? 'DESC' : direction;
    }
    function getPageFromQuery(){
        return pageFromPath != null ? parseInt(pageFromPath) : 0;
    }
    function getTagsFromQuery(){
        return new TagArray(queryParams.getAll('tag'));
    }
    const TagArray = require("../Utility/TagArray")
    const {pageFromPath} = useParams();
    const location = useLocation();
    const queryParams = new URLSearchParams(location.search);
    let [data, setData] = useState([]);

    let [orderBy, setOrderBy] = useState(getOrderByFromQuery());
    let [orderDir, setOrderDir] = useState(getOrderDirFromQuery());
    const [tags, setTags] = useState(getTagsFromQuery());
    let [page,setPage] = useState(getPageFromQuery());

    let [postIsOpen, setPostIsOpen] = useState(false);
    const [error,setError] = useState(null);
    const [loading, setLoading] = useState(true);


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
            console.log('breadCrumb',page)
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
        const queryTags = getTagsFromQuery();
        if(!queryTags.equals(tags)) {console.log('stepped into tag if');setTags(queryTags)}

        const order = getOrderByFromQuery();
        if(order !== orderBy) setOrderBy(order);

        const direction = getOrderDirFromQuery();
        if(direction !== orderDir) setOrderDir(direction);

        const queryPage = getPageFromQuery();
        if(queryPage !== page) setPage(queryPage);
    }
    useEffect(() => {
        setQueryParams();
        setBreadCrumb();
        setLoading(true);
        getData();
    }, [page,orderDir,orderBy,tags,location.pathname,location.search]);
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