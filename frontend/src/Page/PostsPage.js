import React, {useEffect, useState} from 'react';
import LoadingIndicator from "../Component/LoadingIndicator";
import PostContainer from "../Component/PostContainer";
import AddPostModal from "../Component/AddPostModal";

function PostsPage(props) {
    let [data, setData] = useState([]);
    let [orderBy, setOrderBy] = useState("score");
    let [orderDir, setOrderDir] = useState("DESC");
    let [page,setPage] = useState(0);
    const [error,setError] = useState(null);
    const [loading, setLoading] = useState(true);

    async function getData(){
        await fetch(`/api/post-service/posts/${page}?order=${orderBy}&direction=${orderDir}`)
            .then(response => {
                if (!response.ok) {
                    throw new Error(`The returned response was ${response.status}`);
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
    useEffect(() => {
        setLoading(true);
        getData();
    }, [page,orderDir,orderBy]);
    if(error) return (<div className={"error"} id={"response-error-box"}>{`There is a problem fetching data from the server - ${error}`}</div>);
    return (
        <>
            <AddPostModal reloadFunction={reloadFunction}/>
            {loading && <LoadingIndicator/>}
            {data.length > 0 && data.map(p => (<PostContainer postObject={p} key={p.post.id}></PostContainer>))}
        </>
    );
}

export default PostsPage;